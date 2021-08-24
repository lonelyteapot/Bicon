package dev.lonelyteapot.bicon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import dev.lonelyteapot.bicon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onKeyboardKeyClick(key: View) {
        val orig = binding.editNewSpending.text.toString()
        binding.editNewSpending.text = when (key) {
            binding.keyboard.keyEnter -> {
                "0"
            }
            binding.keyboard.keyBackspace -> {
                if (orig.length == 1) "0"
                else orig.dropLast(1)
            }
            else -> {
                val text = (key as Button).text
                if (orig == "0") text
                else orig + text
            }
        }
    }
}