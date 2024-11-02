package pokitmons.pokit.core.ui.components.block.pokittoast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.pokittoast.attributes.PokitToastType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PokitToast(
                modifier = Modifier.padding(20.dp),
                text = "최대 30개의 포킷을 생성할 수 있습니다.\n포킷을 삭제한 뒤에 추가해주세요.",
                onClick = {}
            )
            PokitToast(
                modifier = Modifier.padding(20.dp),
                text = "링크 저장 완료",
                onClick = {},
                type = PokitToastType.Success
            )
            PokitToast(
                modifier = Modifier.padding(20.dp),
                text = "링크 저장 실패",
                onClick = {},
                type = PokitToastType.Error
            )
            PokitToast(
                modifier = Modifier.padding(20.dp),
                text = "저장 공간 부족",
                onClick = {},
                type = PokitToastType.Warning
            )
        }
    }
}
