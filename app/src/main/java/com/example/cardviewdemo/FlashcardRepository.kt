package com.example.cardviewdemo

import androidx.annotation.WorkerThread
import com.example.cardviewdemo.db.FlashCardPair
import com.example.cardviewdemo.db.FlashPairDao
import kotlinx.coroutines.flow.Flow

class FlashcardRepository(private val flashPairDao: FlashPairDao) {

    val allFlashcardpairs: Flow<List<FlashCardPair>> = flashPairDao.getFlashCarPairs()

    //insert
    @WorkerThread
    suspend fun insertFlashcards(flashCardPair: FlashCardPair){
        flashPairDao.insert(flashCardPair)
    }
}