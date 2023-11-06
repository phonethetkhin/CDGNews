package com.ptk.ptk_news.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tbl_article")
data class ArticleEntity(

    @ColumnInfo(name = "publishedAt")
    @SerialName("publishedAt")
    val publishedAt: String? = null,

    @ColumnInfo(name = "author")
    @SerialName("author")
    val author: String? = null,

    @ColumnInfo(name = "urlToImage")
    @SerialName("urlToImage")
    val urlToImage: String? = null,

    @ColumnInfo(name = "description")
    @SerialName("description")
    val description: String? = null,

    @ColumnInfo(name = "title")
    @SerialName("title")
    val title: String? = null,

    @ColumnInfo(name = "url")
    @SerialName("url")
    val url: String? = null,

    @ColumnInfo(name = "content")
    @SerialName("content")
    val content: String? = null,

    var postComment: String? = "",
    var commentTime: String? = "",
    var isHeadLine: Boolean = false,
    var isBookMark: Boolean = false,
    var isFav: Boolean = false,

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(publishedAt)
        parcel.writeString(author)
        parcel.writeString(urlToImage)
        parcel.writeString(description)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(content)
        parcel.writeString(postComment)
        parcel.writeString(commentTime)
        parcel.writeByte(if (isHeadLine) 1 else 0)
        parcel.writeByte(if (isBookMark) 1 else 0)
        parcel.writeByte(if (isFav) 1 else 0)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleEntity> {
        override fun createFromParcel(parcel: Parcel): ArticleEntity {
            return ArticleEntity(parcel)
        }

        override fun newArray(size: Int): Array<ArticleEntity?> {
            return arrayOfNulls(size)
        }
    }
}
