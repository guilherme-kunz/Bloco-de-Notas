package com.guilhermekunz.blocodenotas.di

import android.app.Application
import androidx.room.Room
import com.guilhermekunz.blocodenotas.data.local.NoteDatabase
import com.guilhermekunz.blocodenotas.data.repository.NoteRepositoryImpl
import com.guilhermekunz.blocodenotas.domain.repository.NoteRepository
import com.guilhermekunz.blocodenotas.domain.use_case.GetNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideGetNotesUseCase(repository: NoteRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }
}