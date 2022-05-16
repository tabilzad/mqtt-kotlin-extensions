# mqtt-kotlin-extensions

## Connection Extensions

```kotlin

import java.util.UUIDimport

val client = MqttAsyncClient("tcp://mqtt.eclipseprojects.io:1883", UUID.randomUUID().toString())

client.orchestrate {
    onConnectComplete { isReconnect, serverURI ->
        log.info("Connected to $serverURI")
    }
    onMessageArrived { topic, message: MqttMessage ->
        log.info("Message arrived on topic $topic")
        log.info(message.toString())
    }
    onDeliveryComplete {
        log.info("Message delivery of ${it.messageId} complete")
    }
    onConnectionLost { exception ->
        log.debug("MQTT connection lost due to ${exception?.message}")
    }
    catchError { exception ->
        log.error("Failed to connect to broker due to ${exception?.message}")
    }
}
```

```kotlin
  client.connect {
    onSuccess {
        log.info("MQTT Session started")
    }
    onFailure { _, exception ->
        log.error("Failed to connect due to ${exception.message}")
    }
}
```
## Action extensions
```kotlin
val topic = "my_topic"
client.subscribe(topic, 1) {
    onSuccess {
        log.info("Now subscribed to $topic successfully")
    }
    onFailure { _, t ->
        log.error("Failed to subscribe to $topic due to: ${t?.message}")
    }
}
```