package edu.wsc2022.a01.mrtranslate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.wsc2022.a01.mrtranslate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}