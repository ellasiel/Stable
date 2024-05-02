package com.eltex.shultestable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eltex.shultestable.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}