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
            val str = value.toString()
            if (binding.etTotal.text.toString() != str) {
                binding.etTotal.setText(str)
            }
        }

    private var until: LocalDate = LocalDate.now()

    private var daysLeft: Long = 1

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
        daysLeft = max(ChronoUnit.DAYS.between(LocalDate.now(), until) + 1, 1)
        forEachDay = total / daysLeft
        forToday = forEachDay
    }

    fun addNewSpending(value: Long) {
        total -= value
        forToday -= value
        forEachDay = (total - max(forToday, 0)) / max(daysLeft - 1, 1)
        binding.etNewRecord.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.etTotal.doAfterTextChanged { text ->
            try {
                val new = text.toString().toLong()
                if (total != new) {
                    total = new
                    calculate()
                }
            } catch (e: NumberFormatException) {
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
                    addNewSpending(textView.text.toString().toLong())
                    true
                }
                else -> false
            }
        }

        setContentView(binding.root)
    }
}