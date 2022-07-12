package bubasara.quizypeasy.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.ActivityMainBinding
import bubasara.quizypeasy.models.QuizyPeasyRoomDatabase

class MainActivity : FragmentActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.item_category)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        /*//  instantiate the database
        val database : QuizyPeasyRoomDatabase by lazy { QuizyPeasyRoomDatabase.getDatabase(this) }*/
    }
}