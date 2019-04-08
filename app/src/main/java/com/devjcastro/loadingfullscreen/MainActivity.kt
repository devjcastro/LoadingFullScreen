package com.devjcastro.loadingfullscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devjcastro.loadingfullscreen.params.LoadingDefaultParams

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val params = LoadingDefaultParams().apply {
            description = "We are configuring the application, please wait a moment!"
            backgroundTransparency = 0.9f
        }
        //LoadingFS.show(this, params)


        LoadingFS.builder(LoadingType.DEFAULT, params)
        LoadingFS.show(this)
        //(LoadingFS.instance as DefaultLoading).viewDescription?.text = "Estamos configurando la aplicación, por favor espere un momento!"
    }
}
