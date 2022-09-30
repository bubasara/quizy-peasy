package bubasara.quizypeasy.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bubasara.quizypeasy.R
import bubasara.quizypeasy.databinding.FragmentSplashscreenBinding

class SplashscreenFragment : Fragment(R.layout.fragment_splashscreen) {

    private var _binding : FragmentSplashscreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var logo : ImageView
    private lateinit var squareRotated : ImageView
    private lateinit var square : ImageView
    private lateinit var triangle : ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logo = binding.imgLogo
        squareRotated = binding.imgSquareRotated
        square = binding.imgSquare
        triangle = binding.imgTriangle

        enlargeLogo(logo)
        loading(square)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View, shouldNavigateToHome : Boolean) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
                if(shouldNavigateToHome)
                    findNavController().navigate(R.id.action_splashscreenFragment_to_homescreenFragment)
            }

        })
    }

    private fun enlargeLogo(element : ImageView, shouldNavigateToHome : Boolean = false) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(element, scaleX, scaleY)
        animator.duration = 1000
        animator.disableViewDuringAnimation(requireView(), shouldNavigateToHome)
        animator.start()
    }

    private fun loading(element : ImageView, shouldNavigateToHome : Boolean = false) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f)
        val animatorEnlarge = ObjectAnimator.ofPropertyValuesHolder(element, scaleX, scaleY)
        val animatorRotate = ObjectAnimator.ofFloat(element , "rotation", 0f, 360f);
        animatorEnlarge.duration = 500
        animatorEnlarge.repeatCount = 1
        animatorEnlarge.repeatMode = ObjectAnimator.REVERSE
        animatorRotate.duration = 1000
        animatorEnlarge.disableViewDuringAnimation(requireView(), shouldNavigateToHome)
        animatorEnlarge.doOnEnd {

            when (element) {
                square -> loading(squareRotated)
                squareRotated -> loading(triangle, true)
            }
        }
        animatorEnlarge.start()
        animatorRotate.start();
    }
}