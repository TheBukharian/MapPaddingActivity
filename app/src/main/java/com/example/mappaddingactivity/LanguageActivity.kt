package com.example.mappaddingactivity

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class LanguageActivity :  AppCompatActivity(),BottomSheetDialog.ItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        loadLocate()


        val langbtn = findViewById<Button>(R.id.languageBtn)
        langbtn.setOnClickListener {

            supportFragmentManager.let {
                BottomSheetDialog.newInstance(Bundle()).apply {
                    show(it, tag)
                }

            }
        }







    }



    override fun onItemClick() {
        loadLocate()
        recreate()
    }


     fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang","")
        setLocate(language!!)


    }
    fun setLocate(Lang: String) {

        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor: SharedPreferences.Editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

}