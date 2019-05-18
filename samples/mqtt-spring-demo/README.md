# spring-mqtt-demo

## 重要

- 当消息发布并发过高时，报错: 32202，详见`org.eclipse.paho.client.mqttv3.internal.ClientState#ClientState`:

```Java
492	if (actualInFlight >= this.maxInflight) {
493		//@TRACE 613= sending {0} msgs at max inflight window
494		log.fine(CLASS_NAME, methodName, "613", new Object[]{new Integer(actualInFlight)});
495
496		throw new MqttException(MqttException.REASON_CODE_MAX_INFLIGHT);
497	}
```
原来默认的处于 __飞行__ 状态的消息数量有限制，默认为10。
__飞行__ 状态表述为: 未被ack的消息。

由此可见，qos=1或2时可能会出现此问题。

-------

## 介绍
[SpringBoot 配置点这里](../../../..)

## 介绍
Spring Integration基于[Eclipse Paho MQTT客户端](https://www.eclipse.org/paho/)库提供了支持MQTT协议的所谓入站和出站通道适配器。但是使用起来不是很灵活(有可能是没有深入理解😂)。在参考了[这篇文章](https://blog.csdn.net/zhang89xiao/article/details/51871973)后有了如下实现：

#### 1. maven依赖

```
	<dependency>
        <groupId>org.springframework.integration</groupId>
        <artifactId>spring-integration-core</artifactId>
        <version>4.3.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.integration</groupId>
        <artifactId>spring-integration-mqtt</artifactId>
        <version>4.3.9.RELEASE</version>
    </dependency>
    <dependency>
	    <groupId>org.eclipse.paho</groupId>
	    <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
	    <version>1.2.0</version>
    </dependency>
```

#### 2. 配置文件

* applicationContext.xml

类型|描述
-|-
DefaultMqttPahoClientFactory|客户端工厂类,根据配置的选项(用户名、密码、服务器集群地址等)创建一个默认的客户端。
MqttPahoMessageHandler|MQTT出站通道适配器的抽象类的实现,用于推送消息。

```
    <bean id="clientFactory"  
        class="org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory">  
        <property name="userName" value="${mqtt.username}"/>  
        <property name="password" value="${mqtt.password}"/>
        <property name="cleanSession" value="${mqtt.cleanSession}"/>
        <property name="keepAliveInterval" value="${mqtt.keepAliveInterval}"/>
        <property name="serverURIs">
            <array>
                <value>${mqtt.serverURI1}</value>
            </array>
        </property>
    </bean>

    <bean id="mqttHandler" class="org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler">  
        <constructor-arg name="clientId" value="${mqtt.clientId}"/>
        <constructor-arg name="clientFactory" ref="clientFactory"/>
        <property name="async" value="${mqtt.async}"/>
        <property name="defaultQos" value="${mqtt.defaultQos}"/>
        <property name="completionTimeout" value="${mqtt.completionTimeout}"/>
    </bean>
```

* config.properties

```
#用户名
mqtt.username=mqttPubClient
#密码
mqtt.password=123456
#是否清除会话
mqtt.cleanSession=false
#服务端url
mqtt.serverURI1=tcp://localhost:1883
#是否异步发送
mqtt.async=true
#超时时间
mqtt.completionTimeout=20000
#心跳
mqtt.keepAliveInterval=30
#客户端id
mqtt.clientId=mqttPubClient
#默认的消息服务质量
mqtt.defaultQos=1

```
#### 3. 调用

```
	@Resource  
    private MqttPahoMessageHandler mqttHandler;  
	
	@Override
	public void send(String topic, String content) {
		// 构建消息
		Message<String> messages = MessageBuilder.withPayload(content).setHeader(MqttHeaders.TOPIC, topic).build();  
		// 发送消息
        mqttHandler.handleMessage(messages);
	}
```

##### 链接

*   MQTT Java客户端的使用：[https://www.jianshu.com/p/65e1748a930c](https://www.jianshu.com/p/65e1748a930c)
*  MQTT服务端Mosquitto搭建：[https://www.jianshu.com/p/9e3cb7042a2e](https://www.jianshu.com/p/9e3cb7042a2e)
