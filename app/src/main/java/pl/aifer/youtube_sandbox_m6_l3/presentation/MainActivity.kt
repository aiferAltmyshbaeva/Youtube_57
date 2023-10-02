package pl.aifer.youtube_sandbox_m6_l3.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.aifer.youtube_sandbox_m6_l3.R
import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource
import pl.aifer.youtube_sandbox_m6_l3.core.network.RetrofitClient
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private val retrofitClient = RetrofitClient().createApiService()
        private val remoteDataSource = RemoteDataSource(retrofitClient)
        internal val repository = Repository(remoteDataSource)
    }
}