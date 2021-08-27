package dev.lonelyteapot.bicon

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * EditText that forces selection/cursor to be at the end,
 * except when selecting all.
 */
class BlockedSelectionEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        val text = text ?: return
        if (selStart != 0 || selEnd != text.length) {
            setSelection(text.length)
        }
    }
}