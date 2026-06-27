package com.example.seapedia.global.utils

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.example.seapedia.ui.theme.error


interface BaseSupportingText {
    @Composable fun SupportText(text: String,style: TextStyle = Typography.error)
    fun validate(text:String) : Boolean

}
object EmailSupportingText : BaseSupportingText{
    @Composable
    override fun SupportText(text: String,style: TextStyle) {
        if (text.isBlank()){
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            Text("Email must a valid email address",style = style)
        }
    }

    override fun validate(text: String): Boolean {
        return text.isNotBlank()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(text.trim()).matches()
    }

}
object PasswordSupportingText : BaseSupportingText{
    @Composable
    override fun SupportText(text: String,style: TextStyle) {
        if (text.isBlank()){
            return
        }
        if (text.length < 8)
            Text("Password must be 8 char or more",style = style)
//        if (!text.any { it.isLowerCase() }){
//            Text("Password must atleast have one lowercase",style = style)
//        }
    }

    override fun validate(text: String): Boolean {
        return text.isNotBlank()
                && text.length >= 8
//                && text.any{it.isLowerCase()}
    }
}

object NormalSupportingText : BaseSupportingText{
    @Composable
    override fun SupportText(text: String, style: TextStyle) {
        if (text.isBlank())
            Text("Cannot be blank",style = style)
    }

    @Composable
    fun SupportText(
        text: String,
        title: String,
        style: TextStyle = Typography.error
    ) {
        if (text.isBlank())
            Text("$title Cannot be blank",style = style)
    }

    override fun validate(text: String): Boolean {
        return text.isNotBlank()
    }
}

object NumberSupportingText : BaseSupportingText{
    @Composable
    override fun SupportText(text: String, style: TextStyle) {
        if (text.isBlank())
            Text("Cannot be blank",style = style)
        if (text.toInt() < 0)
            Text("Cannot be lower than 0",style = style)
    }

    override fun validate(text: String): Boolean {
        return text.isNotBlank() && text.toInt() > 0
    }

}

object RupiahSupportingText : BaseSupportingText{
    @Composable
    override fun SupportText(text: String, style: TextStyle) {
        val value = text
            .removePrefix("Rp ")
            .replace(".", "")
            .trim()
        if(value.isBlank())
            Text("Cannot be blank",style = style)
        val price = value.toIntOrNull() ?: 0
        if (price < 0)
            Text("Cannot be lower than 0",style = style)
    }

    override fun validate(text: String): Boolean {
        val value = text
            .removePrefix("Rp ")
            .replace(".", "")
            .trim()

        return value.isNotBlank() && value.toInt() > 0
    }

}
object PhoneNumberSupportingText : BaseSupportingText{
    private val phoneRegex = Regex("^\\+62\\d{8,13}$")

    @Composable
    override fun SupportText(text: String, style: TextStyle) {
        if (text.isBlank()) {
            Text(
                text = "Phone number cannot be blank",
                style = style
            )
        } else if (!validate(text)) {
            Text(
                text = "Phone number must be in format +628xxxxxxxxxx",
                style = style
            )
        }
    }

    override fun validate(text: String): Boolean {
        return phoneRegex.matches(text)
    }
}