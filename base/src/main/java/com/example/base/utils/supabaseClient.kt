package com.example.base.utils
import com.example.base.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient


object SupabaseClient{
    val client = createSupabaseClient(
        supabaseKey = BuildConfig.supabaseKey,
        supabaseUrl = BuildConfig.supabaseUrl
    ){
        install(Auth)
    }
}