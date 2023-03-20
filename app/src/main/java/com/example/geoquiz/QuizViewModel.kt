package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private companion object {
        private const val TAG = "QuizViewModel"
        private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
        private const val QUESTION_BANK_KEY = "QUESTION_BANK_KEY"
        private const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private val currentQuestion: Question
        get() = questionBank[currentIndex]
    val currentQuestionAnswer: Boolean
        get() = currentQuestion.answer
    val currentQuestionText: Int
        get() = currentQuestion.textResId
    val currentQuestionUserAnswer: Boolean?
        get() = currentQuestion.userAnswer

    val finalScore: Double?
        get() {
            var numRight = 0
            questionBank.forEach {
                if (it.userAnswer == null) return null
                if (it.userAnswer == it.answer) numRight++
            }
            return numRight.toDouble() / questionBank.size.toDouble()
        }

    fun moveToNext() {
        currentIndex = (currentIndex + 1).mod(questionBank.size)
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1).mod(questionBank.size)
    }

    fun setCurrentQuestionAnswer(answer: Boolean) {
        currentQuestion.userAnswer = answer
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}