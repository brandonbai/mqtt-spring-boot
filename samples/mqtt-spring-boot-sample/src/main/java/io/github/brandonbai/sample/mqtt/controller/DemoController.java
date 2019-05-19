package io.github.brandonbai.sample.mqtt.controller;

import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mqtt")
public class DemoController {

    @Resource
    private MqttPahoMessageHandler mqttPahoMessageHandler;

    @RequestMapping("/send")
    public String send(String topic, String content) {

        Message<String> message = MessageBuilder.withPayload(content).setHeader(MqttHeaders.TOPIC, topic).build();

        mqttPahoMessageHandler.handleMessage(message);
        return "ok";
    }
}
