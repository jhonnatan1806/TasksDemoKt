package com.jhaner.taskdemo

import android.content.ContentValues
import android.content.Context
import com.jhaner.taskdemo.Task
import com.jhaner.taskdemo.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// TaskDaoImpl <- clase que implementa la interfaz TaskDao
// context <- objeto que proporciona acceso a información sobre la aplicación y el entorno en el que se ejecuta
class TaskDaoImpl(context: Context) : TaskDao {

    private val dbHelper = TaskDatabaseHelper(context)
    private val db = dbHelper.writableDatabase

    // Flow <- flujo de datos que emite cero o más valores y eventualmente se completa o falla
    override fun readAll(): Flow<List<Task>> = flow {
        // cursor <- puntero que apunta a la primera fila de la tabla de la base de datos
        // db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        val cursor = db.query(TaskDatabaseHelper.TABLE_NAME, null, null, null, null, null, "${TaskDatabaseHelper.COLUMN_ID} ASC")
        // mutableListOf <- crea una lista mutable(modificable) vacia
        val tasks = mutableListOf<Task>()
        while (cursor.moveToNext()) {
            // TODO: 1. Crear un objeto Task con los datos del cursor y agregarlo a la lista tasks
            // ej. val new_user = User(age = cursor.getInt(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_AGE)), ...)
            // ej. users.add(new_user) <- agrega un objeto a la lista
            // COMPLETAR AQUI
            
        }
        cursor.close()
        // emit <- emite la lista de tareas al flujo de datos
        emit(tasks)
    }

    // suspend <- la funcion debe ser llamada desde un bloque de codigo suspendido
    // bloque suspendido <- bloque de codigo que puede ser pausado y reanudado
    override suspend fun insert(task: Task) {
        // ContentValues <- objeto que almacena pares clave-valor
        // ContentValues.put(key, value) <- agrega un par clave-valor al objeto
        val values = ContentValues().apply {
            // TODO: 2. Agregar los valores de la tarea al objeto values
            // ej. put(UserDatabaseHelper.COLUMN_NAME, user.name)
            // COMPLETAR AQUI
            
        }
        // db.insert(table, nullColumnHack, values)
        // nullColumnHack <- nombre de la columna que no permitirá valores nulos
        // values <- objeto ContentValues que contiene los valores de la fila que se insertará
        db.insert(TaskDatabaseHelper.TABLE_NAME, null, values)
    }

    override suspend fun update(task: Task) {
        val values = ContentValues().apply {
            // TODO: 3. Actualizar los valores de la tarea en la base de datos
            // ej. put(UserDatabaseHelper.COLUMN_NAME, user.name)
            // COMPLETAR AQUI
            
        }
        // db.update(table, values, whereClause, whereArgs)
        // whereClause <- condición que se utiliza para seleccionar las filas que se actualizarán
        // whereArgs <- argumentos que se utilizan para reemplazar los signos de interrogación en la condición whereClause
        db.update(TaskDatabaseHelper.TABLE_NAME, values, "${TaskDatabaseHelper.COLUMN_ID} = ?", arrayOf(task.id.toString()))
    }

    override suspend fun delete(task: Task) {
        // db.delete(table, whereClause, whereArgs)
        // whereClause <- condición que se utiliza para seleccionar las filas que se eliminarán
        // whereArgs <- argumentos que se utilizan para reemplazar los signos de interrogación en la condición whereClause
        db.delete(TaskDatabaseHelper.TABLE_NAME, "${TaskDatabaseHelper.COLUMN_ID} = ?", arrayOf(task.id.toString()))
    }

}
