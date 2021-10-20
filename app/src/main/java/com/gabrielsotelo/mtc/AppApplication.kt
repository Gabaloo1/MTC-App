package com.gabrielsotelo.mtc

import android.app.Application
import com.gabrielsotelo.mtc.database.QuestionRoomDatabase
import com.gabrielsotelo.mtc.database.UserRoomDatabase

class AppApplication: Application() {
    val userDatabase: UserRoomDatabase by lazy { UserRoomDatabase.getDatabase(this) }
    val questionDatabase: QuestionRoomDatabase by lazy { QuestionRoomDatabase.getDatabase(this) }
}