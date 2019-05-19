package com.github.brandonbai.springmqttdemo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

public class MqttSubscriber extends MqttPahoMessageDrivenChannelAdapter {


    public MqttSubscriber(String clientId, MqttPahoClientFactory clientFactory, String... topic) {
        super(clientId, clientFactory, topic);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {

        System.out.println(topic);
        System.out.println(new String(mqttMessage.getPayload()));
    }
}
