package com.bagus2x.toko.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity("store")
data class Store @JvmOverloads constructor(
    @SerialName("store_id")
    @ColumnInfo("store_id")
    @PrimaryKey(autoGenerate = false)
    val storeId: String,
    @SerialName("account_id")
    @ColumnInfo("account_id")
    val accountId: String,
    @SerialName("account_name")
    @ColumnInfo("account_name")
    val accountName: String,
    @SerialName("address")
    @ColumnInfo("address")
    val address: String,
    @SerialName("area_id")
    @ColumnInfo("area_id")
    val areaId: String,
    @SerialName("area_name")
    @ColumnInfo("area_name")
    val areaName: String,
    @SerialName("channel_id")
    @ColumnInfo("channel_id")
    val channelId: String,
    @SerialName("channel_name")
    @ColumnInfo("channel_name")
    val channelName: String,
    @SerialName("dc_id")
    @ColumnInfo("dc_id")
    val dcId: String,
    @SerialName("dc_name")
    @ColumnInfo("dc_name")
    val dcName: String,
    @SerialName("latitude")
    @ColumnInfo("latitude")
    val latitude: String?,
    @SerialName("longitude")
    @ColumnInfo("longitude")
    val longitude: String?,
    @SerialName("region_id")
    @ColumnInfo("region_id")
    val regionId: String,
    @SerialName("region_name")
    @ColumnInfo("region_name")
    val regionName: String,
    @SerialName("store_code")
    @ColumnInfo("store_code")
    val storeCode: String,
    @SerialName("store_name")
    @ColumnInfo("store_name")
    val storeName: String,
    @SerialName("subchannel_id")
    @ColumnInfo("subchannel_id")
    val subchannelId: String,
    @SerialName("subchannel_name")
    @ColumnInfo("subchannel_name")
    val subchannelName: String,
    @ColumnInfo("distance")
    val distance: String? = null,
    @ColumnInfo("visit_histories")
    val visitHistory: List<VisitHistory> = emptyList()
)
