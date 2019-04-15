package com.diegobiazin.firebasenotification.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.diegobiazin.firebasenotification.MainActivity
import com.diegobiazin.firebasenotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage?.data!!.isNotEmpty()) {
            val msg = remoteMessage.data["mensagem"]
            val titulo = remoteMessage.data["titulo"]
            val nome = remoteMessage.data["nome"]
            val urlImagem = remoteMessage.data["urlImagem"]

            val mensagem = "$msg -- $nome -- $urlImagem"

            sendNotification_1(titulo, mensagem)
        }

        if (remoteMessage.notification != null) {
            Log.d("diegobiazin.com", remoteMessage.notification?.title)
            Log.d("diegobiazin.com", remoteMessage.notification?.body)

            val titulo: String? = remoteMessage.notification?.title
            val msg: String? = remoteMessage.notification?.body

            sendNotification_1(titulo, msg)
        }
    }

    private fun sendNotification_1(titulo: String?, msg: String?) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channel = getString(R.string.default_notification_channel_id)

        val sound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(this, channel)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(titulo)
            .setContentText(msg)
            .setSound(sound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channel, "channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, notification.build())
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
    }
}