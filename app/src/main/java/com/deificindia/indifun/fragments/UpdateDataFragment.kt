package com.deificindia.indifun.fragments

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import androidx.fragment.app.Fragment
import com.deificindia.indifun.R
import com.deificindia.indifun.services.UPDATE_PROGRESS
import com.deificindia.indifun.services.UpdateService
import kotlinx.android.synthetic.main.fragment_update_data.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpdateDataFragment(handler: Handler?) : Fragment(R.layout.fragment_update_data) {
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun startUpdate(){
        context?.let {
            //UpdateService.enqueueWork(it, "","", resultreceiver)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(handler: Handler, param1: String, param2: String) =
                UpdateDataFragment(handler).apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    val resultreceiver: ResultReceiver = object: ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == UPDATE_PROGRESS) {

                val progress = resultData?.getInt("progress")?:0 //get the progress
                progresssbar1.setProgress(progress)
                text.setText("Downloading Updates...")

                if (progress == 100) {
                    progresssbar1.setProgress(100)
                    text.setText("Update Completed...")
                }
            }

        }
    }


}