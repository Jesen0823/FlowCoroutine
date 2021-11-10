package com.jesen.flowcoroutine.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesen.flowcoroutine.room.bean.User
import com.jesen.flowcoroutine.room.db.AbsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RoomViewModel(app: Application) : AndroidViewModel(app) {

    fun insert(uid: String, firstName: String, lastName: String) {
        val user = User(uid.toInt(), firstName, lastName)
        viewModelScope.launch {
            AbsDatabase.getInstance(getApplication()).userDao()
                .insert(user)
            Log.d("ViewModel--","insert user: $uid")
        }
    }

    fun getAll():Flow<List<User>>{
        return AbsDatabase.getInstance(getApplication()).userDao().getAll()
            .catch { e ->e.printStackTrace() }
            .flowOn(Dispatchers.IO)
    }
}