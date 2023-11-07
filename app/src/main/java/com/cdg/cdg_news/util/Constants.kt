package com.cdg.cdg_news.util

import com.cdg.cdg_news.model.ui_model.CountryModel


object Constants {
    const val BASE_URL = "https://newsapi.org/"
//    const val API_KEY = "00e93666295040cd91bf645774a191f3"
//    const val API_KEY = "e6efa7af141a41238111e8a74f730a0c"
//    const val API_KEY = "f79e1eac22994912a3a0ef3453477863"
//    const val API_KEY = "c24f80d4e23b40bcba1c127d91102039"
    const val API_KEY = "18dfad835f4347f2a0eb7c2ad321ca9c"

}

fun getCountriesList() = listOf(
    CountryModel(0, "All Countries", ""),
    CountryModel(1, "Argentina", "ar"),
    CountryModel(2, "Australia", "au"),
    CountryModel(3, "Austria", "at"),
    CountryModel(4, "Belgium", "be"),
    CountryModel(5, "Brazil", "br"),
    CountryModel(6, "Bulgaria", "bg"),
    CountryModel(7, "Canada", "ca"),
    CountryModel(8, "China", "cn"),
    CountryModel(9, "Colombia", "co"),
    CountryModel(10, "Cuba", "cu"),
    CountryModel(11, "Czech Republic", "cz"),
    CountryModel(12, "Egypt", "eg"),
    CountryModel(13, "France", "fr"),
    CountryModel(14, "Germany", "de"),
    CountryModel(15, "Greece", "gr"),
    CountryModel(16, "Hong Kong", "hk"),
    CountryModel(17, "Hungary", "hu"),
    CountryModel(18, "India", "in"),
    CountryModel(19, "Indonesia", "id"),
    CountryModel(20, "Ireland", "ie"),
    CountryModel(21, "Israel", "il"),
    CountryModel(22, "Italy", "it"),
    CountryModel(23, "Japan", "jp"),
    CountryModel(24, "Latvia", "lv"),
    CountryModel(25, "Lithuania", "lt"),
    CountryModel(26, "Malaysia", "my"),
    CountryModel(27, "Mexico", "mx"),
    CountryModel(28, "Morocco", "ma"),
    CountryModel(29, "Netherlands", "nl"),
    CountryModel(30, "New Zealand", "nz"),
    CountryModel(31, "Nigeria", "ng"),
    CountryModel(32, "Norway", "no"),
    CountryModel(33, "Philippines", "ph"),
    CountryModel(34, "Poland", "pl"),
    CountryModel(35, "Portugal", "pt"),
    CountryModel(36, "Romania", "ro"),
    CountryModel(37, "Russia", "ru"),
    CountryModel(38, "Saudi Arabia", "sa"),
    CountryModel(39, "Serbia", "rs"),
    CountryModel(40, "Singapore", "sg"),
    CountryModel(41, "Slovakia", "sk"),
    CountryModel(42, "Slovenia", "si"),
    CountryModel(43, "South Africa", "za"),
    CountryModel(44, "South Korea", "kr"),
    CountryModel(45, "Sweden", "se"),
    CountryModel(46, "Switzerland", "ch"),
    CountryModel(47, "Taiwan", "tw"),
    CountryModel(48, "Thailand", "th"),
    CountryModel(49, "Turkey", "tr"),
    CountryModel(50, "UAE", "ae"),
    CountryModel(51, "Ukraine", "ua"),
    CountryModel(52, "United Kingdom", "uk"),
    CountryModel(53, "United States", "us"),
    CountryModel(54, "Venuzuela", "ve"),
)
