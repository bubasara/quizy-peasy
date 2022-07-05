package bubasara.quizypeasy.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.DialogDeleteCategoryBinding
import bubasara.quizypeasy.viewmodels.SharedViewModel

class DeleteCategoryDialog : DialogFragment(R.layout.dialog_delete_category) {

    private var _binding : DialogDeleteCategoryBinding? = null
    private val binding get() = _binding!!

    val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDeleteCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  click on button YES -> set live data value to yes as a flag
            that category should be deleted */
        binding.btnYes.setOnClickListener {
            sharedViewModel.categoryDeleteLiveDataBoolean.value = true
            /*  pop back stack to return to previous fragment
                instead of navigate (which would create new fragment)   */
            findNavController().popBackStack()
        }

        binding.btnNo.setOnClickListener {
            //  no need to set live data value to false
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