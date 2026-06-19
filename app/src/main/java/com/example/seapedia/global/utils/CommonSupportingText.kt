package com.example.seapedia.global.utils

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
        if (!text.any { it.isLowerCase() }){
            Text("Password must atleast have one lowercase",style = style)
        }
    }

    override fun validate(text: String): Boolean {
        return text.isNotBlank()
                && text.length >= 8
                && text.any{it.isLowerCase()}
    }
}
