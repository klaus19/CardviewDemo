package com.example.cardviewdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardviewdemo.adapter.FlashcardListAdapter
import com.example.cardviewdemo.databinding.FragmentFlashCardBinding
import com.example.cardviewdemo.databinding.FragmentUserBinding
import com.example.cardviewdemo.db.FlashCardPair
import com.example.cardviewdemo.db.FlashCardViewModel
import com.example.cardviewdemo.db.FlashcardViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FlashCardFragment : Fragment() {

    private lateinit var binding: FragmentFlashCardBinding
    private val newWordActivityRequestCode = 1

    private val flashcardViewModel: FlashCardViewModel by viewModels {
        FlashcardViewModelFactory((requireActivity().application as FlashcardApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFlashCardBinding.inflate(inflater, container, false)

         val adapter = FlashcardListAdapter()
        binding.recyclerview1.adapter = adapter
        binding.recyclerview1.layoutManager = LinearLayoutManager(requireContext())


        flashcardViewModel.allFlashcards.observe(requireActivity()){flashcard ->
            flashcard.let {
                adapter.submitList(it)
            }
        }

        binding.fab1.setOnClickListener {
            val intent = Intent(requireActivity(), UserFragment::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val front =data?.getStringExtra(UserFragment.EXTRA_REPLY)
            val back = data?.getStringExtra(UserFragment.EXTRA_REPLY1)
            if (!front.isNullOrEmpty() && !back.isNullOrEmpty()) {
                val newWord = FlashCardPair(front, back)
                flashcardViewModel.insertFlashcards(newWord)
            }
        } else {
            Toast.makeText(
                requireContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}