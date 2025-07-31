package com.tigasatudesember.bukupemasok.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    @Singleton
    fun supabaseClient(): SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://psejukecmfiqlnyespug.supabase.co",
        supabaseKey = "sb_publishable_nd5YLD_vqFu9_jPdj5w2tA_KVn6gAF0"
    ) {
        install(Postgrest)
    }
}