package com.example.gonetexam.api

import com.example.gonetexam.common.utils.*
import com.google.gson.Gson
import okhttp3.Headers
import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    UnavailableService(0),
    SocketTimeOut(-1)
}

class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception, data: T? = null): Resource<T> {
        e.printStackTrace()
        return when (e) {
            is HttpException -> Resource.error(
                getErrorMessage(e.code()),
                data
            )
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                data
            )
            else -> Resource.error(
                getErrorMessage(ErrorCodes.UnavailableService.code),
                data
            )
        }
    }

    fun <T : Any> handleException(headers: Headers, data: T? = null): Resource<T> {
        val jsonHeader = headers["exception"]?.replace("\"\",", "\":")
        val exceptionsInfo = Gson().fromJson(jsonHeader, Array<ExceptionInfo>::class.java)
        val exceptionInfo = exceptionsInfo[0]
        return Resource.error(getErrorMessage(exceptionInfo.code, exceptionInfo.httpStatus), data)
    }

    fun <T : Any> handleException(errorCodes: ErrorCodes, data: T? = null): Resource<T> {
        return Resource.error(getErrorMessage(errorCodes.code), data)
    }

    private fun getErrorMessage(code: Int, httpStatus: String? = null): ErrorModel {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> ErrorModel(ErrorModel.Type.TOLERABLE, TIMEOUT_EXCEPTION)
            400 -> ErrorModel(ErrorModel.Type.TOLERABLE, HTTP_ERROR_CODE_400)
            401 -> ErrorModel(ErrorModel.Type.TOLERABLE, HTTP_ERROR_CODE_401)
            403 -> ErrorModel(ErrorModel.Type.TOLERABLE, HTTP_ERROR_CODE_403)
            404 -> ErrorModel(ErrorModel.Type.TOLERABLE, HTTP_ERROR_CODE_404)
            500 -> ErrorModel(ErrorModel.Type.TOLERABLE, HTTP_ERROR_CODE_500)
            else -> ErrorModel(ErrorModel.Type.TOLERABLE, String.format(GENERAL_EXCEPTION, code))
        }
    }
}
