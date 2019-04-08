package com.devjcastro.loadingfullscreen

import android.content.Context
import android.widget.PopupWindow
import kotlin.reflect.KClass

interface ILoading {
    fun <T: Any> init(params: T, kClass: KClass<T>)
    fun show(context: Context)
    fun <T: Any> show(context: Context, params: T, kClass: KClass<T>)
    fun dismiss(): Unit?
    fun getInstance(): PopupWindow?
}