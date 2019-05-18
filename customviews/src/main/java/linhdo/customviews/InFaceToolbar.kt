package linhdo.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.getDrawableOrThrow
import kotlinx.android.synthetic.main.inface_toolbar.view.*
import linhdo.customviews.logging.InLogging
import linhdo.customviews.logging.Logger

/***
 * @author: Lac
 * 18-May-19 - 1:25 PM
 */
class InFaceToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Toolbar(context, attrs, defStyleAttr) {
    private lateinit var onLeftClick: (idRes: Int) -> Unit
    private lateinit var onRightClick: () -> Unit
    private lateinit var view: View

    init {
        initView(context, attrs, defStyleAttr)
    }

    enum class LeftIcon(@DrawableRes val idRes: Int) {
        MENU(R.drawable.ic_menu),
        BACKABLE(R.drawable.ic_arrow_back)
    }

    fun setOnLeftClickListener(block: (idRes: Int) -> Unit) {
        onLeftClick = block
    }

    fun setOnRightClickListener(block: () -> Unit) {
        onRightClick = block
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        view = View.inflate(context, R.layout.inface_toolbar, this)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.InFaceToolbar, defStyleAttr, 0)

        if (typedArray.hasValue(R.styleable.InFaceToolbar_leftDrawable)) {
            val leftIcon = when (typedArray.getInt(R.styleable.InFaceToolbar_leftDrawable, 0)) {
                0 -> LeftIcon.MENU
                else -> LeftIcon.BACKABLE
            }
            setLeftIcon(leftIcon)
        } else view.ivLeftIcon?.visibility = View.GONE

        if (typedArray.hasValue(R.styleable.InFaceToolbar_rightDrawable)) {
            val rightIcon = typedArray.getDrawableOrThrow(R.styleable.InFaceToolbar_rightDrawable)
            setRightIcon(rightIcon)
        } else view.ivRightIcon?.visibility = View.GONE

        if (typedArray.hasValue(R.styleable.InFaceToolbar_inTitle)) {
            val title = typedArray.getString(R.styleable.InFaceToolbar_inTitle) ?: ""
            if (title.isEmpty()) view.tvTitle?.visibility = View.INVISIBLE
            setTitle(title)
        } else view.tvTitle?.visibility = View.GONE

        if (typedArray.hasValue(R.styleable.InFaceToolbar_inSubTitle)) {
            val subTitle = typedArray.getString(R.styleable.InFaceToolbar_inSubTitle) ?: ""
            if (subTitle.isEmpty()) view.tvSubTitle?.visibility = View.INVISIBLE
            setSubTitle(subTitle)
        } else view.tvSubTitle?.visibility = View.GONE

        typedArray.recycle()
    }

    private fun setLeftIcon(leftIconType: LeftIcon) {
        view.ivLeftIcon?.apply {
            visibility = View.VISIBLE
            setImageResource(leftIconType.idRes)
            setOnClickListener {
                if (::onLeftClick.isInitialized) onLeftClick(leftIconType.idRes)
                else InLogging(context, "Left button listener still not implementation", Logger.ERROR)
            }
        }
    }

    private fun setRightIcon(iconDrawable: Drawable) {
        view.ivRightIcon?.apply {
            visibility = View.VISIBLE
            setImageDrawable(iconDrawable)
            setOnClickListener {
                if (::onRightClick.isInitialized) onRightClick()
                else InLogging(context, "Right button listener still not implementation", Logger.ERROR)
            }
        }
    }

    private fun setTitle(title: String) {
        view.tvTitle?.apply {
            visibility = View.VISIBLE
            text = title
        }
    }

    fun setSubTitle(subTitle: String) {
        view.tvSubTitle?.apply {
            visibility = View.VISIBLE
            text = subTitle
        }
    }
}