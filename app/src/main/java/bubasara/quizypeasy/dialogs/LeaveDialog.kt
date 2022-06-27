package bubasara.quizypeasy.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.DialogLeaveBinding
import kotlin.math.log
import kotlin.system.exitProcess

class LeaveDialog : DialogFragment(R.layout.dialog_leave) {

    private var _binding : DialogLeaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLeaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click on Yes button -> quit app
        binding.btnYes.setOnClickListener {
            activity?.finish();
            Log.i("Sara", "all good")
            exitProcess(0);
        }

        /*  click on No button
            if previously on Stats fragment -> go to Stats fragment
            if previously on Gameplay fragment -> go to Gameplay fragment
         */
        binding.btnNo.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}