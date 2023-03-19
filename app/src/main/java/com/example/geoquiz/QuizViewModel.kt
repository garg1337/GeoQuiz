package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private companion object {
        private const val TAG = "QuizViewModel"
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}