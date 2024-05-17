package com.jhaner.taskdemo

import kotlinx.coroutines.flow.Flow

// TaskDao <- interfaz que define las operaciones de acceso a datos para la entidad Task
interface TaskDao {
    fun readAll(): Flow<List<Task>>
    suspend fun insert(task: Task)
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
}
