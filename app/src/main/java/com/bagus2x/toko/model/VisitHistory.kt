@file:UseSerializers(FileSerializer::class, LocalDateTimeSerializer::class)

package com.bagus2x.toko.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Serializable
data class VisitHistory(
    @SerialName("location")
    val location: Location,
    @SerialName("photo")
    val photo: File,
    @SerialName("visitedAt")
    val visitedAt: LocalDateTime,
)

object FileSerializer : KSerializer<File> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("File", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): File {
        return File(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: File) {
        encoder.encodeString(value.path)
    }
}

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("File", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return Instant.ofEpochMilli(decoder.decodeLong()).atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeLong(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }
}

