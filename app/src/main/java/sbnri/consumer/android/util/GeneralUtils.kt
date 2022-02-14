package sbnri.consumer.android.util

import android.content.Context
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import sbnri.consumer.android.R
import sbnri.consumer.android.adapters.ItemEncapsulator
import sbnri.consumer.android.util.ActivityUtils.dpToPx
import sbnri.consumer.android.util.ActivityUtils.getItemEncapsulatorFromObject

import java.math.BigInteger

fun <T> convertCollectionOfListsToSingularLists(collections: Collection<List<T>>?): List<T> {
    return collections?.flatten() ?: emptyList()
}

fun <T> singleElementAsArrayList(item: T?): ArrayList<T> {
    return if (item == null) arrayListOf() else arrayListOf(item)
}


fun isAlphabetsOnly(string: String?): Boolean {
    return string?.all { it.isLetter() } ?: false
}


fun <T> getItemEncapsulatorsFromObjects(items: List<T>?): List<ItemEncapsulator> {
    return if (items == null || items.isEmpty()) {
        java.util.ArrayList()
    } else items.filter { it != null }.map { getItemEncapsulatorFromObject(it) }
}

fun encapsulateItems(type: Int, arrayList: List<*>?): List<ItemEncapsulator> {
    return arrayList?.filterNotNull()?.map { ItemEncapsulator(type, it) } ?: emptyList()
}

fun <T> encapsulateItem(type: Int, obj: T): ItemEncapsulator {
    return ItemEncapsulator(type, obj)
}

fun getType(c: Class<*>): Int {
    return BigInteger(c.simpleName.toByteArray()).toInt() + c.simpleName.hashCode() * 31
}


fun isListNullOrEmpty(list: List<*>?): Boolean {
    return list.isNullOrEmpty()
}

fun getFileExtension(fileName: String?): String {
    return fileName?.substringAfterLast(".") ?: ""
}

fun addStepsDynamically(context: Context?, layout: LinearLayout?, string: List<String?>?) {
    if (string?.isNotEmpty() == true) {
        string.forEach {
            layout?.addView(context?.let { context -> getTextView(it, context) })
        }
    }
}

fun getTextView(text: String?, context: Context): TextView? {
    val textView = TextView(context)
    textView.setPadding(dpToPx(0F, context), dpToPx(0F, context), dpToPx(0F, context), dpToPx(0F, context))
    textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.43f, context.resources.displayMetrics), 1.0f)
    textView.text = text ?: ""
    textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
    return textView
}

fun doesStringContainAlphabets(string: String?): Boolean {
    return string?.any { c -> c.isLetter() } ?: false
}





fun setUpHomeItems() : ArrayList<String>
{
    return arrayListOf("About Us","Committes","Dignitaries Message",
    "Events","MemberShip","Gallery","VCCI Member List","Contact","Member's Login","Settings")
}
