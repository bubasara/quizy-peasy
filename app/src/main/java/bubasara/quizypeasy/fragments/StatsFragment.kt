package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentStatsBinding

class StatsFragment : Fragment(R.layout.fragment_stats) {

    private var _binding : FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click on Play again button -> go to Choose Categories fragment
        binding.btnPlayAgain.setOnClickListener {
            findNavController().navigate(R.id.action_statsFragment_to_chooseCategoriesFragment)
        }

        //click on Quit button -> go to Leave dialog
        binding.btnQuit.setOnClickListener {
            findNavController().navigate(R.id.action_statsFragment_to_leaveDialog)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}