package com.ashish.restaurantapp.ui.main.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashish.restaurantapp.R
import com.ashish.restaurantapp.data.models.*
import com.ashish.restaurantapp.databinding.FragmentHomeBinding
import com.ashish.restaurantapp.ui.main.adapter.*
import com.ashish.restaurantapp.ui.main.view.activities.ResultActivity
import com.ashish.restaurantapp.ui.main.viewmodel.MainViewModel
import com.ashish.restaurantapp.utils.Status
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var addAdapter: AddAdapter
    private lateinit var collectionsAdapter: CollectionsAdapter
    private lateinit var eatWhatAdapter: EatWhatAdapter
    private lateinit var establishmentAdapter: EstablishmentAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var cuisineAdapter: CuisineAdapter

    companion object {
        var newInstance = HomeFragment()
        var categoriesExpanded = false
        var cuisinesExpanded = false
        var establishmentExpanded = false
        var eatWhatExpanded = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        val requireActivity = requireActivity()

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(activity, ResultActivity::class.java)
                intent.putExtra("querry", query)
                intent.action = "querry"
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.loadingMain.visibility = View.VISIBLE

        setImageSlider()
        setEatWhat()
        setAdds()

        observers(requireActivity)
    }

    private fun observers(requireActivity: FragmentActivity) {
        mainViewModel.getCategories().observe(requireActivity) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingMain.visibility = View.GONE
                    it.data?.let {
                        setCategoryList(it)
                    }
                }
                Status.LOADING -> {
                    binding.loadingMain.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.loadingMain.visibility = View.GONE
                    Toasty.error(requireActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        mainViewModel.getRestaurantsCollection().observe(requireActivity) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingMain.visibility = View.GONE
                    it.data?.let {
                        setRestaurantCollection(it)
                    }
                }
                Status.LOADING -> {
                    binding.loadingMain.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.loadingMain.visibility = View.GONE
                    Toasty.error(requireActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        mainViewModel.getCuisines().observe(requireActivity) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingMain.visibility = View.GONE
                    it.data?.let {
                        setCuisineList(it)
                    }
                }
                Status.LOADING -> {
                    binding.loadingMain.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.loadingMain.visibility = View.GONE
                    Toasty.error(requireActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        mainViewModel.getEstablishments().observe(requireActivity) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingMain.visibility = View.GONE
                    it.data?.let {
                        setEstablishmentList(it)
                    }
                }
                Status.LOADING -> {
                    binding.loadingMain.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.loadingMain.visibility = View.GONE
                    Toasty.error(requireActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setImageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.food1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.food2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.food3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.food4, ScaleTypes.FIT))

        binding.bannerSlider.setImageList(imageList, ScaleTypes.FIT)

    }

    private fun setAdds() {

        val list = ArrayList<Int>()
        list.add(R.drawable.add1)
        list.add(R.drawable.add2)
        list.add(R.drawable.add3)
        list.add(R.drawable.add4)

        addAdapter = AddAdapter(list)
        binding.addsRecyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.addsRecyclerview.adapter = addAdapter
    }


    private fun setEatWhat() {

        val list = ArrayList<EatWhat>()
        list.add(EatWhat("Pizza", R.drawable.pizza))
        list.add(EatWhat("Burger", R.drawable.burger))
        list.add(EatWhat("Rolls", R.drawable.roll))
        list.add(EatWhat("Chicken", R.drawable.chicken))
        list.add(EatWhat("Cake", R.drawable.cake))
        list.add(EatWhat("Biryani", R.drawable.biryani))
        list.add(EatWhat("Thali", R.drawable.thali))
        list.add(EatWhat("Paneer", R.drawable.paneer))
        list.add(EatWhat("Chole Bhature", R.drawable.chole))
        list.add(EatWhat("Fries", R.drawable.fries))

        eatWhatAdapter = EatWhatAdapter(requireActivity(), list)
        binding.eatWhatRecyclerview.layoutManager =
            GridLayoutManager(activity, 3)
        binding.eatWhatRecyclerview.adapter = eatWhatAdapter

        binding.seeAllEatWhat.setOnClickListener {
            if (!eatWhatExpanded) {
                eatWhatExpanded = true
                binding.seeAllEatWhat.text = "see less"
            } else {
                eatWhatExpanded = false
                binding.seeAllEatWhat.text = "see more"
            }
            eatWhatAdapter.notifyDataSetChanged()

        }
    }

    private fun setCuisineList(cuisines: CuisineList) {
        cuisineAdapter = CuisineAdapter(requireActivity(), cuisines)
        binding.cuisineRecyclerview.layoutManager =
            GridLayoutManager(activity, 3)
        binding.cuisineRecyclerview.adapter = cuisineAdapter

        binding.seeAllCuisines.setOnClickListener {
            if (!cuisinesExpanded) {
                cuisinesExpanded = true
                binding.seeAllCuisines.text = "see less"
            } else {
                cuisinesExpanded = false
                binding.seeAllCuisines.text = "see more"
            }
            cuisineAdapter.notifyDataSetChanged()
        }
    }

    private fun setCategoryList(categories: CategoryList) {
        categoryAdapter = CategoryAdapter(requireActivity(), categories)
        binding.categoryRecyclerview.layoutManager =
            GridLayoutManager(activity, 3)
        binding.categoryRecyclerview.adapter = categoryAdapter

        binding.seeAllCategories.setOnClickListener {
            if (!categoriesExpanded) {
                categoriesExpanded = true
                binding.seeAllCategories.text = "see less"
            } else {
                categoriesExpanded = false
                binding.seeAllCategories.text = "see more"
            }
            categoryAdapter.notifyDataSetChanged()
        }
    }

    private fun setEstablishmentList(establishments: EstablishmentList) {
        establishmentAdapter = EstablishmentAdapter(requireActivity(), establishments)
        binding.establishmentRecyclerview.layoutManager =
            GridLayoutManager(activity, 3)
        binding.establishmentRecyclerview.adapter = establishmentAdapter

        binding.seeAllEstablishments.setOnClickListener {
            if (!establishmentExpanded) {
                establishmentExpanded = true
                binding.seeAllEstablishments.text = "see less"
            } else {
                establishmentExpanded = false
                binding.seeAllEstablishments.text = "see more"
            }
            establishmentAdapter.notifyDataSetChanged()
        }
    }

    private fun setRestaurantCollection(collections: CollectionList) {
        collectionsAdapter = CollectionsAdapter(requireActivity(), collections)
        binding.collectionRecyclerview.layoutManager =
            LinearLayoutManager(activity)
        binding.collectionRecyclerview.adapter = collectionsAdapter

        collectionsAdapter.notifyDataSetChanged()
    }


}