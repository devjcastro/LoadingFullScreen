package com.devjcastro.loadingfullscreen

class LoadingBuilder {
    fun build(type: LoadingType): ILoading {
        return when(type) {
            LoadingType.DEFAULT -> DefaultLoadingFactory().create()
        }
    }
}