package com.example.base.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue


object SupabaseClient{
    val client = createSupabaseClient(
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJvd3lod3ZubXJqbmZiZ3NweGZhIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0NjIyNTc5MiwiZXhwIjoyMDYxODAxNzkyfQ.gmf2ZbrWVg_UTH7xmdpmt2dsCowSkNGwmBgpWd7lCfw",
        supabaseUrl = "https://rowyhwvnmrjnfbgspxfa.supabase.co"
    ){
        install(GoTrue)
    }
}