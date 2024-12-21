package com.deificindia.indifun.deificpk.frags

import com.deificindia.indifun.db.EntityCall

interface OnResultLiveEntity<T>{
    fun onResult(res: List<T>?)
}

interface OnStartLocalRender{
    fun callAdapterClickListener(what:Int, trac: EntityCall)
    fun onRemovedCallAt(index:Long)
    fun callAccepted(index: Long, userType: Int, fuid:String)
}
