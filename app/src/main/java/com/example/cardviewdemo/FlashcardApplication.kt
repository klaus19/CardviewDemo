package com.example.cardviewdemo

import android.app.Application
import com.example.cardviewdemo.db.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FlashcardApplication:Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { FlashcardRepository(database.flashcardpairDao()) }
}