package com.acme.contacts.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.acme.contacts.R
import com.acme.contacts.databinding.FragmentEnterCredentialsBinding
import com.acme.contacts.security.AuthenticationViewModel.AuthenticationStatus.*
import com.acme.contacts.security.EnterCredentialsFragmentDirections.Companion.actionLoginPop


class EnterCredentialsFragment : Fragment() {

    val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    lateinit var binding: FragmentEnterCredentialsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_credentials, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginBtn.setOnClickListener {
                authenticationViewModel.authenticate(usernameTv.text.toString(), passwordTv.text.toString())
            }
        }

        authenticationViewModel.authenticationStatus.observe(viewLifecycleOwner, Observer { authState ->
            when (authState) {
                AUTHENTICATED -> findNavController().navigate(actionLoginPop())
                USER_DECLINED -> findNavController().navigate(actionLoginPop())
                else -> Unit
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            authenticationViewModel.declineAuthentication()
        }
    }
}