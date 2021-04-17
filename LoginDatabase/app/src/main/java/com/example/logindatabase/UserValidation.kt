package com.example.logindatabase

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout

class UserValidation(private val context: Context) {
    fun isInputEditTextFilled(textInputEditText: EditText, textInputLayout: ConstraintLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            ConstraintLayout.AUTOFILL_HINT_NAME
            hideKeyboardFrom(textInputEditText)
            return false
        } else {
            ConstraintLayout.AUTOFILL_TYPE_NONE
        }
        return true
    }
    fun isInputEditTextEmail(textInputEditText: EditText, textInputLayout: ConstraintLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            ConstraintLayout.AUTOFILL_HINT_EMAIL_ADDRESS
            hideKeyboardFrom(textInputEditText)
            return false
        } else {
            ConstraintLayout.AUTOFILL_TYPE_NONE
        }
        return true
    }
    fun isInputEditTextMatches(EditText1:EditText, EditText2:EditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value1 = EditText1.text.toString().trim()
        val value2 = EditText2.text.toString().trim()
        if (!value1.contentEquals(value2)) {
            textInputLayout.error = message
            hideKeyboardFrom(EditText2)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return true
    }
    private fun hideKeyboardFrom(view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}