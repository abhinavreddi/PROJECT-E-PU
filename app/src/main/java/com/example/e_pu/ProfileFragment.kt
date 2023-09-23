package com.example.e_pu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    private lateinit var  auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        val logoutButton = view.findViewById<Button>(R.id.LogoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()

            val intent = Intent(activity, Login ::class.java)

            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn",false)
            editor.apply()

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)


            activity?.finish()

        }
    }




}
