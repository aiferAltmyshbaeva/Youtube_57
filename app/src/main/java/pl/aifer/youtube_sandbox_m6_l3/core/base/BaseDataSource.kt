package pl.aifer.youtube_sandbox_m6_l3.core.base

import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null || response.code() in 200..299) {
                    Result.success(body!!)
                } else {
                    Result.failure(Throwable(response.message()))
                }
            } else {
                Result.failure(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }
}