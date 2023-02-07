package com.example.makemon.ui

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.makemon.R
import com.example.makemon.databinding.ActivityMainBinding
import com.example.makemon.service.FirebaseService
import com.example.makemon.ui.base.BaseActivity
import com.example.makemon.ui.character_list.CharacterListFiveFragment
import com.example.makemon.ui.character_list.CharacterListFourFragment
import com.example.makemon.ui.character_list.CharacterListOneFragment
import com.example.makemon.ui.character_list.CharacterListSixFragment
import com.example.makemon.ui.character_list.CharacterListThreeFragment
import com.example.makemon.ui.character_list.CharacterListTwoFragment
import com.example.makemon.ui.settings.SettingsActivity
import com.example.makemon.utils.CloseBackPressed
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        const val TAG = "MainActivity"
    }

    private lateinit var navController: NavController

    lateinit var navHostFragment: NavHostFragment

    private lateinit var closeBackPressed: CloseBackPressed

    override fun initBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        closeBackPressed = CloseBackPressed(this)

        lifecycleScope.launch(Dispatchers.IO) {
            getTokenResult()
        }

        setNav()
        setToolbarBack()
    }

    override fun onResume() {
        super.onResume()

//        val audioManager: AudioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ((audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 0.5).toInt()), AudioManager.FLAG_PLAY_SOUND)
    }

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                Log.w("test", "test")
                return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                Log.w("test", "test")
                return true
            }
        }
        return false
    }*/

    private fun setNav() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.mainFragment -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                }
                R.id.characterListMainFragment -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                }
                R.id.settingsActivity -> {
                    Toast.makeText(this, "업데이트 예정입니다.", Toast.LENGTH_LONG).show()
//                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
//                    startActivity(intent)
                }
                else -> {}
            }
            false
        }
    }

    fun bottomNavigationVisibility(boolean: Boolean) {
        if (boolean) {
            binding.bottomNavigation.visibility = View.VISIBLE
        } else {
            binding.bottomNavigation.visibility = View.GONE
        }
    }

    fun setTitleText(text: Int) {
        binding.toolbar.title.setText(text)
    }

    fun setToolbarBackVisibility(boolean: Boolean) {
        if (boolean) {
            binding.toolbar.back.visibility = View.VISIBLE
        } else {
            binding.toolbar.back.visibility = View.GONE
        }
    }

    private fun setToolbarBack() {
        binding.toolbar.back.setOnClickListener {
            onBackPressed()
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.w(TAG, "backPressedCallback: call")
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            when (val fragment = navHostFragment.childFragmentManager.fragments[0]) {
                is CharacterListOneFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListOneFragment_to_characterListMainFragment)
                }
                is CharacterListTwoFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListTwoFragment_to_characterListMainFragment)
                }
                is CharacterListThreeFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListThreeFragment_to_characterListMainFragment)
                }
                is CharacterListFourFragment -> {

                    fragment.findNavController().navigate(R.id.action_characterListFourFragment_to_characterListMainFragment)
                }
                is CharacterListFiveFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListFiveFragment_to_characterListMainFragment)
                }
                is CharacterListSixFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListSixFragment_to_characterListMainFragment)
                }
                else -> {
                    closeBackPressed.onBackPressed()
                }
            }
        }
    }

    @Deprecated("Deprecated in Android SDK Version 33")
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.w(TAG, "ANDROID VERSION >= 33")
            this.onBackPressedDispatcher.addCallback(this, backPressedCallback)
        } else {
            Log.w(TAG, "ANDROID VERSION < 33")
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            when (val fragment = navHostFragment.childFragmentManager.fragments[0]) {
                is CharacterListOneFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListOneFragment_to_characterListMainFragment)
                }
                is CharacterListTwoFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListTwoFragment_to_characterListMainFragment)
                }
                is CharacterListThreeFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListThreeFragment_to_characterListMainFragment)
                }
                is CharacterListFourFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListFourFragment_to_characterListMainFragment)
                }
                is CharacterListFiveFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListFiveFragment_to_characterListMainFragment)
                }
                is CharacterListSixFragment -> {
                    fragment.findNavController().navigate(R.id.action_characterListSixFragment_to_characterListMainFragment)
                }
                else -> {
                    closeBackPressed.onBackPressed()
                }
            }
        }
    }

    private suspend fun getTokenResult() = suspendCoroutine<String?> { continuation ->

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Log.w("MainActivity", "getTokenResult:: Task Success")
                continuation.resume(task.result)
                Log.w("MainActivity", "FCM Token:: ${task.result}")
            }else {
                Log.w("MainActivity", "getTokenResult:: Task Failed: Null")
                continuation.resume(null)
            }
        }
    }
}