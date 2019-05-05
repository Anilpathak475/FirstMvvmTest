package com.savedroid.wish.network.model

import com.savedroid.wish.network.model.StatusCode
import okhttp3.ResponseBody

open class Kexception(val errorBody: ResponseBody?,
                      message: String?,
                      cause: Throwable?,
                      val statusCode: StatusCode
) : Exception(message, cause)