package bubasara.quizypeasy.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.DialogCreateNewQuestionBinding

class CreateNewQuestionFragment : DialogFragment(R.layout.dialog_create_new_question) {

    private var _binding : DialogCreateNewQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCreateNewQuestionBinding.inflate(inflater, container, false)
        return binding.root
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