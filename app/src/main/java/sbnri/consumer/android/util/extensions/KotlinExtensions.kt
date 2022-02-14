package sbnri.consumer.android.util.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import sbnri.consumer.android.R


fun Button.disable()
{
    //getBackground().setColorFilter(resources.getColor(R.color.semitransparent), PorterDuff.Mode.MULTIPLY)
    alpha = 0.5f
    setClickable(false)
}


fun Button.enable()
{
        alpha = 1f
      //  getBackground().setColorFilter(resources.getColor(R.color.dodgerBlue), PorterDuff.Mode.MULTIPLY)
        setClickable(true)
    }

fun isNullTrue(obj:Any?):Boolean
{
    return obj == null
}

fun View.hideView()
{
    this.visibility = View.GONE
}

fun View.ShowView()
{
    this.visibility = View.VISIBLE
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}

