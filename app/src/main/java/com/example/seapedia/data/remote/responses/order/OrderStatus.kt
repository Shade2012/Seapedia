package com.example.seapedia.data.remote.responses.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus(val displayName: String) {

    @SerialName("Dikembalikan")
    RETURN("Dikembalikan"),

    @SerialName("Pesanan Selesai")
    DONE("Pesanan Selesai"),

    @SerialName("Sedang Dikirim")
    ON_WAY("Sedang Dikirim"),

    @SerialName("Menunggu Pengirim")
    WAITING_DRIVER("Menunggu Pengirim"),

    @SerialName("Sedang Dikemas")
    PROCCESS("Sedang Dikemas"),

    @SerialName("All")
    All("All")
}