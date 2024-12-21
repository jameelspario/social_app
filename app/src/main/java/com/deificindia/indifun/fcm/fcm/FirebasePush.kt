package com.spario.lib

import com.spario.lib.fcm.FirebasePushBuilder
import com.spario.lib.fcm.PushService
import com.spario.lib.fcm.config.PushType.TO_GROUP
import com.spario.lib.fcm.config.PushType.TO_TOKEN
import com.spario.lib.fcm.config.PushType.TO_TOPIC
import com.spario.lib.fcm.connection.PushNotificationTask
import com.spario.lib.fcm.model.Notification
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
/*
FirebasePush.build(serverKey)
                .setNotification(Notification("FCM-AndroidToOtherDevice", "This is a body"))
                .setOnFinishPush { onFinishPush() }
                .sendToTopic(topic)
--------------------------------------------------------
fun fcmpush(){
        FirebasePush.build("jdsgfhaedhqwitrofnasdhaskgr")
                //.setNotification(Notification("FCM-AndroidToOtherDevice", "This is a body"))
                .setData(JSONObject().apply {
                    put("MESSAGE", "msg")
                    put("USER", "user name")
                })
                .setOnFinishPush { onFinishPush() }
                .sendToToken("/topics/${resources.getString(R.string.LiveChat)}")

}
 */
class FirebasePush constructor(private val serverKey: String) : PushService, FirebasePushBuilder {

    companion object {

        /** Request method **/
        private const val POST = "POST"

        /** Properties keys **/
        private const val CONTENT_TYPE = "Content-Type"
        private const val ACCEPT = "Accept"
        private const val AUTHORIZATION = "Authorization"

        /** Properties values **/
        private const val APPLICATION_JSON = "application/json"

        /** Root keys **/
        private const val NOTIFICATION = "notification"
        private const val DATA = "data"

        /** Build method **/
        fun build(serverKey: String) = FirebasePush(serverKey)

        /** FCM **/
        private const val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"
    }

    private var root: JSONObject = JSONObject()
    var asyncResponse: PushNotificationTask.AsyncResponse? = null

    override fun sendToToken(token: String) {
        root.put(TO_TOKEN, token)
        sendPushNotification(false)
    }

    override fun setData(data: JSONObject) = apply {
        //this.data = data
        root.put(DATA, data)
    }

    override fun setOnFinishPush(asyncResponse: PushNotificationTask.AsyncResponse) = apply {
        this.asyncResponse = asyncResponse
    }

    override fun setOnFinishPush(onFinishPush: (s:String) -> Unit) = apply {
        this.asyncResponse = object : PushNotificationTask.AsyncResponse {
            override fun onFinishPush(ouput: String) {
                onFinishPush(ouput)
            }
        }
    }

    private fun sendPushNotification(toTopic: Boolean) {
        val conn = URL(API_URL_FCM).openConnection() as HttpURLConnection
        conn.apply {
            useCaches = false
            doInput = true
            doOutput = true
            requestMethod = POST

            setRequestProperty(CONTENT_TYPE, APPLICATION_JSON)
            setRequestProperty(ACCEPT, APPLICATION_JSON)
            setRequestProperty(AUTHORIZATION, "key=$serverKey")
        }

        val pushNotificationTask = PushNotificationTask(conn, root, toTopic)
        pushNotificationTask.asyncResponse = asyncResponse
        pushNotificationTask.execute()
    }
}