package io.github.brandonbai.mqtt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;

import javax.annotation.Resource;

/**
 * mqtt auto configuration
 */
@Configuration
@ConditionalOnProperty(prefix = "mqtt")
@EnableConfigurationProperties(MqttProperties.class)
public class MqttAutoConfiguration {

    @Resource
    private MqttProperties mqttProperties;

    @Bean
    public DefaultMqttPahoClientFactory clientFactory() {

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setPassword(mqttProperties.getUsername());
        factory.setUserName(mqttProperties.getPassword());
        factory.setCleanSession(mqttProperties.getCleanSession());
        factory.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        factory.setServerURIs(mqttProperties.getServerURI1());

        return factory;
    }

    @Bean
    public MqttMessageClient mqttMessageClient() {

        MqttMessageClient client = new MqttMessageClient(mqttProperties.getClientId(), clientFactory());
        client.setAsync(mqttProperties.getAsync());
        client.setDefaultQos(mqttProperties.getDefaultQos());
        client.setCompletionTimeout(mqttProperties.getCompletionTimeout());

        return client;
    }
}
