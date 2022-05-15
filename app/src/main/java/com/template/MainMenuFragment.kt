package com.template

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.template.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private var counter = 0

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(layoutInflater, container, false)
        val assets = requireActivity().assets.list(HTML)
        preferences = requireActivity().getSharedPreferences("Rating_stars", MODE_PRIVATE)
        if (assets?.isNotEmpty()!!) {
            with(binding) {
                webView.loadUrl(ASSETS + assets[counter])
                next.setOnClickListener {
                    if (counter < assets.size - 1) {
                        counter++
                        webView.loadUrl(ASSETS + assets[counter])
                        back.isEnabled = true
                        if (counter == assets.size - 1) next.isEnabled = false
                    }
                }
                back.setOnClickListener {
                    if (counter > 0) {
                        counter--
                        webView.loadUrl(ASSETS + assets[counter])
                        next.isEnabled = true
                        if (counter == 0) back.isEnabled = false
                    }
                }
                fab.setOnClickListener {
                    ArticleRatingDialog(assets[counter]).show(parentFragmentManager, "dialog")
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val HTML = "html"
        private const val ASSETS = "file:///android_asset/html/"
    }
}