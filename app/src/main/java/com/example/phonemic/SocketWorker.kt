package com.example.phonemic

import android.annotation.SuppressLint
import android.content.Context
import android.media.*
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.BufferedOutputStream
import java.io.IOException
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket


class SocketWorker(appContext: Context, params:WorkerParameters):Worker(appContext,params) {


    override fun doWork(): Result {

        startRecording()

        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun startRecording() {
        val frequency=11025
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioEncoding = AudioFormat.ENCODING_PCM_16BIT
        val bufferSize =  AudioRecord.getMinBufferSize(frequency, channelConfig, audioEncoding)
        val audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,frequency,channelConfig,audioEncoding,bufferSize)
        audioRecord?.startRecording()
        var socket = Socket(InetAddress.getByName(IP),PORT)
        var output = BufferedOutputStream(socket.getOutputStream())

        val byteArray = ByteArray(bufferSize)
        while (audioRecord.recordingState==AudioRecord.RECORDSTATE_RECORDING){
            audioRecord.read(byteArray,0,bufferSize)
            audioRecord.stop()
            try {
                if(audioRecord.recordingState==AudioRecord.RECORDSTATE_STOPPED){
                    println("recording stopped")
                    output.write(byteArray)
                    output.flush()
                    socket.close()
                }
            }catch (err:IOException){
                println(err.stackTrace);
            }


        }
    }


    private fun connectToServer() {
        print("connecting to Server")


    }
    companion object{
        private const val IP="192.168.1.60"
        private const val PORT=3000
    }
}