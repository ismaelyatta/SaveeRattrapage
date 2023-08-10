package fr.isen.francoisyatta.projectv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class aide : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aide)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        // get data from putExtra intent
        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")

        //set title in another activity
        actionBar.setTitle(aTitle)
        //a_title.text = aTitle
    }
}