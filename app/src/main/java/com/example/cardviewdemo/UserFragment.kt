package com.example.cardviewdemo

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cardviewdemo.databinding.FragmentUserBinding
import com.example.cardviewdemo.db.FlashCardPair
import com.example.cardviewdemo.db.FlashCardViewModel
import com.example.cardviewdemo.db.FlashcardViewModelFactory


class UserFragment : Fragment() {

    private val hashMap = HashMap<String,String>()
    lateinit var binding: FragmentUserBinding
    private var currentPairIndex =0
    private lateinit var currentPair:Map.Entry<String,String>
    lateinit var counterviewModel:Countviewmodel
    lateinit var flashCardViewModel: FlashCardViewModel
    var isFront=true
    private val totalPairs = 17 // change the value to the actual number of entries in your hashMap


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater,container,false)
        counterviewModel = ViewModelProvider(this).get(Countviewmodel::class.java)
        flashCardViewModel = ViewModelProvider(this).get(FlashCardViewModel::class.java)

        binding.textCounter.text = counterviewModel.count.toString()

        binding.backIcon.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_flashCardFragment)
        }

        //changing color of progress bar progress
        binding.progressHorizontal.progressTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext()
            ,R.color.blue_card))

        //changing color of background color of progress bar
        binding.progressHorizontal.progressBackgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(),
            R.color.silver))

        // Hashmap of strings that will shown on cardview front and back side
        hashMap["What"] = "Kas"
        hashMap["When"] = "Kai"
        hashMap["Where"] = "Kur"
        hashMap["Who"] = "Kas"
        hashMap["Whom"] = "Kam"
        hashMap["Why"] = "Kodėl"
        hashMap["How"] = "Kaip"
        hashMap["Which"] = "Kuris/kuri"
        hashMap["Whose"] = "Kieno"
        hashMap["I"] = "aš"
        hashMap["you (singular)"] = "tu/jūs (informal/formal)"
        hashMap["he"] = "jis"
        hashMap["she"] = "ji"
        hashMap["it "] = "tai"
        hashMap["we"] = "mes"
        hashMap["you (plural)"] = "jūs"
        hashMap["they"] = "jie"

        val front_animation = AnimatorInflater.loadAnimator(context, R.anim.front_animator) as AnimatorSet
        val back_animation = AnimatorInflater.loadAnimator(context,R.anim.back_animator)as AnimatorSet

        currentPair = hashMap.entries.elementAt(currentPairIndex)
        binding.textCardFront.text = currentPair.key
        binding.textCardBack.text = hashMap[currentPair.key]



        binding.btnFlip.setOnClickListener {

           with(binding){
               val progress = ((currentPairIndex + 1) * 100) / totalPairs
               binding.progressHorizontal.progress = progress

               // initialize currentPairIndex to 0 if it hasn't been initialized yet
               if (currentPairIndex < 0) {
                   currentPairIndex = 0
               }
               if (isFront) {
                   front_animation.setTarget(textCardFront)
                   back_animation.setTarget(textCardBack)
                   front_animation.start()
                   back_animation.start()
                   isFront = false
                   textCardBack.visibility = View.VISIBLE
                   textCardFront.visibility = View.GONE
                   imageFlashCard.visibility = View.GONE
                   cardViewQuestions.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.pink))

               } else {
                   currentPairIndex = (currentPairIndex + 1) % hashMap.size
                   textCardFront.visibility = View.VISIBLE
                   textCardBack.visibility = View.GONE
                   imageFlashCard.visibility = View.VISIBLE
                   cardViewQuestions.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_card))
                   front_animation.setTarget(textCardBack)
                   back_animation.setTarget(textCardFront)
                   back_animation.start()
                   front_animation.start()
                   isFront = true
               }
               // retrieve the current pair from the hashMap
               currentPair = hashMap.entries.elementAt(currentPairIndex)
               binding.textCardFront.text = currentPair.key
               binding.textCardBack.text = hashMap[currentPair.key]
           }

         }

        binding.imageFlashCard.setOnClickListener {

            counterviewModel.addWordCount()
            binding.textCounter.text = counterviewModel.count.toString()
            // increment currentPairIndex and get the next pair
            currentPairIndex++
            if (currentPairIndex >= hashMap.size) {
                // if we have reached the end of the hashmap, start again from the beginning
                currentPairIndex = 0
            }
            currentPair = hashMap.entries.elementAt(currentPairIndex)

            // update the UI with the new pair
            binding.textCardFront.text = currentPair.key
            binding.textCardBack.text = hashMap[currentPair.key]
                    //val flashCardPair = FlashCardPair(front,back!!)//flashCardViewModel.insertFlashcards(flashCardPair)

                        val front  = binding.textCardFront.text.toString()
                        val back = binding.textCardBack.text.toString()
                         val newWord = FlashCardPair(front,back)
                         flashCardViewModel.insertFlashcards(newWord)
                    }

        binding.cardLearning.setOnClickListener {

                 findNavController().navigate(R.id.action_userFragment_to_flashCardFragment)
        }
        return binding.root
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY1 = "hey"
    }

}