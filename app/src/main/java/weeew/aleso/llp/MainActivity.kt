package weeew.aleso.llp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import weeew.aleso.llp.db.NoteDatabase
import weeew.aleso.llp.ui.NotePage
import weeew.aleso.llp.ui.NotesViewModel
import weeew.aleso.llp.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, NoteDatabase::class.java,"notes.db").build()
    }

    private val vm by viewModels<NotesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                val state by vm.state.collectAsState()
                NotePage(state = state, onEvent = vm::onEvent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesTheme {

    }
}