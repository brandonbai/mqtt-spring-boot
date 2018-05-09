package com.github.brandonbai.springmqttdemo.controller;

import com.github.brandonbai.springmqttdemo.service.MqttService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MqttController {

    @Resource
    private MqttService mqttService;

    @RequestMapping("/send")
    public String sendMessgae(String topic, String content) {

        mqttService.send(topic, content);

        return "发送成功";
    }

}
