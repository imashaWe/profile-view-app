package com.example.userprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.userprofile.databinding.FragmentMainBinding
import com.example.userprofile.models.User
import com.example.userprofile.services.api.UserAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val tag = "MainFragment"

class MainFragment : Fragment() {

    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding

    private val userAPIService = UserAPIService.create()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener { loadData() }
    }

    private fun loadData() {
        val id = binding.textInputID.text

        if (id?.isEmpty() == true) {
            showMessage("Please type the User ID!")
            return
        }

        var user = userAPIService.getUser(id.toString())

        setLoading(true)
        binding.layoutDetails.visibility = View.GONE

        user.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                val body = response.body()

                setLoading(false)

                body?.let {
                    binding.textViewUserName.text = body.name
                    binding.textViewEmail.text = body.email
                    binding.textViewPhone.text = body.phone
                    binding.textViewWeb.text = body.website

                    binding.layoutDetails.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(tag, "error:" + t.message)
                showMessage(t.message!!)
            }


        })


    }

    private fun setLoading(isLoading: Boolean) {
        binding.buttonNext.isActivated = isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}