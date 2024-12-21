package com.spario.lib.fcm

interface PushService {
    fun sendToToken(token: String)
}