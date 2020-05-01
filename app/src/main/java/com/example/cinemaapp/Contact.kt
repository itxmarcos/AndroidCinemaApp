package com.example.cinemaapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat

class Contact : AppCompatActivity() {
    val REQUEST_PHONE_CALL=1

    val txtCall=findViewById<EditText>(R.id.txt_phone)
    val txtWeb=findViewById<EditText>(R.id.txt_web)
    val txtMail=findViewById<EditText>(R.id.txt_web)

    val btnCall=findViewById<Button>(R.id.btn_call)
    val btnWeb=findViewById<Button>(R.id.btn_web)
    val btnMail=findViewById<Button>(R.id.btn_mail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val intentB=getIntent() //get intent object and data
        val id=intentB.getStringExtra("ID")

        btnCall.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            } else {
                startCall()
            }
        }

        btnMail.setOnClickListener {
            val intentMail = Intent(Intent.ACTION_SEND)
            intentMail.type="text/html"
            intentMail.putExtra(Intent.EXTRA_EMAIL,arrayOf<String>(txtMail.toString()))
            intentMail.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            intentMail.putExtra(Intent.EXTRA_TEXT, "Text message")
            startActivity(intentMail)
        }

        btnWeb.setOnClickListener {
            val uri = Uri.parse( txtWeb.toString() )
            startActivity( Intent( Intent.ACTION_VIEW,uri ) )
            //setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(Uri.parse("Phone number:" + txtCall))
        startActivity(callIntent)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_PHONE_CALL) startCall()
    }
}
