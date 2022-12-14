package com.example.movieapps.presentation.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieapps.R
import com.example.movieapps.activity.LoginActivity
import com.example.movieapps.data.viewmodel.ViewModelUser
import com.example.movieapps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().applicationContext.getSharedPreferences("datauser",
            Context.MODE_PRIVATE)

//        binding..text = "Welcome, " + sharedPreferences.getString("username","")
        binding.btnLogout.setOnClickListener {
            alertDialog()
        }
        getUserById()

        binding.btnUpdate.setOnClickListener {
            updateUser()
            this.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
    }

    private fun getUserById() {
        var id = sharedPreferences.getString("id","").toString()
        val viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getUserById(id)
        viewModel.getLiveDataUserById().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etUsername.setText(it.username)
                binding.etPassword.setText(it.password)
                binding.etAddress.setText(it.address)
                binding.etAge.setText(it.age)
            }
        }
    }

    private fun updateUser() {
        var id = sharedPreferences.getString("id","").toString()
        var name = sharedPreferences.getString("name","").toString()
        var username = binding.etUsername.text.toString()
        var password = binding.etPassword.text.toString()
        var address = binding.etAddress.text.toString()
        var age = binding.etAge.text.toString()
        val viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.updateApiUser(id, name, username, password, age, address)
        viewModel.updateLiveDataUser().observe(this.requireActivity(), {
            if (it != null){
                Toast.makeText(this.requireActivity(), "Data Update Successfully", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alertDialog(){
        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Logout")

        builder.setMessage("Are You Sure Want To Loguot?")

        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(requireActivity(),"No", Toast.LENGTH_LONG).show()
        }

        builder.setPositiveButton("Yes"){dialogInterface, which->
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            val saveUser = sharedPreferences.edit()
            saveUser.clear()
            saveUser.apply()
            Toast.makeText(requireActivity(),"Logout Succesfully", Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}