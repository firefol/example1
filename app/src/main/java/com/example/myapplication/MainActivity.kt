package com.example.myapplication

import android.R
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        //bitmap.eraseColor(Color.argb((255 - 99).toFloat(), 0F, 0F, 0F))
        /* for (i in 0..99) {
             for (j in 0 .. 99) {
                 bitmap.setPixel(j, i, Color.argb((255 - Random.nextInt(50,99).toFloat()), 0F, 0F, 0F))
                 val width = bitmap.width
                 binding.imageView2.setImageBitmap(bitmap)
             }
         }*/


    }
}