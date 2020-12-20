package com.ralphordanza.budgetup.di

import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import com.ralphordanza.budgetup.core.interactors.*
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
    fun provideSaveToFirestore(userRepository: UserRepository) : SaveToFirestore {
        return SaveToFirestore(userRepository)
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
    fun provideGetWallets(walletReposiroy: WalletRepository) : GetWallets {
        return GetWallets(walletReposiroy)
    }

    @Provides
    fun provideAddWallet(walletReposiroy: WalletRepository) : AddWallet {
        return AddWallet(walletReposiroy)
    }

    @Provides
    fun provideInteractors(
        registerUser: RegisterUser,
        saveToFirestore: SaveToFirestore,
        loginUser: LoginUser,
        logoutUser: LogoutUser,
        getWallets: GetWallets,
        addWallet: AddWallet
    ) : Interactors {
        return Interactors(
            registerUser,
            saveToFirestore,
            loginUser,
            logoutUser,
            getWallets,
            addWallet
        )
    }
}