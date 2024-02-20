package com.heiyu.messaging.aspect;

import java.util.Date;
import java.util.List;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;


    @Around("execution(* com.heiyu.messaging.controller.*.*(..))")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String className = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        log.info("{}.{} started", className, methodName);
        boolean exceptionThrown = false;
        Date startTime = new Date();
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            exceptionThrown = true;
            throw throwable;
        } finally {
            Date endTime = new Date();
            log.info("{}.{} ended. Latency: {} ms, exceptionThrown: {}", className, methodName,
                    endTime.getTime() - startTime.getTime(), exceptionThrown);

            AmazonCloudWatch amazonCloudWatch = AmazonCloudWatchClientBuilder.standard()
                    .withRegion(this.region)
                    .withCredentials(this.awsCredentialsProvider)
                    .build();

            List<Dimension> dimensions = List.of(
                    new Dimension().withName("ClassName").withValue(className), //isn't Dimension() length of 0?
                    new Dimension().withName("MethodName").withValue(methodName));

            PutMetricDataRequest putMetricDataRequest = new PutMetricDataRequest()
                    .withNamespace("Messaging-Heiyu")
                    .withMetricData(List.of(new MetricDatum()
                                    .withMetricName("Latency")
                                    .withValue(1.0 * endTime.getTime() - startTime.getTime())
                                    .withUnit(StandardUnit.Milliseconds),
                            new MetricDatum()
                                    .withMetricName("Error")
                                    .withValue(exceptionThrown ? 1.0 : 0.0)
                                    .withUnit(StandardUnit.Count),
                            new MetricDatum()
                                    .withMetricName("Latency")
                                    .withValue(1.0 * endTime.getTime() - startTime.getTime())
                                    .withDimensions(dimensions)
                                    .withUnit(StandardUnit.Milliseconds),
                            new MetricDatum()
                                    .withMetricName("Error")
                                    .withDimensions(dimensions)
                                    .withValue(exceptionThrown ? 1.0 : 0.0)
                                    .withUnit(StandardUnit.Count)));

            amazonCloudWatch.putMetricData(putMetricDataRequest);
        }
    }

}
