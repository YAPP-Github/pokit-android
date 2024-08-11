package pokitmons.pokit.home.remind

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard

@Composable
fun RemindScreen() {
    Column {
        RemindSection(title = "오늘 이 링크는 어때요?") { ToadyLinkCard() }
        RemindSection(title = "한번도 읽지 않았어요") {
            LinkCard(
                item =,
                title =,
                sub =,
                painter =,
                notRead =,
                badgeText =,
                onClickKebab =,
                onClickItem =
            )
        }
        RemindSection(title = "즐겨찾기 링크만 모았어요") {
            LinkCard(
                item =,
                title =,
                sub =,
                painter =,
                notRead =,
                badgeText =,
                onClickKebab =,
                onClickItem =
            )
        }
    }
}
