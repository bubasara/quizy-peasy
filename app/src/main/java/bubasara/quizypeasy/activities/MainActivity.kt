package bubasara.quizypeasy.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import bubasara.quizypeasy.R

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_leave)
    }
}