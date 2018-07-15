package com.example.keshavaggarwal.curofyassignment.utils

/**
 * Created by KeshavAggarwal on 14/07/18.
 */

import com.example.keshavaggarwal.curofyassignment.utils.Status.*
/**
 * A generic class that holds a value(data) with its loading status.
 * @param <T>
</T> */

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }

    }
}