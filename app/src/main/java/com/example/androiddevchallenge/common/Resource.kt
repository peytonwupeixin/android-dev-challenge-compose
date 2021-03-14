package com.example.androiddevchallenge.common
/**
 * A generic class that holds a value with its success, error or loading status.
 *
 * @author PeytonWu
 * @since 2021/3/13
 */
data class Resource<T>(val status: Int, val data: T?, val message: String?) {

    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val LOADING = 2

        fun <T> success(data: T) = Resource(SUCCESS, data, null)
        fun <T> error(msg: String) = Resource<T>(ERROR, null, msg)
        fun <T> loading() = Resource<T>(LOADING, null, null)
    }
}