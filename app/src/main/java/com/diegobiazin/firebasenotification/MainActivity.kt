package com.diegobiazin.firebasenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
                val statusSports: Boolean = switch_Sports.isChecked
                Toast.makeText(this, "Switch Sports $statusSports", Toast.LENGTH_LONG).show()
            }
            R.id.switch_Politica -> {
                val statusPolitica: Boolean = switch_Politica.isChecked
                Toast.makeText(this, "Switch Política $statusPolitica", Toast.LENGTH_LONG).show()
            }
            R.id.button_CadastrarToken -> {
                Toast.makeText(this, "Button Cadastrar Token", Toast.LENGTH_LONG).show()
            }
            R.id.button_EnviarNotificacao -> {
                Toast.makeText(this, "Button Enviar Notificação", Toast.LENGTH_LONG).show()
            }
        }
    }

}
