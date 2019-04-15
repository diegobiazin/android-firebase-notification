package com.diegobiazin.firebasenotification.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.diegobiazin.firebasenotification.MainActivity
import com.diegobiazin.firebasenotification.NotificationActivity
import com.diegobiazin.firebasenotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Picasso

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage?.data!!.isNotEmpty()) {
            val msg = remoteMessage.data["mensagem"]
            val titulo = remoteMessage.data["titulo"]
            val nome = remoteMessage.data["nome"]
            val urlImagem = remoteMessage.data["urlImagem"]

            val mensagem = "$msg -- $nome -- $urlImagem"

            sendNotification_2(titulo, mensagem, urlImagem)
        } else if (remoteMessage.notification != null) {
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

    private fun sendNotification_2(titulo: String?, msg: String?, url: String?) {

        Glide.with(baseContext).asBitmap().load(url).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                bitmap: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val intent: Intent = Intent(baseContext, NotificationActivity::class.java)
                intent.putExtra("url", url)
                intent.putExtra("mensagem", msg)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val channel = getString(R.string.default_notification_channel_id)

                val sound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                val notification = NotificationCompat.Builder(baseContext, channel)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(titulo)
                    .setContentText(msg)
                    .setSound(sound)
                    .setAutoCancel(true)
                    .setLargeIcon(bitmap)
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
//                    .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentIntent(pendingIntent)

                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationChannel =
                        NotificationChannel(channel, "channel", NotificationManager.IMPORTANCE_DEFAULT)
                    notificationManager.createNotificationChannel(notificationChannel)
                }

                notificationManager.notify(0, notification.build())

                return false
            }
        }).submit()

//        val bitMap : Bitmap = Picasso.get().load(url).get()

    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
    }
}