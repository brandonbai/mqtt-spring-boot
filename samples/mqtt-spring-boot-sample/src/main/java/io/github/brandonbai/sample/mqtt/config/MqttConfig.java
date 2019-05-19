package io.github.brandonbai.sample.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig {

    @Resource
    private MqttProperties mqttProperties;

    @Bean
    public DefaultMqttPahoClientFactory clientFactory() {

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setPassword(mqttProperties.getUsername().toCharArray());
        connectOptions.setUserName(mqttProperties.getPassword());
        connectOptions.setCleanSession(mqttProperties.getCleanSession());
        connectOptions.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        connectOptions.setServerURIs(mqttProperties.getServerURIs());

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(connectOptions);

        return factory;
    }

    @Bean
    public MqttPahoMessageHandler mqttMessageClient() {

        MqttPahoMessageHandler client = new MqttPahoMessageHandler(mqttProperties.getClientId(), clientFactory());
        client.setAsync(mqttProperties.getAsync());
        client.setDefaultQos(mqttProperties.getDefaultQos());
        client.setCompletionTimeout(mqttProperties.getCompletionTimeout());

        return client;
    }
}