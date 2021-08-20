package com.example.phonemic;

import android.app.IntentService
import android.content.Intent
import androidx.core.app.JobIntentService
import java.io.*
import java.net.InetAddress
import java.net.Socket

class ClientThread : JobIntentService() {
    lateinit var input: BufferedReader
    lateinit var output: PrintWriter
    lateinit var socket: Socket

    override fun onHandleWork(intent: Intent) {
        var ip=intent.getStringExtra("SERVER_ID")
        var port = intent.getIntExtra("PORT",5050)
        this.socket= Socket(ip,port)
        this.output= PrintWriter(this.socket.getOutputStream())
        try {

            this.output.write("Here is Client");
            this.output.flush()
            this.socket.close()

        }catch (err:IOException){
            err.printStackTrace()
        }

    }
    override fun onStopCurrentWork(): Boolean {
        return super.onStopCurrentWork()
    }

}