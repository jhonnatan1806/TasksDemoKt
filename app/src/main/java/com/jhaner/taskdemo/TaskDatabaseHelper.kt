package com.jhaner.taskdemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// TaskDatabaseHelper <- clase que extiende de SQLiteOpenHelper y se utiliza para crear y actualizar la base de datos
class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // onCreate <- metodo que se ejecuta cuando se crea la base de datos
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    // onUpgrade <- metodo que se ejecuta cuando se actualiza la base de datos
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    // companion object <- equivalente a um objeto estatico en Java 
    companion object {
        const val DATABASE_NAME = "tasks.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "task_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IS_COMPLETED = "isCompleted"

        const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_IS_COMPLETED INTEGER NOT NULL
            )
        """

        const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}