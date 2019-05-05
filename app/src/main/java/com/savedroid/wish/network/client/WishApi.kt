package com.savedroid.wish.network.client

import com.savedroid.wish.network.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface WishApi {
    @GET("/wish/wish")
    fun wish(): Call<ApiResponse>
}