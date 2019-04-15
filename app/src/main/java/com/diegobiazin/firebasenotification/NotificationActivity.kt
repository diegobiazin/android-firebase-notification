package com.diegobiazin.firebasenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val url = intent.getStringExtra("url")
        val msg = intent.getStringExtra("mensagem")

        exibirNotificacao(url, msg)
    }

    private fun exibirNotificacao(url: String?, msg: String?) {

        Picasso.get().load(url).into(imageView_Notification)

        textView_Notification.text = msg
    }
}
