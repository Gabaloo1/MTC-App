package com.gabrielsotelo.mtc.model

import androidx.lifecycle.*
import androidx.room.Room
import com.gabrielsotelo.mtc.database.Question
import com.gabrielsotelo.mtc.database.QuestionDao
import com.gabrielsotelo.mtc.database.User
import com.gabrielsotelo.mtc.database.UserDao
import com.gabrielsotelo.mtc.ui.HomeFragment
import kotlinx.coroutines.launch

class AppViewModel(private val userDao: UserDao, private val questionDao: QuestionDao): ViewModel(){
    private fun insertUser(user: User){
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    fun retrieveUser(id: Int): LiveData<User>{
        return userDao.getUser(id).asLiveData()
    }

    fun addNewUser(username: String, category: String, examDate: String, correctPractice: Int,
                   totalPractice: Int, accuracyPractice: Double, correctTest: Int,
                   totalTest: Int, accuracyTest: Double){
        val new = User(
            username = username,
            category = category,
            examDate = examDate,
            correctPractice = correctPractice,
            totalPractice = totalPractice,
            accuracyPractice = accuracyPractice,
            correctTest = correctTest,
            totalTest = totalTest,
            accuracyTest = accuracyTest
        )
        insertUser(new)
    }

    fun retrieveQuestion(index: Int): LiveData<Question>{
        return questionDao.getQuestion(index).asLiveData()
    }

    fun updateUser(practiceCorr: Int, practice: Int, testCorr: Int, test: Int){
        val currentUser = HomeFragment.user
        val newPracticeCorrect = currentUser.correctPractice + practiceCorr
        val newPracticeTotal = currentUser.totalPractice + practice
        val newTestCorrect = currentUser.correctTest + testCorr
        val newTestTotal = currentUser.totalTest + test

        val newUser = User(currentUser.id,currentUser.username,currentUser.category,
            currentUser.examDate,newPracticeCorrect,newPracticeTotal,
            newPracticeCorrect.toDouble()/newPracticeTotal,newTestCorrect,newTestTotal,
            newTestCorrect.toDouble()/newTestTotal)
        viewModelScope.launch {
            userDao.update(newUser)
        }
    }
}

class AppViewModelFactory(private val userDao: UserDao, private val questionDao: QuestionDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(userDao, questionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}