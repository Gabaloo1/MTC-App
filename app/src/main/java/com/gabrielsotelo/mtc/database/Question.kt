package com.gabrielsotelo.mtc.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index


@Entity(tableName = "A1")
data class Question(
    @PrimaryKey
    val index: Int,
    @ColumnInfo(name = "tipo")
    val tipo: String,
    @ColumnInfo(name = "categoria")
    val categoria: String,
    @ColumnInfo(name = "tema")
    val tema: String,
    @ColumnInfo(name = "pregunta")
    val pregunta: String,
    @ColumnInfo(name = "alternativa_a")
    val a: String,
    @ColumnInfo(name = "alternativa_b")
    val b: String,
    @ColumnInfo(name = "alternativa_c")
    val c: String,
    @ColumnInfo(name = "alternativa_d")
    val d: String,
    @ColumnInfo(name = "respuesta")
    val respuesta: String,
    @ColumnInfo(name = "image")
    val image: Int,
)