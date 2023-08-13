package fr.isen.francoisyatta.projectv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_anomalies.*



class anomalies : AppCompatActivity() {

    private val CHANNEL_ID= "Channel_id_1"
    private val notificationsId= 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anomalies)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        // get data from putExtra intent
        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")

        //set title in another activity
        actionBar.setTitle(aTitle)
        //a_title.text = aTitle

        createNotificationChannel()

        notif_button.setOnClickListener {
            sendNotification()
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Alerte !!"
            val descriptionText = "Une anomalie est détecté à votre tuyau"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description= descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(){

        val builder = NotificationCompat.Builder(this, CHANNEL_ID )
            .setSmallIcon(R.drawable.warning)
            .setContentTitle("Alerte !!")
            .setContentText("Une anomalie est détecté à votre tuyau")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)){

            notify(notificationsId, builder.build())
        }
    }
}