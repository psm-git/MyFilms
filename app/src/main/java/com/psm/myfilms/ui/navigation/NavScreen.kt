package com.psm.myfilms.ui.navigation

import kotlinx.serialization.Serializable

object NavScreen {
    @Serializable
    object Home

    @Serializable
    data class Detail(val movieId: Int)
}