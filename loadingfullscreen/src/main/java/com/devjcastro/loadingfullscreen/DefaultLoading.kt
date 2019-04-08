package com.devjcastro.loadingfullscreen

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.devjcastro.loadingfullscreen.params.LoadingDefaultParams
import kotlin.reflect.KClass

class DefaultLoading: ILoading {

    private var popupWindow: PopupWindow? = null
    var backgroundTransparency = 0.8f
    var marginStart = 50
    var maringEnd = 50

    var title = "Loading..."
    var viewTitle: TextView? = null

    var description = ""
    var viewDescription: TextView? = null

    override fun <T : Any> init(params: T, kClass: KClass<T>) {
        if (kClass == LoadingDefaultParams::class) {
            val attrs = (params as LoadingDefaultParams)
            attrs.title?.let {
                title = it
            }

            attrs.description?.let {
                description = it
            }
            backgroundTransparency = attrs.backgroundTransparency
        }
    }

    override fun show(context: Context) {

        init(LoadingDefaultParams().apply {
            title = "Esto es una prueba"
            description = "Esto es otra prueba"
        }, LoadingDefaultParams::class)

        if (popupWindow == null) {
            popupWindow = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        popupWindow?.let {
            if (!it.isShowing) {
                val window = (context as Activity).window
                popupWindow?.contentView = createContentView(context)
                window.decorView.rootView.post {
                    popupWindow?.showAtLocation(window.decorView.rootView, Gravity.CENTER, 0, 0)
                }

            }
        }
    }

    override fun <T : Any> show(context: Context, params: T, kClass: KClass<T>) {

        init(params, kClass)

        if (popupWindow == null) {
            popupWindow = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        popupWindow?.let {
            if (!it.isShowing) {
                val window = (context as Activity).window
                popupWindow?.contentView = createContentView(context)
                window.decorView.rootView.post {
                    popupWindow?.showAtLocation(window.decorView.rootView, Gravity.CENTER, 0, 0)
                }

            }
        }
    }

    override fun dismiss() = popupWindow?.dismiss()

    override fun getInstance() = popupWindow

    private fun getContainerText(context: Context): View {

        val paramsLayout = ConstraintLayout.LayoutParams(0,
            ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
            marginEnd = maringEnd
        }

        val linearLayout = LinearLayout(context).apply {
            id = View.generateViewId()
            layoutParams = paramsLayout
            orientation = LinearLayout.VERTICAL
        }

        viewTitle = TextView(context).apply {
            id = ViewGroup.generateViewId()
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.TextAppearance_AppCompat_Display1)
            }
            else {
                setTextAppearance(context, R.style.TextAppearance_AppCompat_Display1)
            }
        }
        linearLayout.addView(viewTitle)



        viewDescription = TextView(context).apply {
            id = ViewGroup.generateViewId()
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            maxLines = 2
            ellipsize = TextUtils.TruncateAt.END
        }
        linearLayout.addView(viewDescription)

        return linearLayout
    }

    private fun createContentView(context: Context): View {

        val mainLayout = ConstraintLayout(context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundColor(Color.parseColor("#ffffff"))
            alpha = backgroundTransparency
        }

        //Progressbar
        var paramsProgressBar = ConstraintLayout.LayoutParams(200, 200)
        paramsProgressBar.marginStart = marginStart
        val progressBar = ProgressBar(context).apply {
            id = View.generateViewId()
            layoutParams = paramsProgressBar
        }
        mainLayout.addView(progressBar)

        //LinearLayout Text
        val linearLayout = getContainerText(context)
        mainLayout.addView(linearLayout)


        var constraintSet = ConstraintSet()
        constraintSet.clone(mainLayout)

        constraintSet.connect(progressBar.id, ConstraintSet.TOP, mainLayout.id, ConstraintSet.TOP)
        constraintSet.connect(progressBar.id, ConstraintSet.START, mainLayout.id, ConstraintSet.START)
        constraintSet.connect(progressBar.id, ConstraintSet.BOTTOM, mainLayout.id, ConstraintSet.BOTTOM)
        constraintSet.connect(progressBar.id, ConstraintSet.END, linearLayout.id, ConstraintSet.START)
        constraintSet.applyTo(mainLayout)



        constraintSet = ConstraintSet()
        constraintSet.clone(mainLayout)
        constraintSet.connect(linearLayout.id, ConstraintSet.TOP, mainLayout.id, ConstraintSet.TOP)
        constraintSet.connect(linearLayout.id, ConstraintSet.BOTTOM, mainLayout.id, ConstraintSet.BOTTOM)
        constraintSet.connect(linearLayout.id, ConstraintSet.END, mainLayout.id, ConstraintSet.END)
        constraintSet.connect(linearLayout.id, ConstraintSet.START, progressBar.id, ConstraintSet.END)
        //constraintSet.constrainWidth(linearLayout.id, 0)
        constraintSet.constrainDefaultWidth(linearLayout.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)


        constraintSet.createHorizontalChain(
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
            intArrayOf(progressBar.id, linearLayout.id),
            null,
            ConstraintSet.CHAIN_PACKED
        )
        constraintSet.applyTo(mainLayout)



        setupText()

        return mainLayout
    }

    private fun setupText() {
        viewTitle?.text = title
        viewDescription?.text = description
    }
}