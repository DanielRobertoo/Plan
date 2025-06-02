package com.example.requestlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.base.datasore.UserPreferences
import com.example.requestlist.usecase.requestListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class requestListViewModel @Inject constructor(preferences: UserPreferences) {
    var state by mutableStateOf(requestListState())

    
}