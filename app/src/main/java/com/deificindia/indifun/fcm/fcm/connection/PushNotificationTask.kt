package com.spario.lib.fcm.connection

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection

class PushNotificationTask(private var conn: HttpURLConnection,
                           private var root: JSONObject,
                           private var toTopic: Boolean) : AsyncTask<Void, Void, String>() {

    var asyncResponse: AsyncResponse? = null

    override fun doInBackground(vararg p0: Void?): String {
        var wr: OutputStreamWriter? = null
        try {
            wr = OutputStreamWriter(conn.outputStream)
            wr.write(root.toString())
            wr.flush()
            log(root.toString())
            val builder = StringBuilder()

            conn.inputStream.bufferedReader().use { reader ->
                builder.append(reader.readLine())
            }

            val result = builder.toString()
            val obj = JSONObject(result)
            log(result)
            if (toTopic) {
                if (obj.has("message_id")) {
                    return obj.get("message_id").toString()
                }
            } else {
                val success = Integer.parseInt(obj.getString("success"))
                if (success > 0) {
                    return "SUCCESS"
                }
            }
            return builder.toString()
        } catch (e: Exception) {
            Log.e("PushNotification", e.message, e)
            return "Error in post to ${e.message}"
        } finally {
            wr?.close()
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
            result?.let {
                asyncResponse?.onFinishPush(it)
        }
    }

    interface AsyncResponse {
        fun onFinishPush(ouput: String)
    }

    fun log(s:String){
        Log.e("PUSHNOTICE", s)
    }
}