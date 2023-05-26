package weeew.aleso.llp.ui

import weeew.aleso.llp.db.Note

sealed interface NoteEvent {
    object SaveNote : NoteEvent
    data class SetTitle(val title: String) : NoteEvent
    data class SetContent(val content: String) : NoteEvent
    object ShowDialog : NoteEvent
    object HideDialog : NoteEvent
    data class SortNotes(val sortType: SortType) : NoteEvent
    data class DeleteNote(val note : Note) : NoteEvent
}