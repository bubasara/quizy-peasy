package bubasara.quizypeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentStatsBinding
import bubasara.quizypeasy.viewmodels.SharedViewModel

class StatsFragment : Fragment(R.layout.fragment_stats) {

    private var _binding : FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Toast.makeText(requireContext(), "No turning back, sorry!", Toast.LENGTH_SHORT).show()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click on Play again button -> go to Choose Categories fragment
        binding.btnPlayAgain.setOnClickListener {
            sharedViewModel.resetListOfCheckedCategories()
            findNavController().popBackStack(R.id.chooseCategoriesFragment, false)
            //findNavController().navigate(R.id.action_statsFragment_to_chooseCategoriesFragment)
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