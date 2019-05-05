package com.savedroid.wish.network.errorhandler

import com.savedroid.wish.network.model.Kexception
import com.savedroid.wish.network.model.StatusCode

open class ApiErrorHandler {

    open fun getExceptionType(response: retrofit2.Response<*>): Exception {
        return Kexception(response.errorBody(), response.message(), null, StatusCode(response.code()))
    }


}