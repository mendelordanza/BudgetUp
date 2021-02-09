package com.ralphordanza.budgetup.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.data.implementation.TransactionDataSourceImpl
import com.ralphordanza.budgetup.core.data.implementation.UserDataSourceImpl
import com.ralphordanza.budgetup.core.data.implementation.WalletDataSourceImpl
import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import com.ralphordanza.budgetup.core.domain.network.TransactionDtoMapper
import com.ralphordanza.budgetup.core.domain.network.WalletDtoMapper
import com.ralphordanza.budgetup.framework.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context) : SessionManager{
        return SessionManager(context)
    }

    @Provides
    @Singleton
    fun provideWalletDtoMapper() : WalletDtoMapper{
        return WalletDtoMapper()
    }

    @Provides
    @Singleton
    fun provideTransactionDtoMapper() : TransactionDtoMapper{
        return TransactionDtoMapper()
    }

    @Provides
    @Singleton
    fun provideUserDataSource(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore) : UserDataSource{
        return UserDataSourceImpl(firebaseAuth, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideWalletDataSource(firebaseFirestore: FirebaseFirestore, walletDtoMapper: WalletDtoMapper) : WalletDataSource{
        return WalletDataSourceImpl(firebaseFirestore, walletDtoMapper)
    }

    @Provides
    @Singleton
    fun provideTransactionDataSource(firebaseFirestore: FirebaseFirestore, transactionDtoMapper: TransactionDtoMapper) : TransactionDataSource{
        return TransactionDataSourceImpl(firebaseFirestore, transactionDtoMapper)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource) : UserRepository{
        return UserRepository(userDataSource)
    }

    @Provides
    @Singleton
    fun provideWalletRepository(walletDataSource: WalletDataSource) : WalletRepository{
        return WalletRepository(walletDataSource)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDataSource: TransactionDataSource) : TransactionRepository{
        return TransactionRepository(transactionDataSource)
    }
}