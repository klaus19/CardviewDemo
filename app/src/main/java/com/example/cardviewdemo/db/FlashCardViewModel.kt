package com.example.cardviewdemo.db

import androidx.lifecycle.*
import com.example.cardviewdemo.FlashcardRepository
import kotlinx.coroutines.launch

class FlashCardViewModel(private val flashrepository: FlashcardRepository):ViewModel() {

    val allFlashcards: LiveData<List<FlashCardPair>> = flashrepository.allFlashcardpairs.asLiveData()

    fun insertFlashcards(flashCardPair: FlashCardPair) = viewModelScope.launch {
        flashrepository.insertFlashcards(flashCardPair)
    }
}
class FlashcardViewModelFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
