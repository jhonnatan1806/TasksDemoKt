package com.jhaner.taskdemo

// data class <- clase que se utiliza para almacenar datos
data class Task(
    val id: Int = 0,
    val name: String,
    val isCompleted: Boolean = false
)
