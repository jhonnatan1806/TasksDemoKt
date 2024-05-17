package com.jhaner.taskdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jhaner.taskdemo.ui.theme.TaskDemoTheme
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {

    // viewModels <- crea un ViewModelProvider que proporciona el ViewModel
    // TaskViewModelFactory <- clase que crea una instancia de TaskViewModel
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(application)
    }

    // onCreate <- funcion que se ejecuta cuando se crea la actividad
    // para mas informacion revisar https://developer.android.com/guide/components/activities/activity-lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent <- establece la vista de la actividad
        setContent {
            // XTheme <- tema personalizado que se utiliza para personalizar la apariencia de la aplicación
            TaskDemoTheme {
                // Surface <- contenedor que pinta su contenido en un color de fondo
                // Modifier.fillMaxSize() <- modificador que hace que el contenedor ocupe todo el tamaño disponible
                // MaterialTheme.colorScheme.background <- color de fondo del tema Material
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TaskScreen(taskViewModel)
                }
            }
        }
    }
}

// Composable <- funcion que crea y muestra una interfaz de usuario
@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    // observeAsState <- observa los cambios en el LiveData y actualiza el estado del composable
    // LiveData <- clase que se utiliza para almacenar datos observables
    val allTasks by taskViewModel.allTasks.observeAsState(emptyList())
    var taskName by remember { mutableStateOf(TextFieldValue("")) }

    // Column <- contenedor vertical que organiza su contenido en una sola columna
    Column(modifier = Modifier.padding(16.dp)) {
        // TextField <- campo de texto que permite al usuario ingresar texto
        TextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") },
            modifier = Modifier.fillMaxWidth()
        )
        // Spacer <- espacio en blanco que se utiliza para separar elementos
        Spacer(modifier = Modifier.height(8.dp))
        // Button <- botón que se utiliza para realizar una acción cuando se presiona
        Button(
            onClick = {
                if (taskName.text.isNotEmpty()) {
                    taskViewModel.insert(Task(name = taskName.text))
                    taskName = TextFieldValue("")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // LazyColumn <- contenedor que organiza su contenido en una lista perezosa
        LazyColumn {
            items(allTasks) { task ->
                TaskItem(task = task, onDelete = { taskViewModel.delete(it) })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: (Task) -> Unit) {
    // Row <- contenedor horizontal que organiza su contenido en una sola fila
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(task.name, modifier = Modifier.weight(1f))
        IconButton(onClick = { onDelete(task) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
        }
    }
}