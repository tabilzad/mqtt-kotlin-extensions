package com.tabilzad.mqtt

import org.eclipse.paho.client.mqttv3.*

data class MqttActionListenerBuilder(
    private val action: (IMqttActionListener) -> IMqttToken,
    var onSuccess: ((asyncActionToken: IMqttToken) -> Unit) = {},
    var onFailure: (asyncActionToken: IMqttToken?, exception: Throwable?) -> Unit = { _: IMqttToken?, _: Throwable? -> }
) : MqttAPI {

    override fun commit(onCommitError: (e: MqttException) -> Unit) {
        try {
            action(object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    this@MqttActionListenerBuilder.onSuccess(asyncActionToken)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    this@MqttActionListenerBuilder.onFailure(asyncActionToken, exception)
                }
            })
        } catch (ex: MqttException) {
            onFailure(null, ex)
        } catch (ex: IllegalArgumentException) {
            onFailure(null, ex)
        } catch (ex: NullPointerException) {
            onFailure(null, ex)
        } catch (ex: Throwable) {
            onFailure(null, ex)
        }
    }
}

fun MqttActionListenerBuilder.onSuccess(doOnSuccess: (asyncActionToken: IMqttToken) -> Unit) {
    this.onSuccess = doOnSuccess
}

fun MqttActionListenerBuilder.onFailure(doOnFailure: (asyncActionToken: IMqttToken?, exception: Throwable?) -> Unit) {
    this.onFailure = doOnFailure
}

data class MqttCallbackExtendedBuilder(
    private val action: (MqttCallback) -> Unit,
    var connectionLost: (Throwable?) -> Unit = {},
    var messageArrived: (String, MqttMessage) -> Unit = { _, _ -> },
    var deliveryComplete: (IMqttDeliveryToken) -> Unit = { },
    var connectComplete: (Boolean, String) -> Unit = { _, _ -> },
    var onException: (MqttException) -> Unit = { }
) : MqttAPI {

    override fun commit(onCommitError: (e: MqttException) -> Unit) {
        try {
            action(object : MqttCallbackExtended {
                override fun connectionLost(cause: Throwable?) {
                    this@MqttCallbackExtendedBuilder.connectionLost(cause)
                }

                @Throws(Exception::class)
                override fun messageArrived(topic: String, message: MqttMessage) {
                    this@MqttCallbackExtendedBuilder.messageArrived(topic, message)
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {
                    this@MqttCallbackExtendedBuilder.deliveryComplete(token)
                }

                override fun connectComplete(reconnect: Boolean, serverURI: String) {
                    this@MqttCallbackExtendedBuilder.connectComplete(reconnect, serverURI)
                }
            })
        } catch (ex: MqttException) {
            onException(ex)
            onCommitError(ex)
        }
    }
}

fun MqttCallbackExtendedBuilder.onConnectComplete(block: (reconnect: Boolean, serverURI: String) -> Unit) {
    this.connectComplete = block
}

fun MqttCallbackExtendedBuilder.onDeliveryComplete(block: (token: IMqttDeliveryToken) -> Unit) {
    this.deliveryComplete = block
}

@Throws(Exception::class)
fun MqttCallbackExtendedBuilder.onMessageArrived(block: (topic: String, message: MqttMessage) -> Unit) {
    this.messageArrived = block
}

fun MqttCallbackExtendedBuilder.onConnectionLost(block: (Throwable?) -> Unit) {
    this.connectionLost = block
}

fun MqttCallbackExtendedBuilder.catchError(block: (MqttException?) -> Unit) {
    this.onException = block
}
