package pl.aifer.youtube_sandbox_m6_l3.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.aifer.youtube_sandbox_m6_l3.core.network.RemoteDataSource
import pl.aifer.youtube_sandbox_m6_l3.core.network.RetrofitClient
import pl.aifer.youtube_sandbox_m6_l3.databinding.ActivityMainBinding
import pl.aifer.youtube_sandbox_m6_l3.domain.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf(R.id.playlistItemsFragment),
//            fallbackOnNavigateUpListener = ::onSupportNavigateUp
//        )
//        binding.appBar.setupWithNavController(navController, appBarConfiguration)
//
    }

    companion object {
        private val retrofitClient = RetrofitClient().createApiService()
        private val remoteDataSource = RemoteDataSource(retrofitClient)
        internal val repository = Repository(remoteDataSource)
    }
}