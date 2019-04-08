package com.devjcastro.loadingfullscreen

class DefaultLoadingFactory: AbstractLoadingFactory {

    override fun create(): ILoading {
        return DefaultLoading()
    }
}