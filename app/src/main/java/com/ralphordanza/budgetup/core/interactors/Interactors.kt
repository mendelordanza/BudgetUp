package com.ralphordanza.budgetup.core.interactors

data class Interactors(
    //User session
    val registerUser: RegisterUser,
    val saveToFirestore: SaveToFirestore,
    val loginUser: LoginUser,
    val logoutUser: LogoutUser,

    //Wallet
    val getWallets: GetWallets,
    val addWallet: AddWallet
    )