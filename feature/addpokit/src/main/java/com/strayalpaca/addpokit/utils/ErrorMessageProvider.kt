package com.strayalpaca.addpokit.utils

import android.content.Context
import com.strayalpaca.addpokit.R
import dagger.hilt.android.qualifiers.ApplicationContext
import pokitmons.pokit.domain.model.pokit.PokitErrorCode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMessageProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun errorCodeToMessage(errorCode: String): String {
        return when (errorCode) {
            PokitErrorCode.ALREADY_USED_POKIT_NAME -> {
                context.getString(R.string.already_used_pokit_name)
            }
            PokitErrorCode.CANNOT_FOUND_POKIT_INFO -> {
                context.getString(R.string.cannot_found_pokit_info)
            }
            PokitErrorCode.UNAVAILABLE_POKIT -> {
                context.getString(R.string.unavailable_pokit)
            }
            PokitErrorCode.CANNOT_FOUND_POKIT_IMAGE -> {
                context.getString(R.string.cannot_found_pokit_image)
            }
            PokitErrorCode.TOO_MUCH_POKIT -> {
                context.getString(R.string.too_much_pokit)
            }
            else -> {
                context.getString(R.string.uncategorized_error)
            }
        }
    }

    fun getTextLengthErrorMessage(): String {
        return context.getString(R.string.text_length_limit_format)
    }
}
