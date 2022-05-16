package com.tabilzad.mqtt

import org.eclipse.paho.client.mqttv3.MqttException

internal interface MqttAPI {
    fun commit(onCommitError: (e: MqttException) -> Unit = {})
}

internal fun <T : MqttAPI> T.buildWith(actions: T.() -> Unit) {
    actions(this)
    commit()
}

