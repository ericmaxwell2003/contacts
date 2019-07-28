package com.acme.contacts.security

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.acme.contacts.ContactsGraphDirections.Companion.actionGlobalLogin
import com.acme.contacts.security.AuthenticationViewModel.AuthenticationStatus.*

abstract class SecureFragment : Fragment() {

    val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authenticationViewModel.authenticationStatus.observe(viewLifecycleOwner, Observer { authStatus ->
            when(authStatus) {
                UNAUTHENTICATED -> findNavController().navigate(actionGlobalLogin())
                USER_DECLINED -> popBackStackOrExit()
                else -> Unit
            }
        })
    }

    private fun popBackStackOrExit() {
        if(!findNavController().popBackStack()) {
            requireActivity().finish()
        }
    }
}
