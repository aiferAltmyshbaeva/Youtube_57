package pl.aifer.youtube_sandbox_m6_l3.core.utils

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int?,
) {
    companion object {
        fun <T> success(data: T?): Resource<T>? {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                message = null,
                code = null
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = null,
                message = null,
                code = null
            )
        }

        fun <T> error(msg: String?, data: T?, code: Int?): Resource<T> {
            return Resource(
                status = Status.ERROR,
                data = data,
                message = msg,
                code = code
            )
        }
    }
}
