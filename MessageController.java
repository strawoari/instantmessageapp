package com.heiyu.messaging.controller;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heiyu.messaging.model.Message;
import com.heiyu.messaging.model.User;
import com.heiyu.messaging.request.SendMessageRequest;
import com.heiyu.messaging.service.MessageService;
import com.heiyu.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;


    @PostMapping("/sendMessages")
    public void sendMessage(@RequestBody SendMessageRequest sendMessageRequest) throws Exception {
        AmazonSNS amazonSNS = AmazonSNSClient.builder()
                .withRegion(this.region)
                .withCredentials(this.awsCredentialsProvider)
                .build();
        Map<String, Object> notification = Map.of(
                "message", sendMessageRequest.getContent(),
                "sendtime", new Date(),
                "receiverUserIds", List.of(1,2)
        );
        System.out.println(notification);

        PublishRequest publishRequest = new PublishRequest()
                .withMessage(new ObjectMapper().writeValueAsString(notification))
                .withTopicArn("arn:aws:sns:us-east-2:121322983371:Messages");
        amazonSNS.publish(publishRequest);
    }

    @GetMapping("/listMessages")
    public List<Message> listMessages(@RequestParam String loginToken,
                                      @RequestParam Integer userId,
                                      @RequestParam Integer groupChatId,
                                      @RequestParam int page) throws Exception { // 1, 2, 3, ....

        User user = this.userService.authenticate(loginToken);
        return this.messageService.listMessages(user, userId, groupChatId, page);
    }
}