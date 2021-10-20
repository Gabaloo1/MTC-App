package com.gabrielsotelo.mtc.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao{
    @Query("SELECT * FROM A1 WHERE `index` = :index")
    fun getQuestion(index: Int): Flow<Question>
}