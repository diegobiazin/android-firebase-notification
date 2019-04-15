package com.diegobiazin.firebasenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.diegobiazin.firebasenotification.util.Util
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    private fun setListeners() {
        switch_Sports.setOnClickListener(this)
        switch_Politica.setOnClickListener(this)
        button_CadastrarToken.setOnClickListener(this)
        button_EnviarNotificacao.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.switch_Sports -> {
                subscribeSport()
            }
            R.id.switch_Politica -> {
                subscribePolitica()
            }
            R.id.button_CadastrarToken -> {
                Toast.makeText(this, "Button Cadastrar Token", Toast.LENGTH_LONG).show()
            }
            R.id.button_EnviarNotificacao -> {
                Toast.makeText(this, "Button Enviar Notificação", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun subscribeSport() {
        if (Util.statusInternet_MoWi(baseContext))
            notificaSubscribeSport()
        else
            Toast.makeText(this, "Erro - Sem conexão com a internet - Verifique o Wifi ou 3G", Toast.LENGTH_LONG).show()
    }

    private fun notificaSubscribeSport() {
        val statusSports: Boolean = switch_Sports.isChecked

        if (statusSports) {
            FirebaseMessaging.getInstance().subscribeToTopic("sports").addOnCompleteListener {
                if (it.isComplete)
                    Toast.makeText(this, "Inscrição Sports Realizada com Sucesso", Toast.LENGTH_LONG).show()
                else {
                    switch_Sports.isChecked = false
                    Toast.makeText(this, "Erro - Inscrição Sports não sucedida", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("sports").addOnCompleteListener {
                if (it.isComplete)
                    Toast.makeText(this, "Inscrição Sports Cancelada com Sucesso", Toast.LENGTH_LONG).show()
                else {
                    switch_Sports.isChecked = false
                    Toast.makeText(this, "Erro - Cancelamento Inscrição Sports não sucedida", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun notificaSubscribePolitica() {
        val statusPolitica: Boolean = switch_Politica.isChecked

        if (statusPolitica) {
            FirebaseMessaging.getInstance().subscribeToTopic("politica").addOnCompleteListener {
                if (it.isComplete)
                    Toast.makeText(this, "Inscrição Politica Realizada com Sucesso", Toast.LENGTH_LONG).show()
                else {
                    switch_Politica.isChecked = false
                    Toast.makeText(this, "Erro - Inscrição Politica não sucedida", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("politica").addOnCompleteListener {
                if (it.isComplete)
                    Toast.makeText(this, "Inscrição Politica Cancelada com Sucesso", Toast.LENGTH_LONG).show()
                else {
                    switch_Politica.isChecked = false
                    Toast.makeText(this, "Erro - Cancelamento Inscrição Politica não sucedida", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun subscribePolitica() {
        if (Util.statusInternet_MoWi(baseContext))
            notificaSubscribePolitica()
        else
            Toast.makeText(this, "Erro - Sem conexão com a internet - Verifique o Wifi ou 3G", Toast.LENGTH_LONG).show()
    }
}
