package com.ptk.ptk_news.ui.ui_states


data class ProfileUIStates(

    val userName: String = "",
    val themeId: Int = 1,

    val availableTextSize: List<String> = listOf("S", "M", "L"),
    val selectedTextSize: String = "M"

)