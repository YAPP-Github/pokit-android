package com.strayalpaca.addlink.model

data class Link(
    val url: String,
    val title: String,
    val imageUrl: String?,
)

internal val sampleLink = Link(url = "https://pokit.com/watch?v=xSTwqkUyM8k", title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법", imageUrl = null)
