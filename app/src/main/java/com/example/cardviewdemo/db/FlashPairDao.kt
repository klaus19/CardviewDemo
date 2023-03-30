package com.example.cardviewdemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashPairDao {
    @Query("SELECT * FROM word_table ORDER BY front ASC, back ASC")
     fun getFlashCarPairs(): Flow<List<FlashCardPair>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flashpair:FlashCardPair)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}