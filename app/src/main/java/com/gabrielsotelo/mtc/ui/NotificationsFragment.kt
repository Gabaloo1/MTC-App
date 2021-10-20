package com.gabrielsotelo.mtc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabrielsotelo.mtc.AppApplication
import com.gabrielsotelo.mtc.databinding.FragmentNotificationsBinding
import com.gabrielsotelo.mtc.model.AppViewModel
import com.gabrielsotelo.mtc.model.AppViewModelFactory
import com.gabrielsotelo.mtc.ui.notifications.NotificationsViewModel

class NotificationsFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels{
        AppViewModelFactory(
            (activity?.application as AppApplication).userDatabase.userDao(),
            (activity?.application as AppApplication).questionDatabase.questionDao()
        )
    }
    private var binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}