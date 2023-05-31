package com.alezzgo.notes.ui

import com.alezzgo.notes.db.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.TITLE
)