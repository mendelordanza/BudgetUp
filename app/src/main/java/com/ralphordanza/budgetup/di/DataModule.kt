package com.ralphordanza.budgetup.di

import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.core.interactors.LoginUser
import com.ralphordanza.budgetup.core.interactors.LogoutUser
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
    fun provideLoginUser(userRepository: UserRepository) : LoginUser {
        return LoginUser(userRepository)
    }

    @Provides
    fun provideLogoutUser(userRepository: UserRepository) : LogoutUser {
        return LogoutUser(userRepository)
    }

    @Provides
    fun provideInteractors(
        registerUser: RegisterUser,
        loginUser: LoginUser,
        logoutUser: LogoutUser
    ) : Interactors {
        return Interactors(
            registerUser,
            loginUser,
            logoutUser
        )
    }
}