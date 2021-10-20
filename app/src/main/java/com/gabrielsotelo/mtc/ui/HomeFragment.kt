package com.gabrielsotelo.mtc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gabrielsotelo.mtc.AppApplication
import com.gabrielsotelo.mtc.R
import com.gabrielsotelo.mtc.database.User
import com.gabrielsotelo.mtc.database.getFormattedPracticeAccuracy
import com.gabrielsotelo.mtc.database.getFormattedTestAccuracy
import com.gabrielsotelo.mtc.databinding.FragmentHomeBinding
import com.gabrielsotelo.mtc.model.AppViewModel
import com.gabrielsotelo.mtc.model.AppViewModelFactory
import com.gabrielsotelo.mtc.ui.home.HomeViewModel
import kotlin.math.round

class HomeFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels{
        AppViewModelFactory(
            (activity?.application as AppApplication).userDatabase.userDao(),
            (activity?.application as AppApplication).questionDatabase.questionDao()
        )
    }
    private var binding: FragmentHomeBinding? = null
    companion object {
        lateinit var user: User
    }
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val correctPractice = 5
        val totalPractice = 10
        val correctTest = 115
        val totalTest = 3
        sharedViewModel.addNewUser("Gabriel", "A1","2021-07-26", 5, 10,
            correctPractice.toDouble() / totalPractice , 115,
            3, correctTest.toDouble() / totalTest)

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.retrieveUser(1).observe(this.viewLifecycleOwner) {selectedUser ->
            user = selectedUser
            bind(user)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun bind(user: User){
        binding?.apply {
            viewModel = sharedViewModel
            examCountdown.text = getString(R.string.exam_countdown, user.getDaysDifference())
            practiceAccuracy.text = user.getFormattedPracticeAccuracy()
            testAccuracy.text = user.getFormattedTestAccuracy()
            practiceButton.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationPractice()
                findNavController().navigate(action)
            }
            testButton.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationTest()
                findNavController().navigate(action)
            }
        }
    }

}