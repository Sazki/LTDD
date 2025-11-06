package com.example.a23da040_nguyendinhphuongnam.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a23da040_nguyendinhphuongnam.model.MayTinh

@Database(entities = [MayTinh::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun computerDao(): ComputerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "computers_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
