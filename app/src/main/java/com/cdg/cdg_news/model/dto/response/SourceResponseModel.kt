package com.cdg.cdg_news.model.dto.response

import com.cdg.cdg_news.db.entity.SourceEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SourceResponseModel(

    @SerialName("sources")
    val sources: List<SourceEntity> = listOf(),

    @SerialName("status")
    val status: String? = null
)


