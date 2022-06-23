package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}