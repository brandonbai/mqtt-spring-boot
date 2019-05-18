# MQTT integration with Spring Boot

mqtt-spring-boot-starter will help you use mqtt with Spring Boot.


## Summary

- [Maven](#maven)
- [Configuration](#configuration)
- [Use](#use)
- [Samples](#samples)

## Maven
```xml
<dependency>
  <groupId>io.github.brandonbai</groupId>
  <artifactId>mqtt-spring-boot-starter</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Configuration
`{your applicaton.yml/applicaton.properties}`

```yml
mqtt:
  username: mqttPubClient
  password: 123456
  cleanSession: false
  serverURIs: tcp://localhost:1883
  async: true
  completionTimeout: 20000
  keepAliveInterval: 30
  clientId: mqttPubClient
  defaultQos: 1
```

## Use

```java
public class YourClass {
  @Resource
  private MqttMessageClient mqttMessageClient;

  public void yourMethod() {
    mqttMessageClient.sendMessage("topic", "content");
  }
}
```

## Samples

- [Mqtt + SpringMvc + Spring](./samples/mqtt-spring-demo)

- [mqtt + Spring Boot]() // todo

- [mqtt-spring-boot-starter]() // todo
