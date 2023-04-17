package com.alezzgo.notes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Note(
    val title : String,
    val content : String,
    val creationTimeMillis : Long,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
)