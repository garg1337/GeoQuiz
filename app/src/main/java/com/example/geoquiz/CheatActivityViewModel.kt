package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CheatActivityViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private companion object {
        private const val TAG = "QuizViewModel"
        private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
        private const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
    }

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

}