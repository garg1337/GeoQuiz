package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private companion object {
        private const val TAG = "MainActivity"
    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }
        binding.questionTextView.setOnClickListener {
            nextQuestion()
        }
        binding.prevButton.setOnClickListener {
            previousQuestion()
        }
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1).mod(questionBank.size)
        updateQuestion()
    }

    private fun previousQuestion() {
        currentIndex = (currentIndex - 1).mod(questionBank.size)
        updateQuestion()
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex]
        val questionTextResId = question.textResId
        binding.questionTextView.setText(questionTextResId)
        updateAnswerButtonsForCurrentQuestion()
    }

    private fun updateAnswerButtonsForCurrentQuestion() {
        val question = questionBank[currentIndex]
        if (question.userAnswer != null) {
            disableAnswerButtons()
        } else {
            enableAnswerButtons()
        }
    }

    private fun enableAnswerButtons() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun disableAnswerButtons() {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val question = questionBank[currentIndex]
        question.userAnswer = userAnswer
        updateAnswerButtonsForCurrentQuestion()
        val correctAnswer = question.answer
        val messageResId = if (question.userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        val finalScore = finalScore()
        if (finalScore != null) {
            val scorePercent = finalScore * 100
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING

            Toast.makeText(
                this, "Final score: ${df.format(scorePercent)}%",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun finalScore(): Double? {
        var numRight = 0
        questionBank.forEach {
            if (it.userAnswer == null) return null
            if (it.userAnswer == it.answer) numRight++
        }
        return numRight.toDouble() / questionBank.size.toDouble()
    }
}