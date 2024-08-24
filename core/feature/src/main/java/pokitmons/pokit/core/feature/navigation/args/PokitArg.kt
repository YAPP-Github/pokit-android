package pokitmons.pokit.core.feature.navigation.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokitArg(
    val id: Int,
    val imageId: Int,
    val imageUrl: String,
    val title: String,
) : Parcelable
