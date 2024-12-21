package com.deificindia.indifun.fcm

import com.spario.lib.FirebasePush
import org.json.JSONObject

class FirePush {

    companion object obj{
        public fun inviteuser( touser:String, pkroom:String, pkuserid:String, pkusername:String, pkavtar:String){
            FirebasePush.build("AAAAVxBtAO0:APA91bHFQXCYI5zdp7zAWNHP9h0szE2r0EvSAqtn76kGmioXSOBM3BMbArFK6lj0LPAx5CzgZLFvXq8tHTKKEngXDb85D8TLcRwzIN9SsDTlBT1r29rHT4DM7PlyEYVseiW18nbeQEaI")
                    //.setNotification(Notification("FCM-AndroidToOtherDevice", "This is a body"))
                    .setData(JSONObject().apply {
                        put("type", 1)
                        put("pkroom", pkroom)
                        put("pkuserid", pkuserid)
                        put("pkusername", pkuserid)
                        put("pkavtar", pkavtar)

                    })
                    .setOnFinishPush {s->
                        //onFinishPush(s)
                    }
                    .sendToToken("/topics/$touser")
        }

        fun acceptPk(touser:String){
            FirebasePush.build("AAAAVxBtAO0:APA91bHFQXCYI5zdp7zAWNHP9h0szE2r0EvSAqtn76kGmioXSOBM3BMbArFK6lj0LPAx5CzgZLFvXq8tHTKKEngXDb85D8TLcRwzIN9SsDTlBT1r29rHT4DM7PlyEYVseiW18nbeQEaI")
                    //.setNotification(Notification("FCM-AndroidToOtherDevice", "This is a body"))
                    .setData(JSONObject().apply {
                        put("type", 2)
                       /* put("pkroom", pkroom)
                        put("pkuserid", pkuserid)
                        put("pkusername", pkuserid)
                        put("pkavtar", pkavtar)*/

                    })
                    .setOnFinishPush {s->
                        //onFinishPush(s)
                    }
                    .sendToToken("/topics/$touser")
        }
    }



}