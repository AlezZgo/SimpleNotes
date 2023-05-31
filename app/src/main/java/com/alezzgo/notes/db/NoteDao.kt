package com.alezzgo.notes.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsert(note : Note)

    @Delete
    suspend fun delete(note : Note)

    @Query("SELECT * FROM note ORDER BY creationTimeMillis DESC")
    fun notesOrderedByCreationTime() : Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun notesOrderedByTitle() : Flow<List<Note>>

}