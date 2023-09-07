package com.currency.app.di

class Resource<Any>(val requestStatus: RequestStatus, val data: Any?, val throwable: Throwable?) {

    companion object {
        fun <Any> success(data: Any): Resource<Any> {
            return Resource(
                RequestStatus.SUCCESS,
                data,
                null
            )
        }

        fun <Any> error(throwable: Throwable?): Resource<Any> {
            return Resource(
                RequestStatus.ERROR,
                null,
                throwable
            )
        }

        fun <Any> loading(): Resource<Any> {
            return Resource(
                RequestStatus.LOADING,
                null,
                null
            )
        }

    }
}