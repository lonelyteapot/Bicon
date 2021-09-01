package dev.lonelyteapot.bicon

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import dev.lonelyteapot.bicon.databinding.ActivityMainBinding
import java.lang.NumberFormatException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import kotlin.math.max

val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var total: Long = 0
        set(value) {
            field = value
            val str = if (value == 0L) {""} else {value.toString()}
            if (binding.etTotal.text.toString() != str) {
                binding.etTotal.setText(str)
            }
        }

    private var until: LocalDate = LocalDate.now()
        set(value) {
            field = value
            val str = value.format(DATE_FORMATTER)
            if (binding.etUntil.text.toString() != str) {
                binding.etUntil.setText(str)
            }
        }

    private var daysLeft: Long = 0

    private var forEachDay: Long = 0
        set(value) {
            field = value
            binding.etForEachDay.setText(value.toString())
        }

    private var forToday: Long = 0
        set(value) {
            field = value
            binding.etForToday.setText(value.toString())
        }

    fun calculate() {
        daysLeft = max(ChronoUnit.DAYS.between(LocalDate.now(), until), 0L)
        forEachDay = total / (daysLeft + 1)
        forToday = forEachDay
    }

    fun addNewSpending(value: Long) {
        total -= value
        forToday -= value
        forEachDay = max((total - forToday) / max(daysLeft, 1L), max(0L, forToday))
        binding.etNewRecord.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        until = LocalDate.now()

        binding.etTotal.doAfterTextChanged { text ->
            if (text.isNullOrEmpty()) {
                total = 0
                calculate()
            }
            else {
                try {
                    val new = text.toString().toLong()
                    if (total != new) {
                        total = new
                        calculate()
                    }
                } catch (e: NumberFormatException) {
                }
            }
        }

        binding.etUntil.doAfterTextChanged { text ->
            try {
                until = LocalDate.parse(text, DATE_FORMATTER)
                calculate()
            } catch (e: DateTimeParseException) {
            }
        }

        binding.etNewRecord.setOnEditorActionListener { textView, action, keyEvent ->
            when (action) {
                EditorInfo.IME_ACTION_DONE -> {
                    try {
                        addNewSpending(textView.text.toString().toLong())
                    } catch (e: NumberFormatException) {
                    }
                    true
                }
                else -> false
            }
        }

        setContentView(binding.root)
    }
}