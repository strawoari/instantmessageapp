package com.heiyu.messaging.scheduler;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.List;

@Component
@Log4j2
public class ResourceUtilizationReporter {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

    @Scheduled(fixedRate = 5000)
    public void report() {

        OperatingSystemMXBean operatingSystemMXBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        AmazonCloudWatch amazonCloudWatch = AmazonCloudWatchClientBuilder.standard()
                .withRegion(this.region)
                .withCredentials(this.awsCredentialsProvider)
                .build();


        PutMetricDataRequest putMetricDataRequest = new PutMetricDataRequest()
                .withNamespace("Messaging-Heiyu")
                .withMetricData(List.of(new MetricDatum()
                        .withMetricName("ProcessCPULoad")
                        .withValue(operatingSystemMXBean.getProcessCpuLoad() * 100.0)
                        .withUnit(StandardUnit.Percent)
                ));

        amazonCloudWatch.putMetricData(putMetricDataRequest);
    }
}
