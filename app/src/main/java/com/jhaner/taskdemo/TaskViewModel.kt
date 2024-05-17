package com.jhaner.taskdemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jhaner.taskdemo.ui.theme.TaskDaoImpl
import kotlinx.coroutines.launch

// TaskViewModel <- clase que extiende de AndroidViewModel y se utiliza para manejar la lógica de negocio de la aplicación
// application <- instancia de la clase Application que se utiliza para acceder a los recursos de la aplicación
class TaskViewModel(application: Application) : AndroidViewModel(application) {
    // application <- contexto de la aplicación
    private val taskDao: TaskDao = TaskDaoImpl(application)
    private val _allTasks = MutableLiveData<List<Task>>()
    val allTasks: LiveData<List<Task>> get() = _allTasks

    // init <- bloque de código que se ejecuta cuando se crea una instancia de la clase
    // viewModelScope.launch <- lanza una corrutina en el ámbito del ViewModel
    // corrutina <- tarea asincrónica que puede ser pausada y reanudada
    init {
        viewModelScope.launch {
            taskDao.readAll().collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }

    // insert <- función que inserta una tarea en la base de datos
    fun insert(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        refreshTasks()
    }

    // update <- función que actualiza una tarea en la base de datos
    fun update(task: Task) = viewModelScope.launch {
        taskDao.update(task)
        refreshTasks()
    }

    // delete <- función que elimina una tarea de la base de datos
    fun delete(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        refreshTasks()
    }

    private fun refreshTasks() = viewModelScope.launch {
        taskDao.readAll().collect { tasks ->
            _allTasks.value = tasks
        }
    }
}

// TaskViewModelFactory <- clase que implementa la interfaz ViewModelProvider.Factory y se utiliza para crear una instancia de TaskViewModel
// ViewModelProvider.Factory <- interfaz que se utiliza para crear instancias de ViewModel
class TaskViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
