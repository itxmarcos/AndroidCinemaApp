package com.example.cinemaapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_contact.*

class Contact : AppCompatActivity() {
    private var call: Button? = null
    private var send: Button? = null
    private var open: Button? = null
    private var Number: String? = null
    private var Mail: String? = null
    private var URL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        call = findViewById(R.id.btn_call) as Button
        send = findViewById(R.id.btn_mail) as Button
        open = findViewById(R.id.btn_web) as Button

        call!!.setOnClickListener()
        {
            Number = txt_phone?.text.toString()
            if (Number != "") {
                try {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$Number"))
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        send!!.setOnClickListener()
        {
            Mail = txt_mail?.text.toString()
            if (Mail != "") {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", Mail, null
                    )
                )
                startActivity(Intent(emailIntent))
            }
        }

        open!!.setOnClickListener()
        {
            URL = txt_web?.text.toString()
            if (URL != "") {
                val uris = Uri.parse(URL)
                val intents = Intent(Intent.ACTION_VIEW, uris)
                startActivity(intents)
            }
        }
    }
}