package pokitmons.pokit.core.feature.utils

import android.content.Context
import android.content.Intent

fun shareUrlLink(context: Context, url: String, chooserTitle: String = "Pokit") {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    context.startActivity(Intent.createChooser(intent, chooserTitle))
}
