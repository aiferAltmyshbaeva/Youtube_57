package pl.aifer.youtube_sandbox_m6_l3.utils

interface ResourceProvider {
    fun getStringWithKey(resId: Int, keyResId: String): String
    fun getString(resId: Int): String
}