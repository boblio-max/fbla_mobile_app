package com.fbla.togethertrip.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "togethertrip.db").build()

    @Provides fun tripDao(db: AppDatabase) = db.tripDao()
    @Provides fun memberDao(db: AppDatabase) = db.memberDao()
    @Provides fun itineraryDao(db: AppDatabase) = db.itineraryDao()
    @Provides fun expenseDao(db: AppDatabase) = db.expenseDao()
    @Provides fun pollDao(db: AppDatabase) = db.pollDao()
    @Provides fun packingDao(db: AppDatabase) = db.packingDao()
    @Provides fun noteDao(db: AppDatabase) = db.noteDao()
}