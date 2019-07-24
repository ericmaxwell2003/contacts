package com.acme.contacts.security

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.acme.contacts.ContactsGraphDirections
import com.acme.contacts.security.AuthenticationViewModel.AuthenticationStatus.UNAUTHENTICATED
import com.acme.contacts.security.AuthenticationViewModel.AuthenticationStatus.USER_DECLINED

abstract class SecureFragment : Fragment() {

    val authenticationViewModel by activityViewModels<AuthenticationViewModel>()
    val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authenticationViewModel.authenticationStatus.observe(viewLifecycleOwner, Observer { authStatus ->
            when(authStatus) {
                UNAUTHENTICATED -> navController.navigate(ContactsGraphDirections.actionGlobalLogin())
                USER_DECLINED -> popBackStackOrExit()
                else -> Unit
            }
        })
    }

    private fun popBackStackOrExit() {
        if(!navController.popBackStack()) {
            requireActivity().setVisible(false)
            requireActivity().finish()
        }
    }
}

