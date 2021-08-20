package com.example.phonemic

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE: Int = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var counter = 0
        val micButton:ImageButton = findViewById(R.id.micButton)
        val statusRing:ImageView = findViewById(R.id.status_ring)
        var messageTextView:EditText = findViewById(R.id.message_text_view)

        var workerDataBuilder= Data.Builder()

        lateinit var connectionIntent: Intent
        micButton.setOnClickListener {
            counter++
            statusRing.setImageLevel(counter%3)
            var workerData:Data = workerDataBuilder
                .putString("Message",messageTextView.text.toString()).build()
            val socketWorkerRequest: WorkRequest =
                OneTimeWorkRequestBuilder<SocketWorker>()
                    .setInputData(workerData)
                    .build()
            WorkManager.getInstance(this.applicationContext).enqueue(socketWorkerRequest)
        }



    }


}