package com.deificindia.indifun.Utility

import android.content.SharedPreferences
import com.deificindia.indifun.db.TempDao

const val MYDATAS = "com.deificindia.indifun.mydatas"
const val UsrXp = "com.deificindia.indifun.mydatas.UsrXp"
const val BroadXp = "com.deificindia.indifun.mydatas.BroadXp"
const val BroadLevel = "com.deificindia.indifun.mydatas.BroadLevel"
const val BroadHeart = "com.deificindia.indifun.mydatas.BroadHeart"

fun __getMyDatasRef():SharedPreferences
   = IndifunApplication.getInstance().getmMyDatas()

fun __tempDao():TempDao
    = IndifunApplication.getInstance().tempDao

fun String.saveLong(v:Long){ __getMyDatasRef().edit().putLong(this, v).apply() }
fun String.getLong():Long = __getMyDatasRef().getLong(this, 0L)

fun Long.saveXp(){ UsrXp.saveLong( this) }
fun getXp():Long = UsrXp.getLong()

fun Long.saveBroadXp(){ BroadXp.saveLong( this) }
fun getBroadXp():Long = BroadXp.getLong()

fun Long.saveBroadLevel(){ BroadLevel.saveLong( this) }
fun getBroadLevel():Long = BroadLevel.getLong()

fun Long.saveBroadHeart(){ BroadHeart.saveLong( this) }
fun getBroadHeart():Long = BroadHeart.getLong()

