package com.template

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.template.databinding.RateDialogLayoutBinding

class ArticleRatingDialog(private val articleName: String) : DialogFragment() {

    private lateinit var binding: RateDialogLayoutBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = RateDialogLayoutBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        preferences =
            requireActivity().getSharedPreferences("Rating_stars", AppCompatActivity.MODE_PRIVATE)

        if (preferences.contains(articleName)) binding.ratingBar.rating =
            preferences.getFloat(articleName, 0.0F)

        with(binding) {
            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                rateButton.setOnClickListener {
                    preferences.edit().putFloat(articleName, rating).apply()
                    dismiss()
                }
            }
        }
        return builder.create()
    }
}