package com.devjcastro.loadingfullscreen

import android.content.Context
import com.devjcastro.loadingfullscreen.params.LoadingDefaultParams
import java.lang.RuntimeException

class LoadingFS {
    companion object {

        var instance: ILoading? = null
        var type = LoadingType.DEFAULT
        var attrs: Any? = null

        fun builder(type: LoadingType = LoadingType.DEFAULT, attrs: Any? = null) {
            instance?.dismiss()
            instance = LoadingBuilder().build(type)
            this.type = type
            this.attrs = attrs
        }

        fun show(context: Context) {
            if (instance == null) {
                throw RuntimeException("Must create Loading")
            }

            when (type) {
                LoadingType.DEFAULT -> {
                    instance?.show(context, attrs as LoadingDefaultParams, LoadingDefaultParams::class)
                }
            }
        }

        inline fun <reified T: Any> show(context: Context, params: T) {
            if (instance == null) {
                throw RuntimeException("Must create Loading")
            }

            val kClass = when(T::class) {
                LoadingDefaultParams::class -> {
                    (params as LoadingDefaultParams)
                    LoadingDefaultParams::class
                }
                else -> {
                    (params as LoadingDefaultParams)
                    LoadingDefaultParams::class
                }
            }

            instance?.show(context, params, kClass)
        }

        fun dismiss() = instance?.dismiss()
    }
}