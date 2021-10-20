package com.gabrielsotelo.mtc.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabrielsotelo.mtc.AppApplication
import com.gabrielsotelo.mtc.R
import com.gabrielsotelo.mtc.database.Question
import com.gabrielsotelo.mtc.databinding.FragmentDashboardBinding
import com.gabrielsotelo.mtc.databinding.FragmentNotificationsBinding
import com.gabrielsotelo.mtc.model.AppViewModel
import com.gabrielsotelo.mtc.model.AppViewModelFactory
import com.gabrielsotelo.mtc.ui.dashboard.DashboardViewModel
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class DashboardFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels{
        AppViewModelFactory(
            (activity?.application as AppApplication).userDatabase.userDao(),
            (activity?.application as AppApplication).questionDatabase.questionDao()
        )
    }
    private var binding: FragmentDashboardBinding? = null
    var currentIndex: Int = 1
    lateinit var currentAnswer: String
    lateinit var currentQuestion: Question
    lateinit var indexes: MutableList<Int>
    var counter: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        reset_practice()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.retrieveQuestion(currentIndex).observe(this.viewLifecycleOwner) {selected ->
            currentQuestion = selected
            currentAnswer = currentQuestion.respuesta
            bind()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun bind(){
        binding?.apply{
            viewModel = sharedViewModel
            dashboardFragment = this@DashboardFragment
            navigateBack.setOnClickListener {
                navBack()
            }
            navigateNext.setOnClickListener {
                navNext()
                sharedViewModel.updateUser(0,1,0,0)
            }
        }
        bind_question()
    }

    private fun bind_question(){
        sharedViewModel.retrieveQuestion(currentIndex).observe(this.viewLifecycleOwner) {selected ->
            currentQuestion = selected
            currentAnswer = currentQuestion.respuesta
            binding?.apply{
                questionCard.text = currentQuestion.pregunta
                choiceCardA.text = currentQuestion.a
                choiceCardB.text = currentQuestion.b
                choiceCardC.text = currentQuestion.c
                choiceCardD.text = currentQuestion.d
            }
            if (currentQuestion.image == 1){
                set_image()
            } else {
                binding?.questionImage?.setImageDrawable(null)
            }
        }
    }

    private fun reset_practice(){
        indexes = (1..200).shuffled().toMutableList()
        counter = 0
        currentIndex = indexes[counter]
    }

    private fun num_check(): Boolean{
        return counter in 0..199
    }

    private fun set_image(){
        val storageRef = FirebaseStorage.getInstance().reference.child("A1/$currentIndex.jpg")
        val localFile = File.createTempFile("temp","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding?.questionImage?.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(context, "Error en conexión", Toast.LENGTH_SHORT).show()
        }
    }

    fun verifyChoice(button: View, choice: Char){
        if (choice.toString().equals(currentAnswer, true)){
            sharedViewModel.updateUser(1,1,0,0)
            button.background.setTint(resources.getColor(R.color.teal_200))
            Handler().postDelayed(Runnable {
                button.background.setTint(resources.getColor(R.color.white))
                navNext()
            }, 1000)
        } else {
            sharedViewModel.updateUser(0,1,0,0)
            button.background.setTint(resources.getColor(R.color.purple_700))
            vibratePhone()
            Handler().postDelayed(Runnable {
                button.background.setTint(resources.getColor(R.color.white))
            }, 1000)
        }
    }

    private fun navBack(){
        counter -= 1
        if (num_check()){
            currentIndex = indexes[counter]
            bind_question()
        } else {
            counter = 0
        }
    }

    private fun navNext(){
        counter += 1
        if (num_check()){
            currentIndex = indexes[counter]
            bind_question()
        } else {
            reset_practice()
            bind_question()
        }
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
}