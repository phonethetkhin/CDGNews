package com.ptk.ptk_news.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tbl_source")
data class SourceEntity(

    @ColumnInfo(name = "country")
    @SerialName("country")
    val country: String? = null,

    @ColumnInfo(name = "name")
    @SerialName("name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    @SerialName("description")
    val description: String? = null,

    @ColumnInfo(name = "language")
    @SerialName("language")
    val language: String? = null,

    @ColumnInfo(name = "id")
    @SerialName("id")
    val id: String? = null,

    @ColumnInfo(name = "category")
    @SerialName("category")
    val category: String? = null,

    @ColumnInfo(name = "url")
    @SerialName("url")
    val url: String? = null,

    val selected: Boolean = false,

    @PrimaryKey(autoGenerate = true) val newsId: Int = 0,

)