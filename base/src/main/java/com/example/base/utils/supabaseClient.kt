package com.example.base.utils
import com.example.base.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue


object SupabaseClient{
    val client = createSupabaseClient(
        supabaseKey = BuildConfig.supabaseKey,
        supabaseUrl = BuildConfig.supabaseUrl
    ){
        install(GoTrue)
    }
}