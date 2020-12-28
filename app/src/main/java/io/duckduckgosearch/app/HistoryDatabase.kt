package io.duckduckgosearch.app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
@TypeConverters(DbConverters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}