package bubasara.quizypeasy.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentHomescreenBinding

class HomescreenFragment : Fragment(R.layout.fragment_homescreen) {

    private var _binding : FragmentHomescreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomescreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click on Play button -> go to Choose Categories fragment
        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_homescreenFragment_to_chooseCategoriesFragment)
        }

        //click on How to play button -> go to How to play dialog
        binding.btnHowToPlay.setOnClickListener {
            findNavController().navigate(R.id.action_homescreenFragment_to_howToPlayDialog)
        }

        //click on Privacy policy button -> go to URL
        binding.btnPrivacyPolicy.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW)
            urlIntent.data = Uri.parse("https://www.freeprivacypolicy.com/live/40a38beb-057f-40fc-b3bf-27f54cbf1bcf")
            startActivity(urlIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}