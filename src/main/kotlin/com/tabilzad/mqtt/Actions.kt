package com.tabilzad.mqtt

import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttMessage

fun IMqttAsyncClient.subscribe(
    topic: String,
    qos: Int,
    actions: MqttActionListenerBuilder.() -> Unit
) {
    MqttActionListenerBuilder({ listener: IMqttActionListener ->
        subscribe(topic, qos, null, listener)
    }).buildWith(actions)
}

fun IMqttAsyncClient.unsubscribe(topic: String, actions: MqttActionListenerBuilder.() -> Unit) {
    MqttActionListenerBuilder({
        unsubscribe(topic)
    }).buildWith(actions)
}

fun IMqttAsyncClient.disconnect(
    actions: MqttActionListenerBuilder.() -> Unit = {}
) {
    MqttActionListenerBuilder({ listener ->
        disconnect(null, listener)
    }).buildWith(actions)
}

fun IMqttAsyncClient.publishMessage(
    topic: String,
    msg: MqttMessage,
    actions: MqttActionListenerBuilder.() -> Unit = {}
) {
    MqttActionListenerBuilder({
        publish(topic, msg)
    }).buildWith(actions)
}