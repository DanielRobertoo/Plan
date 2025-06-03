package com.example.requestlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.base.datasore.UserPreferences
import com.example.requestlist.usecase.requestListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class requestListViewModel @Inject constructor(preferences: UserPreferences) : ViewModel(){

    fun onAccept(id: Int) {

    }

    fun onRefuse(id: Int) {

    }

    var state by mutableStateOf(requestListState())

    
}