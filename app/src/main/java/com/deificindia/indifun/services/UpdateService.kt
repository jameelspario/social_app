package com.deificindia.indifun.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import androidx.core.app.JobIntentService
import com.deificindia.indifun.Utility.Logger.loge
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.util.zip.ZipInputStream

private const val ACTION_FOO = "com.deificindia.indifun.Utility.action.FOO"
private const val ACTION_DOWNLOAD = "com.deificindia.indifun.Utility.action.ACTION_DOWNLOAD"

private const val EXTRA_PARAM0 = "com.deificindia.indifun.Utility.extra.PARAM0"
private const val EXTRA_PARAM1 = "com.deificindia.indifun.Utility.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.deificindia.indifun.Utility.extra.PARAM2"

const val UPDATE_PROGRESS = 8344
const val UPDATE_PROGRESSZIP = 8345
const val UPDATE_PROGRESSGIF = 8346

class UpdateService : JobIntentService() {

    private val BUFFER_SIZE = 4096

    override fun onHandleWork(intent: Intent) { onHandleIntent(intent) }

    fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FOO -> {
                val param1_bundle = intent.getBundleExtra(EXTRA_PARAM1)
                val receiver = intent.getParcelableExtra<ResultReceiver>(EXTRA_PARAM2)
                previewTest(param1_bundle, receiver)
            }
            ACTION_DOWNLOAD->{
                val param1_bundle = intent.getBundleExtra(EXTRA_PARAM1)
                val receiver = intent.getParcelableExtra<ResultReceiver>(EXTRA_PARAM2)
                donwloadOnly(param1_bundle, receiver)
            }
        }
    }

    companion object {

        @JvmStatic
        fun enqueueWork(context: Context, bundle: Bundle, param2: ResultReceiver) {
            loge("UpdateService","enqueueWork")
            val intent = Intent(context, UpdateService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, bundle)
                putExtra(EXTRA_PARAM2, param2)
            }

            enqueueWork(context, UpdateService::class.java, 1, intent)
        }

        @JvmStatic
        fun enqueueDownload(context: Context, bundle: Bundle, param2: ResultReceiver) {
            val intent = Intent(context, UpdateService::class.java).apply {
                action = ACTION_DOWNLOAD
                putExtra(EXTRA_PARAM1, bundle)
                putExtra(EXTRA_PARAM2, param2)
            }

            enqueueWork(context, UpdateService::class.java, 1, intent)
        }
    }

    private fun donwloadOnly(bundle: Bundle?, receiver: ResultReceiver?){
        val parentPath = "/data/data/${applicationContext.getPackageName()}/"
        val gameDir = File("$parentPath/gifts")
        gameDir.mkdirs()

        val namewithextension = bundle?.getString("namewithextension")
        val fulllink = bundle?.getString("fulllink")

        val filePath = "${parentPath}gifts/$namewithextension"
        loge("UpdateService donwloadOnly ",filePath, fulllink)
        val theFile = File(filePath)
        if(theFile.exists()){
            bundle?.putSerializable("file", theFile)
            receiver?.send(UPDATE_PROGRESSGIF, bundle)
        }else{
            handleActionAction(namewithextension, filePath, fulllink, receiver, object :OnDownloadFinish{

                override fun downloadFinish(namewithextension: String?, rcvr: ResultReceiver?) {
                    bundle?.putSerializable("file", theFile)
                    rcvr?.send(UPDATE_PROGRESSGIF, bundle)
                }
            })
        }
    }

    private fun previewTest(bundle: Bundle?, receiver: ResultReceiver?){
        val parentPath = "/data/data/${applicationContext.getPackageName()}/"

        val namewithextension = bundle?.getString("namewithextension")
        val fulllink = bundle?.getString("fulllink")


        val zipPath = "${parentPath}gifts/$namewithextension" //*.zip
        val extractedFolder = zipPath.replace(".zip", "")

        loge("UpdateService", namewithextension, parentPath, zipPath, extractedFolder, applicationContext.filesDir.path)

        val extractedFile = File(extractedFolder)
        val zipfolder = File(zipPath)

        val gameDir = File("$parentPath/gifts")
        gameDir.mkdirs()

        if(extractedFile.exists()){
            //read files
            loge("UpdateService read files")
            sendResult("Exist", extractedFolder, bundle, receiver)
        }else {
            if (zipfolder.exists()) {
                //unzip
                loge("UpdateService unzip")
                unzip(zipPath, extractedFolder)
                sendResult("Exist", extractedFolder, bundle, receiver)
            } else {
                //download
                loge("UpdateService download")
                if(!fulllink.isNullOrEmpty())
                    handleActionAction(namewithextension, zipPath, fulllink, receiver, object :OnDownloadFinish{

                        override fun downloadFinish(filewithextension: String?, rcvr: ResultReceiver?) {
                            previewTest(bundle,  rcvr)
                        }
                    })
            }
        }
    }

    @Throws(IOException::class)
    fun unzip(zipFilePath: String, destDirectory: String) {
        val destDir = File(destDirectory)
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        val zipIn = ZipInputStream(FileInputStream(zipFilePath))
        var entry = zipIn.nextEntry
        // iterates over entries in the zip file
        while (entry != null) {
            val filePath = destDirectory + File.separator + entry.name
            if (!entry.isDirectory) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath)
            } else {
                // if the entry is a directory, make the directory
                val dir = File(filePath)
                dir.mkdirs()
            }
            zipIn.closeEntry()
            entry = zipIn.nextEntry
        }
        zipIn.close()
        File(zipFilePath).delete()
        //zipfilePath1.delete()
    }

    @Throws(IOException::class)
    private fun extractFile(zipIn: ZipInputStream, filePath: String) {
        val bos = BufferedOutputStream(FileOutputStream(filePath))
        val bytesIn = ByteArray(BUFFER_SIZE)
        var read = 0
        while (zipIn.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }

    private fun handleActionAction(filewithextension: String?, outPath: String?, urlToDownload: String?,
                                receiver: ResultReceiver?, listener:OnDownloadFinish?) {
        //val urlToDownload: String = intent.getStringExtra("url")
        //val receiver: ResultReceiver = intent.getParcelableExtra("receiver") as ResultReceiver

        try {

            //create url and connect
            val url = URL(urlToDownload)
            val connection: URLConnection = url.openConnection()
            connection.connect()

            // this will be useful so that you can show a typical 0-100% progress bar
            val fileLength: Int = connection.getContentLength()

            // download the file
            val input: InputStream = BufferedInputStream(connection.getInputStream())
            val path = outPath //"/sdcard/g.apk"
            val output: OutputStream = FileOutputStream(path)
            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int
            /*while (input.read(data).also { count = it } != -1) {
                total += count.toLong()

                // publishing the progress....
                val resultData = Bundle()
                resultData.putInt("progress", (total * 100 / fileLength).toInt())
                receiver?.send(UPDATE_PROGRESS, resultData)
                output.write(data, 0, count)
            }*/

            while (true) {
                count = input.read(data)
                total += count.toLong()

                // publishing the progress....
                val resultData = Bundle()
                resultData.putInt("progress", (total * 100 / fileLength).toInt())
                receiver?.send(UPDATE_PROGRESS, resultData)

                if (count <= 0)
                    break

                output.write(data, 0, count)
            }

            // close streams
            output.flush()
            output.close()
            input.close()
        } catch (e: IOException) {
            loge("UpdateService handleActionFoo" + e.message)
            e.printStackTrace()
        }

        val resultData = Bundle()
        resultData.putInt("progress", 100)
        receiver?.send(UPDATE_PROGRESS, resultData)

        listener?.downloadFinish(filewithextension, receiver)

    }

    fun sendResult(str: String, str2: String, bundle: Bundle?, receiver: ResultReceiver?){
        //val resultData = Bundle()
        bundle?.putString("message", str)
        bundle?.putString("extractedFolder", str2)
        receiver?.send(UPDATE_PROGRESSZIP, bundle)
    }

    interface OnDownloadFinish{
        fun downloadFinish(filewithextension: String?, rcvr: ResultReceiver?)
    }



}