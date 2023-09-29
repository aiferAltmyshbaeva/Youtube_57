package pl.aifer.youtube_sandbox_m6_l3.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.aifer.youtube_sandbox_m6_l3.BuildConfig
import pl.aifer.youtube_sandbox_m6_l3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BuildConfig.API_KEY
        BuildConfig.BASE_URL
    }
}