package com.tabilzad.mqtt

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions


fun IMqttAsyncClient.orchestrate(actions: MqttCallbackExtendedBuilder.() -> Unit) {
    MqttCallbackExtendedBuilder(::setCallback)
        .buildWith(actions)
}

fun IMqttAsyncClient.connect(
    options: MqttConnectOptions,
    actions: MqttActionListenerBuilder.() -> Unit
) {
    MqttActionListenerBuilder({ listener ->
        connect(options, null, listener)
    }).buildWith(actions)
}