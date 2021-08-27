package dev.lonelyteapot.bicon

import android.text.Editable
import android.text.TextWatcher

/**
 * Removes leading zeros from text.
 * Adds exactly one zero if it's empty.
 */
class LeadingZeroTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(text: Editable?) {
        if (text == null)
            return
        if (text.isEmpty())
            text.append('0')
        while (text.length > 1 && text.startsWith('0'))
            text.delete(0, 1)
    }
}

/**
 * Edits text to match 'XX/XX/XX...' format after it's changed.
 * Removes non-digits.
 */
class DateTextWatcher : TextWatcher {
    var wasJustCalled = false

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(text: Editable?) {
        if (wasJustCalled || text == null)
            return
        wasJustCalled = true
        var idx = 0
        while (idx < text.length) {
            if (!text[idx].isDigit()) {
                text.delete(idx, idx + 1)
            } else {
                idx += 1
            }
            if ((idx + 1) % 3 == 0) {
                text.insert(idx, "/")
                idx += 1
            }
        }
        wasJustCalled = false
    }
}