package weeew.aleso.llp.ui

import weeew.aleso.llp.db.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.TITLE
)