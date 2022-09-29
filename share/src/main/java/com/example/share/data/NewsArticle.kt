package com.example.share.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Entity(tableName = "news_articles")
@Serializable
data class NewsArticle(
    @SerialName("title")
    val title: String?,
    @SerialName("publishedAt")
    @Serializable(with = PublishedSerializer::class)
    val publishedAt: String?,
    @SerialName("url")
    @PrimaryKey val url: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String?,
    @SerialName("isBookmarked")
    val isBookmarked: Boolean,
    @SerialName("updatedAt")
    val updatedAt: Long = System.currentTimeMillis()
)


@Serializer(forClass = NewsArticle::class)
object PublishedSerializer : KSerializer<String> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("PublishedSerializerString")

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        val str = decoder.decodeString()
        return str.substring(8,10) + "/" + str.substring(5,7) + "/" + str.substring(0,4)
    }
}