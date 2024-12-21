package com.spario.lib.fcm

import com.spario.lib.FirebasePush
import com.spario.lib.fcm.connection.PushNotificationTask
import org.json.JSONObject

interface FirebasePushBuilder {
    fun setData(data: JSONObject) : FirebasePush
    fun setOnFinishPush(asyncResponse: PushNotificationTask.AsyncResponse) : FirebasePush
    fun setOnFinishPush(onFinishPush: (s:String) -> Unit) : FirebasePush
}