package com.ralphordanza.budgetup.core.interactors

data class Interactors(
    //User session
    val registerUser: RegisterUser,
    val loginUser: LoginUser,
    val logoutUser: LogoutUser
)