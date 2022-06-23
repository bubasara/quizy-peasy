package bubasara.quizypeasy.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.DialogEditDeleteQuestionBinding

class EditDeleteQuestionDialog : DialogFragment(R.layout.dialog_edit_delete_question) {

    private var _binding : DialogEditDeleteQuestionBinding? = null
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
        _binding = DialogEditDeleteQuestionBinding.inflate(inflater, container, false)
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