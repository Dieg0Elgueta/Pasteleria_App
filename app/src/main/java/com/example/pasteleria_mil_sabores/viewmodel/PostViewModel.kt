package com.example.pasteleria_mil_sabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleria_mil_sabores.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel(){
    // Lista de posts
    val posts = MutableStateFlow(emptyList<com.example.pasteleria_mil_sabores.model.Post>())

    // Carga de post
    fun cargarPost(){
        viewModelScope.launch {
            val respuesta = RetrofitInstance.api.getPosts()
            posts.value = respuesta
        }


    }
}