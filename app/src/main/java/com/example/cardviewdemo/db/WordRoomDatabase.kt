package com.example.cardviewdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [FlashCardPair::class], version = 2, exportSchema = true)
 abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun flashcardpairDao(): FlashPairDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) :Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val wordDao = database.flashcardpairDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = FlashCardPair("Heo","hi")
                    wordDao.insert(word)
                    word =FlashCardPair("World!","why")
                    wordDao.insert(word)

                }
            }
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
