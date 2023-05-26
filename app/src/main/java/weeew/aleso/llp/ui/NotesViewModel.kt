package weeew.aleso.llp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import weeew.aleso.llp.db.Note
import weeew.aleso.llp.db.NoteDao

class NotesViewModel(
    private val dao: NoteDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TITLE)
    private val _notes = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.TITLE -> dao.notesOrderedByTitle()
            SortType.CREATION_TIME_MILLIS -> dao.notesOrderedByCreationTime()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }
    private val _state = MutableStateFlow(NoteState())
    val state = combine(_state, _sortType, _notes) { state, sortType, notes ->
        state.copy(notes = notes, sortType = sortType)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), NoteState())

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.SetTitle -> _state.update { it.copy(title = event.title) }
            is NoteEvent.SetContent -> _state.update { it.copy(content = event.content) }
            is NoteEvent.SortNotes -> _sortType.value = event.sortType
            is NoteEvent.DeleteNote -> viewModelScope.launch { dao.delete(event.note) }
            NoteEvent.HideDialog -> _state.update { it.copy(isAddingContact = false) }
            NoteEvent.ShowDialog -> _state.update { it.copy(isAddingContact = true) }
            NoteEvent.SaveNote -> viewModelScope.launch {
                dao.upsert(
                    Note(
                        state.value.title,
                        state.value.content,
                        System.currentTimeMillis()
                    )
                )
                _state.update { it.copy(isAddingContact = false, title = "", content = "") }
            }
        }

    }

}