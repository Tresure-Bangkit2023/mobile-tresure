package id.tresure.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.tresure.android.data.local.Place
import id.tresure.android.data.local.PlaceData
import id.tresure.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var list: ArrayList<Place> = arrayListOf()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.apply {
            rvViral.layoutManager = layoutManager
            rvViral.setHasFixedSize(true)
            rvViral.adapter = PlaceAdapter(list)
        }
        list.addAll(PlaceData.listPlace)

        val layoutManager1 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.apply {
            rvOtherPlan.layoutManager = layoutManager1
            rvOtherPlan.setHasFixedSize(true)
            rvOtherPlan.adapter = PlaceAdapter(list)
        }
        list.addAll(PlaceData.listPlace)

        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.apply {
            rvRestaurant.layoutManager = layoutManager2
            rvRestaurant.setHasFixedSize(true)
            rvRestaurant.adapter = PlaceAdapter(list)
        }
        list.addAll(PlaceData.listPlace)

        val layoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.apply {
            rvFamilyPlace.layoutManager = layoutManager3
            rvFamilyPlace.setHasFixedSize(true)
            rvFamilyPlace.adapter = PlaceAdapter(list)
        }
        list.addAll(PlaceData.listPlace)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}