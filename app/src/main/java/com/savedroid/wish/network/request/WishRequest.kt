package com.savedroid.wish.network.request

import com.savedroid.wish.network.errorhandler.ApiErrorHandler
import com.savedroid.wish.network.requesthandler.GenericRequestHandler
import com.savedroid.wish.network.client.WishApi
import com.savedroid.wish.network.errorhandler.WishErrorHandler
import com.savedroid.wish.network.NetworkModule
import com.savedroid.wish.network.model.ApiResponse
import retrofit2.Call

class WishRequest : GenericRequestHandler<ApiResponse>() {
    private val wishApi: WishApi by lazy {
        NetworkModule.createRetrofit().create(WishApi::class.java)
    }

    override val errorHandler: ApiErrorHandler = WishErrorHandler()

    override fun makeRequest(): Call<ApiResponse> {
        return wishApi.wish()
    }
}