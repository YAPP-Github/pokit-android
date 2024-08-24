package pokitmons.pokit.core.feature.navigation.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkArg(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val createdAt: String,
    val domain: String,
) : Parcelable
