package com.ptk.ptk_news.model.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SourceResponseModel(

    @SerialName("sources")
    val sources: List<SourcesItem> = listOf(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class SourcesItem(

    @SerialName("country")
    val country: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("language")
    val language: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("category")
    val category: String? = null,

    @SerialName("url")
    val url: String? = null,
    
    val selected: Boolean = false,
)
