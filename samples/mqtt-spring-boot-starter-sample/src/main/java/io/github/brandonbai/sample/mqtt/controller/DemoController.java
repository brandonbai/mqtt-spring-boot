package io.github.brandonbai.sample.mqtt.controller;

import io.github.brandonbai.mqtt.MqttMessageClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mqtt")
public class DemoController {

    @Resource
    private MqttMessageClient mqttMessageClient;

    @RequestMapping("/send")
    public String send(String topic, String content) {

        mqttMessageClient.sendMessage(topic, content);
        return "ok";
    }
}
