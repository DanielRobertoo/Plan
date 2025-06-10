package com.example.editpost

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.post
import com.example.editpost.usecase.EditPostState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(EditPostState())

    fun getPost(id: Int) {
        viewModelScope.launch {
            val loadPost = client.postgrest.from("post").select() {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<post>()
            state = state.copy(
                gym = loadPost.gym,
                date = loadPost.date,
                title = loadPost.title,
                creator_post_username = loadPost.post_creator_username,
                momentDay = loadPost.moment_day,
                description = loadPost.description,
            )
        }
    }

    fun onTitleChange(s: String){
        state = state.copy(title = s)
    }

    fun onMomentDayChange(s: String){
        state = state.copy(momentDay = s)
    }

    fun onDescriptionChange(s: String){
        state = state.copy(description = s)
    }

    fun onGymChange(s: String){
        state = state.copy(gym = s)
    }





}