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
        val frequency=22050
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioEncoding = AudioFormat.ENCODING_PCM_16BIT
        val bufferSize =  AudioRecord.getMinBufferSize(frequency, channelConfig, audioEncoding)
        val audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,frequency,channelConfig,audioEncoding,bufferSize)
        audioRecord.startRecording()
        val socket = Socket(IP,PORT)
        val output = BufferedOutputStream(socket.getOutputStream())
        val byteArray = ByteArray(bufferSize)
        var stepSize=0
        while (audioRecord.recordingState==AudioRecord.RECORDSTATE_RECORDING&&stepSize<20){
            println("BufferSize:"+bufferSize)
            audioRecord.read(byteArray,0,bufferSize)
            try {
                    println("recording stopped"+(byteArray[0]))
                    output.write(byteArray)
                    output.flush()
            }catch (err:IOException){
                println(err.stackTrace);
            }
            stepSize++;
        }
        socket.close()
        audioRecord.stop()
    }


    private fun connectToServer() {
        print("connecting to Server")


    }
    companion object{
        private const val IP="192.168.1.60"
        private const val PORT=3000
    }
}