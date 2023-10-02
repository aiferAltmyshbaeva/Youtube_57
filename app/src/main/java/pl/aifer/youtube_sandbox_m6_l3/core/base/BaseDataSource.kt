package pl.aifer.youtube_sandbox_m6_l3.core.base

import pl.aifer.youtube_sandbox_m6_l3.core.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null || response.code() in 200..299) {
                    return Resource.success(body)!!
                }

            } else {
                return Resource.error(response.message(), response.body(), response.code())
            }
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString(), null, 429)
        }
        return Resource.error("Fatal error", null, 429)
    }
}