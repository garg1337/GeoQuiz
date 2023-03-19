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
    private val quizViewModel: QuizViewModel by viewModels()

    private companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

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
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun previousQuestion() {
        quizViewModel.moveToPrev()
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        updateAnswerButtonsForCurrentQuestion()
    }

    private fun updateAnswerButtonsForCurrentQuestion() {
        if (quizViewModel.currentQuestionUserAnswer != null) {
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
        quizViewModel.setCurrentQuestionAnswer(userAnswer)
        updateAnswerButtonsForCurrentQuestion()
        val messageResId =
            if (quizViewModel.currentQuestionAnswer == quizViewModel.currentQuestionUserAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        val finalScore = quizViewModel.finalScore
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
}