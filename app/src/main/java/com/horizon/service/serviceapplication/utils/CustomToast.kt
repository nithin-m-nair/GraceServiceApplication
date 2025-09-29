package com.horizon.service.serviceapplication.utils
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun showCustomToast(context: android.content.Context, message: String, isSuccess: Boolean) {
    val toast = Toast(context)

    val layout = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        setPadding(32, 24, 32, 24)
        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setColor(if (isSuccess) Color.parseColor("#4CAF50") else Color.parseColor("#F44336"))
        }
    }

    val textView = TextView(context).apply {
        text = message
        setTextColor(Color.WHITE)
        setTypeface(typeface, Typeface.BOLD)
        textSize = 16f
    }

    layout.addView(textView)

    toast.view = layout
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(android.view.Gravity.BOTTOM or android.view.Gravity.CENTER_HORIZONTAL, 0, 100)
    toast.show()
}

@Composable
fun TestToastExample() {
    val context = LocalContext.current

    // Example usage
    androidx.compose.material3.Button(onClick = {
        showCustomToast(context, "Phone number is valid!", true)
    }) {
        androidx.compose.material3.Text("Show Success Toast")
    }

    androidx.compose.material3.Button(onClick = {
        showCustomToast(context, "Invalid phone number!", false)
    }) {
        androidx.compose.material3.Text("Show Error Toast")
    }
}
