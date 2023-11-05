package com.ptk.ptk_news.model.dto.response

import com.ptk.ptk_news.db.entity.SourceEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SourceResponseModel(

    @SerialName("sources")
    val sources: List<SourceEntity> = listOf(),

    @SerialName("status")
    val status: String? = null
)


