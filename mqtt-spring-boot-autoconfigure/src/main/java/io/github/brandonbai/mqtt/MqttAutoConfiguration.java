package io.github.brandonbai.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;

import javax.annotation.Resource;

/**
 * mqtt auto configuration
 */
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttAutoConfiguration {

    @Resource
    private MqttProperties mqttProperties;

    @Bean
    public DefaultMqttPahoClientFactory clientFactory() {

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        String username = mqttProperties.getUsername();
        String password = mqttProperties.getPassword();
        if(username != null) {
            connectOptions.setUserName(username);
        }
        if (password != null) {
            connectOptions.setPassword(password.toCharArray());
        }
        String[] serverURIs = mqttProperties.getServerURIs();
        if (serverURIs == null || serverURIs.length == 0) {
            throw new NullPointerException("serverURIs can not be null");
        }
        connectOptions.setCleanSession(mqttProperties.getCleanSession());
        connectOptions.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        connectOptions.setServerURIs(serverURIs);

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(connectOptions);

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
