package com.ralphordanza.budgetup.di

import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
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
    fun provideLoginAsGuest(userRepository: UserRepository) : LoginAsGuest {
        return LoginAsGuest(userRepository)
    }

    @Provides
    fun provideLogoutUser(userRepository: UserRepository) : LogoutUser {
        return LogoutUser(userRepository)
    }

    @Provides
    fun provideGetWallets(walletRepository: WalletRepository) : GetWallets {
        return GetWallets(walletRepository)
    }

    @Provides
    fun provideGetTotal(walletRepository: WalletRepository) : GetTotal {
        return GetTotal(walletRepository)
    }

    @Provides
    fun provideAddWallet(walletRepository: WalletRepository) : AddWallet {
        return AddWallet(walletRepository)
    }

    @Provides
    fun provideCalculatorCompute(transactionRepository: TransactionRepository) : CalculatorCompute {
        return CalculatorCompute(transactionRepository)
    }

    @Provides
    fun provideInteractors(
        registerUser: RegisterUser,
        saveToFirestore: SaveToFirestore,
        loginUser: LoginUser,
        logoutUser: LogoutUser,
        loginAsGuest: LoginAsGuest,
        getWallets: GetWallets,
        getTotal: GetTotal,
        addWallet: AddWallet,
        calculatorCompute: CalculatorCompute
    ) : Interactors {
        return Interactors(
            registerUser,
            saveToFirestore,
            loginUser,
            loginAsGuest,
            logoutUser,
            getWallets,
            getTotal,
            addWallet,
            calculatorCompute
        )
    }
}