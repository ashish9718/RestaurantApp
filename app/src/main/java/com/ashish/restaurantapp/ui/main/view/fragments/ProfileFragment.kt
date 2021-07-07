package com.ashish.restaurantapp.ui.main.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.cloudmessaging.CloudMsgActivity
import com.ashish.restaurantapp.databinding.FragmentProfileBinding
import com.ashish.restaurantapp.ui.main.view.activities.SettingsActivity
import com.ashish.restaurantapp.ui.main.view.activities.SigninActivity
import com.ashish.restaurantapp.ui.main.view.activities.WishlistActivity
import com.ashish.restaurantapp.ui.main.viewmodel.UserViewModel
import com.ashish.restaurantapp.utils.Status
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUIData()
        intents()

        binding.swiperefreshlayout.setOnRefreshListener {
            setUIData()
            binding.swiperefreshlayout.isRefreshing = false
        }

    }

    private fun intents() {

        binding.bookmarkView.setOnClickListener {
            startActivity(Intent(activity, WishlistActivity::class.java))
        }
        binding.notificationView.setOnClickListener {
            startActivity(Intent(activity, CloudMsgActivity::class.java))
        }
        binding.settingView.setOnClickListener {
            startActivity(Intent(activity, SettingsActivity::class.java))
        }
        binding.paymentView.setOnClickListener {

        }

        binding.ordersView.setOnClickListener {

        }
        binding.favOrderView.setOnClickListener {

        }
        binding.addressView.setOnClickListener {

        }
        binding.diningView.setOnClickListener {

        }
        binding.bookingsView.setOnClickListener {

        }
        binding.logoutView.setOnClickListener {
            userViewModel.logout()
            startActivity(Intent(activity, SigninActivity::class.java))
            activity?.finish()
        }
    }

    private fun setUIData() {
        userViewModel.getUserDetails().observe(requireActivity()) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    binding.profileLoading.visibility = View.GONE
                    it.data?.let {
                        binding.name.text = it.name
                        binding.email.text = it.email
                        if (it.pic != "") {
                            Picasso.get().load(it.pic).placeholder(R.drawable.profile)
                                .into(binding.circularImageView)
                        }
                        if (it.pic_bg != "") {
                            Picasso.get().load(it.pic_bg).placeholder(R.color.black).fit()
                                .into(binding.profileBackground)
                        }
                    }
                }
                Status.LOADING -> {
                    binding.profileLoading.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.profileLoading.visibility = View.GONE
                    Toasty.error(requireActivity(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}