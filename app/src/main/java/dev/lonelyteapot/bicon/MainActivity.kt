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
        binding.editNewSpending.addTextChangedListener(LeadingZeroTextWatcher())
        binding.editTotalLeft.addTextChangedListener(LeadingZeroTextWatcher())
        binding.editDateUntil.addTextChangedListener(DateTextWatcher())
    }
}