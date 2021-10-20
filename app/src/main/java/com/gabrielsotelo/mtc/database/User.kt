package com.gabrielsotelo.mtc.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "exam_date")
    val examDate: String,
    @ColumnInfo(name = "correct_practice")
    val correctPractice: Int,
    @ColumnInfo(name = "total_practice")
    val totalPractice: Int,
    @ColumnInfo(name = "accuracy_practice")
    val accuracyPractice: Double,
    @ColumnInfo(name = "correct test")
    val correctTest: Int,
    @ColumnInfo(name = "total_test")
    val totalTest: Int,
    @ColumnInfo(name = "accuracy_test")
    val accuracyTest: Double){

    fun getDaysDifference(): Int{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date2 = formatter.parse(examDate)
        val date1 = Calendar.getInstance().time
        val diff: Long = date2.getTime() - date1.getTime()
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return (days + 1).toInt()
    }
}

fun User.getFormattedPracticeAccuracy(): String =
    String.format("%.0f%s", accuracyPractice*100, "%")

fun User.getFormattedTestAccuracy(): String =
    String.format("%.0f%s", accuracyTest, "/40")





//fun User.getAccuracyPractice(): Int =
    //round((correctPractice / totalPractice) * 100.0).toInt()

//fun User.getAccuracyTest(): Int =
    //round((correctTest / totalTest) * 100.0).toInt()