package com.acme.contacts

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.acme.contacts.database.ContactsRepository
import com.acme.contacts.detail.ContactDetailFragment
import com.acme.contacts.detail.ContactDetailFragmentArgs
import com.acme.contacts.list.ContactViewHolder
import com.acme.contacts.list.ContactsListFragment
import com.acme.contacts.list.ContactsListFragmentDirections.Companion.toContactDetail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class ContactNavigationTest {

    val expectedContact = Contact(
        id = "1",
        name = "Test Contact",
        phone = "555-555-5555",
        isFavorite = true
    )

    @Before
    fun initDatabase() {
        ContactsRepository.setupForTesting(ApplicationProvider.getApplicationContext())
        val repository = ContactsRepository.get()
        repository.saveContact(expectedContact)
    }

    @Test
    fun testNavigateToContactDetails() {

        // 1. Create a mock instance of NavController
        val mockNavController = mock(NavController::class.java)

        // 2. Create FragmentScenario instance, parameterized as a ContactsListFragment.
        val fragmentScenario = launchFragmentInContainer<ContactsListFragment>()

        // 3. Wait for the fragment to become resumed.
        fragmentScenario.onFragment { fragment ->

            // 4. Replace the NavController with a mock instance.
            Navigation.setViewNavController(
                fragment.requireView(), mockNavController
            )
        }

        // 5. Click on the first item in the contact list.
        //    It should be the one you inserted in `initDatabase()`
        onView(withId(R.id.rv_contacts_list))
            .perform(actionOnItemAtPosition<ContactViewHolder>(0, click()));

        // 6. Verify that NavController.navigate(...) is called
        // with the contactId you expected.
        verify(mockNavController).navigate(toContactDetail(expectedContact.id))

    }

    @Test
    fun testNavigateBackOnSave() {

        val mockNavController = mock(NavController::class.java)

        // android.os.Bundle created using the ContactDetailFragmentArgs class to ensure type safety.
        val arguments = ContactDetailFragmentArgs(contactId = expectedContact.id).toBundle()
        // FragmentScenario is passed the arguments along with the theme to use.
        // In this case you pass R.style.AppTheme which is defined
        // in your styles.xml resource folder and is a descendant
        // of the AppCompat theme.
        val fragmentScenario =
            launchFragmentInContainer<ContactDetailFragment>(
                arguments, R.style.AppTheme)

        fragmentScenario.onFragment { fragment ->

            Navigation.setViewNavController(
                fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.contact_name_tv)).check(
            matches(withText(expectedContact.name)));
        onView(withId(R.id.contact_number_tv)).check(
            matches(withText(expectedContact.phone)));

        onView(withId(R.id.save_contact_btn)).perform(click())
        verify(mockNavController).popBackStack()
    }
}
