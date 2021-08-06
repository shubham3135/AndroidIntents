package com.shubhamkumarwinner.rememberandauthenticateuser

import android.app.Activity
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shubhamkumarwinner.rememberandauthenticateuser.databinding.RememberUserFragmentBinding
import java.util.*
import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import com.google.android.gms.actions.NoteIntents
import com.google.android.gms.actions.ReserveIntents


class RememberUserFragment : Fragment() {

    private val  viewModel: RememberUserViewModel by viewModels()
    private lateinit var binding: RememberUserFragmentBinding
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
    }
    private val getContact = registerForActivityResult(ActivityResultContracts.PickContact()) {
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){

    }
    private val openDocument = registerForActivityResult(ActivityResultContracts.OpenDocument()){

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = RememberUserFragmentBinding.inflate(layoutInflater, container, false)
        binding.getAccount.setOnClickListener {
            /*val am: AccountManager = AccountManager.get(requireActivity().applicationContext) // "this" references the current Context

            val accounts: Array<out Account> = am.getAccountsByType("com.google")
            Log.d("Account", "accounts are ${accounts.size}")*/
//            openDocument.launch(arrayOf("image/*"))
//            getContact.launch()
//            composeEmail(arrayOf("shubhamkumarwinner@gmail.com"), "This is shubham")
//            playSearchArtist("Sonu Nigam")
//            createNote("Shubham", "This is shubham kumar")
//            dialPhoneNumber("7900148057")
//            searchWeb("When is Raksha bandhan")
            openWifiSettings()
//            composeSmsMessage("Hii, babe")
//            openWebPage("www.google.in")
        }
        return binding.root
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

    private fun composeSmsMessage(message: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("smsto:")  // This ensures only SMS apps respond
            putExtra("sms_body", message)
        }
        startActivity(intent)
    }

    private fun openWifiSettings() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
    }

    private fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        startActivity(intent)

    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)

    }

    private fun createNote(subject: String, text: String) {
        val intent = Intent(NoteIntents.ACTION_CREATE_NOTE).apply {
            putExtra(NoteIntents.EXTRA_NAME, subject)
            putExtra(NoteIntents.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }

    private fun playSearchArtist(artist: String) {
        val intent = Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH).apply {
            putExtra(MediaStore.EXTRA_MEDIA_FOCUS, MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE)
            putExtra(MediaStore.EXTRA_MEDIA_ARTIST, artist)
            putExtra(SearchManager.QUERY, artist)
        }
        startActivity(intent)
    }

    private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(intent)

    }

    private fun insertContact(name: String, number: String) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, name)
            putExtra(ContactsContract.Intents.Insert.PHONE, number)
        }
        startActivity(intent)
    }

    fun addEvent(title: String, location: String, begin: Long, end: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
        }
        startActivity(intent)
    }

    private fun showAllAlarm(){
        val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
        startActivity(intent)
    }

    private fun startTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }

        startActivity(intent)
    }

    private fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        startActivity(intent)
    }

    private fun createCalenderEvent(){
        val calenderEvent: Intent = // Event is on January 23, 2021 -- from 7:30 AM to 10:30 AM.
            Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                val beginTime: Calendar = Calendar.getInstance().apply {
                    set(2021, 0, 23, 7, 30)
                }
                val endTime = Calendar.getInstance().apply {
                    set(2021, 0, 23, 10, 30)
                }
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                putExtra(CalendarContract.Events.TITLE, "Ninja class")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo")
            }
        try {
            startActivity(calenderEvent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            e.printStackTrace()
        }

    }

    private fun sendEmail(){
        val emailIntent: Intent = Intent(Intent.ACTION_SEND).apply {
            // The intent does not have a URI, so declare the "text/plain" MIME type
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("jan@example.com")) // recipients
            putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            putExtra(Intent.EXTRA_TEXT, "Email message text")
            putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
            // You can also attach multiple items by passing an ArrayList of Uris
        }
        try {
            startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            e.printStackTrace()
        }

    }

    private fun dialANumber(){
        val callIntent: Intent = Uri.parse("tel:5551234").let { number ->
            Intent(Intent.ACTION_DIAL, number)
        }
        try {
            startActivity(callIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            e.printStackTrace()
        }
    }

    private fun viewAMap(){
        // Map point based on address
        val mapIntent: Intent = Uri.parse(
            "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"
        ).let { location ->
            // Or map point based on latitude/longitude
            // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
            Intent(Intent.ACTION_VIEW, location)
        }
        try {
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            e.printStackTrace()
        }
    }

    private fun viewAWebPage(){
        val webIntent: Intent = Uri.parse("https://www.android.com").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            e.printStackTrace()
        }
    }

    private fun createChooser(){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
        }

// Always use string resources for UI text.
// This says something like "Share this photo with"
        val title = "This is shubham"
// Create intent to show chooser
        val chooser = Intent.createChooser(intent, title)

// Try to invoke the intent.
        try {
            startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
        }
    }

}