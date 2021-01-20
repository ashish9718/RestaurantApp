package com.ashish.restaurantapp.ui.main.view.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.api.ApiHelper
import com.ashish.restaurantapp.data.api.RetrofitInstance
import com.ashish.restaurantapp.data.repository.RestaurantRepository
import com.ashish.restaurantapp.databinding.ActivityResultBinding
import com.ashish.restaurantapp.ui.base.RestaurantViewModelFactory
import com.ashish.restaurantapp.ui.main.adapter.ItemRestaurantAdapter
import com.ashish.restaurantapp.ui.main.viewmodel.RestaurantViewModel
import com.ashish.restaurantapp.utils.Status
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import es.dmoral.toasty.Toasty

class ResultActivity : AppCompatActivity() {
    private lateinit var adapter: ItemRestaurantAdapter
    private val TAG = "SearchActivity"
    lateinit var binding: ActivityResultBinding
    private lateinit var restaurantViewModel: RestaurantViewModel
    var filtervisible = false
    var filtervalue: String? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT


        val apiService = RetrofitInstance.apiService
        val apiHelper = ApiHelper(apiService)
        val repository = RestaurantRepository(apiHelper)
        val factory = RestaurantViewModelFactory(repository)
        restaurantViewModel = ViewModelProvider(this, factory).get(RestaurantViewModel::class.java)

        getIntents()


        binding.filter.setOnClickListener {
            if (!filtervisible) {
                filtervisible = true
                binding.chipGroup.visibility = View.VISIBLE
            } else {
                filtervisible = false
                binding.chipGroup.visibility = View.GONE
            }
        }

        binding.chipGroup.setOnCheckedChangeListener { chipGroup: ChipGroup, i: Int ->
            val chip: Chip? = findViewById(i)
            chip?.let {
                if (binding.cost.isChecked) {
                    filtervalue = "cost"
                    getIntents()
                    it.setChipBackgroundColorResource(R.color.black)
                    binding.rating.setChipBackgroundColorResource(R.color.gray)

                } else if (binding.rating.isChecked) {
                    filtervalue = "rating"
                    getIntents()
                    it.setChipBackgroundColorResource(R.color.black)
                    binding.cost.setChipBackgroundColorResource(R.color.gray)
                }
            }

        }

    }

    private fun getIntents() {
        when (intent.action) {
            "eatWhat" -> {
                val q: String? = intent.getStringExtra("eat_name")
                if (filtervalue != null) {
                    getRestaurantByLocationFilter(q!!, filtervalue!!)
                } else {
                    getRestaurantByLocation(q!!)
                }
            }

            "cuisine" -> {
                val cuisine_id: Int = intent.getIntExtra("cui_id", 0)
                if (filtervalue != null) {
                    getRestaurantByCuisineFilter(cuisine_id, filtervalue!!)
                } else {
                    getRestaurantByCuisine(cuisine_id)
                }
            }
            "category" -> {
                val category_id: Int = intent.getIntExtra("cat_id", 0)
                if (filtervalue != null) {
                    getRestaurantByCategoryFilter(category_id, filtervalue!!)
                } else {
                    getRestaurantByCategory(category_id)
                }
            }
            "querry" -> {
                val q: String? = intent.getStringExtra("querry")
                if (filtervalue != null) {
                    getRestaurantByLocationFilter(q!!, filtervalue!!)
                } else {
                    getRestaurantByLocation(q!!)
                }
            }
            "establishment" -> {
                val establishment_id: Int = intent.getIntExtra("est_id", 0)
                if (filtervalue != null) {
                    getRestaurantByEstablishmentFilter(establishment_id, filtervalue!!)
                } else {
                    getRestaurantByEstablishment(establishment_id)
                }
            }
            "collection" -> {
                val collection_id: Int = intent.getIntExtra("collection_id", 0)
                if (filtervalue != null) {
                    getRestaurantByCollectionFilter(collection_id, filtervalue!!)
                } else {
                    getRestaurantByCollection(collection_id)
                }
            }
        }
    }

    private fun getRestaurantByCollectionFilter(collectionId: Int, filtervalue: String) {
        restaurantViewModel.getRestaurantByCollectionFilter(collectionId, filtervalue)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants,this, this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByEstablishmentFilter(establishmentId: Int, filtervalue: String) {
        restaurantViewModel.getRestaurantByEstablishmentFilter(establishmentId, filtervalue)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByLocationFilter(q: String, filtervalue: String) {
        restaurantViewModel.getRestaurantByLocationFilter(q, filtervalue)
            .observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE
                        if (it.data?.restaurants?.isNotEmpty() == true) {
                            adapter =
                                ItemRestaurantAdapter(it.data.restaurants, this,this)
                            setAdapter()
                        } else {
                            binding.loadingAnim.setAnimation(R.raw.pagenotfound404)
                            binding.loadingAnim.visibility = View.VISIBLE
                            Toasty.error(
                                this@ResultActivity,
                                "Enter Correct location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByCategoryFilter(categoryId: Int, filtervalue: String) {
        restaurantViewModel.getRestaurantByCategoryFilter(categoryId, filtervalue)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerview.visibility = View.VISIBLE
                        binding.loadingAnim.visibility = View.GONE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingAnim.visibility = View.GONE
                        binding.recyclerview.visibility = View.INVISIBLE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByCuisineFilter(cuisineId: Int, filtervalue: String) {
        restaurantViewModel.getRestaurantByCuisineFilter(cuisineId, filtervalue)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        binding.recyclerview.visibility = View.VISIBLE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.recyclerview.visibility = View.INVISIBLE
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingAnim.visibility = View.GONE
                        binding.recyclerview.visibility = View.INVISIBLE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByCollection(collectionId: Int) {
        restaurantViewModel.getRestaurantsByCollection(collectionId)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByEstablishment(establishmentId: Int) {
        restaurantViewModel.getRestaurantsByEstablishment(establishmentId)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByCategory(category_id: Int) {
        restaurantViewModel.getRestaurantsByCategory(category_id)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getRestaurantByLocation(q: String) {
        restaurantViewModel.getRestaurantsUsingSearch(q).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingAnim.visibility = View.GONE
                    if (it.data?.restaurants?.isNotEmpty() == true) {
                        adapter = ItemRestaurantAdapter(it.data.restaurants, this,this)
                        setAdapter()
                    } else {
                        binding.loadingAnim.setAnimation(R.raw.pagenotfound404)
                        binding.loadingAnim.visibility = View.VISIBLE
                        Toasty.error(
                            this@ResultActivity,
                            "Enter Correct location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Status.LOADING -> {
                    binding.loadingAnim.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.loadingAnim.visibility = View.GONE
                    Toasty.error(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getRestaurantByCuisine(cuisine_id: Int) {
        restaurantViewModel.getRestaurantsByCuisine(cuisine_id)
            .observe(this) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAnim.visibility = View.GONE
                        resource.data?.let {
                            adapter = ItemRestaurantAdapter(it.restaurants, this,this)
                            setAdapter()
                        }
                    }
                    Status.LOADING -> {
                        binding.loadingAnim.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingAnim.visibility = View.GONE
                        Toasty.error(this, resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setAdapter() {
        binding.recyclerview.layoutManager =
            LinearLayoutManager(applicationContext)
        binding.recyclerview.adapter = adapter
    }
}
