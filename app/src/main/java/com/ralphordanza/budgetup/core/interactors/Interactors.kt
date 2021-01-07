package com.ralphordanza.budgetup.core.interactors

data class Interactors(
    //User session
    val registerUser: RegisterUser,
    val saveToFirestore: SaveToFirestore,
    val loginUser: LoginUser,
    val loginAsGuest: LoginAsGuest,
    val logoutUser: LogoutUser,

    //Wallet
    val getWallets: GetWallets,
    val getTotal: GetTotal,
    val addWallet: AddWallet,
    val deleteWallet: DeleteWallet,

    //Calculator
    val calculatorCompute: CalculatorCompute
)