package com.ralphordanza.budgetup.di

import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.core.interactors.RegisterUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    fun provideRegisterUser(userRepository: UserRepository) : RegisterUser {
        return RegisterUser(userRepository)
    }

    @Provides
    fun provideInteractors(
        registerUser: RegisterUser
    ) : Interactors {
        return Interactors(
            registerUser
        )
    }
}