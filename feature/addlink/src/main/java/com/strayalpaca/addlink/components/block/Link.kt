package com.strayalpaca.addlink.components.block

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.addlink.R
import com.strayalpaca.addlink.model.Link
import pokitmons.pokit.core.ui.components.block.linkurlcard.LinkUrlCard

@Composable
internal fun LoadingLink() {
    LinkUrlCard(
        thumbnailPainter = rememberAsyncImagePainter(model = null),
        url = "",
        title = "",
        openWebBrowserByClick = false,
        isLoading = true
    )
}

@Composable
internal fun Link(
    link: Link,
    title: String?,
    modifier: Modifier = Modifier,
    openWebBrowserByClick: Boolean = true,
) {
    val placeHolder = stringResource(id = R.string.placeholder_title)
    val linkTitle = remember(link, title) { title ?: link.title.ifEmpty { placeHolder } }

    LinkUrlCard(
        modifier = modifier,
        thumbnailPainter = rememberAsyncImagePainter(model = link.imageUrl),
        url = link.url,
        title = linkTitle,
        openWebBrowserByClick = openWebBrowserByClick
    )
}
