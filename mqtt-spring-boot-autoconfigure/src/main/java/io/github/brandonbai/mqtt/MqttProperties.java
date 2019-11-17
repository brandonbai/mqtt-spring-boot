package io.github.brandonbai.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否清除会话
     */
    private Boolean cleanSession = false;
    /**
     * 服务端url
     */
    private String[] serverURIs;
    /**
     * 是否异步发送
     */
    private Boolean async = true;
    /**
     * 超时时间
     */
    private int completionTimeout = 20000;
    /**
     * 心跳
     */
    private int keepAliveInterval = 30;
    /**
     * 客户端id
     */
    private String clientId = "mqttPubClient";
    /**
     * 默认的消息服务质量
     */
    private int defaultQos = 1;
}
