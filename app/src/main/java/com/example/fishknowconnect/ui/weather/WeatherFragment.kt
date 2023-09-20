package com.example.fishknowconnect.ui.fish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fishknowconnect.R
import com.example.fishknowconnect.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment(), OnClickListener {

    private var _binding: FragmentWeatherBinding? = null
    private var navController: NavController? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view);
        view.findViewById<Button>(R.id.buttonNewPost).setOnClickListener(this)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        navController?.navigate(R.id.action_weatherFragment_to_newPostActivity)
    }
}