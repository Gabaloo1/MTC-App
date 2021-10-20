package com.gabrielsotelo.mtc.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao{
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)
}