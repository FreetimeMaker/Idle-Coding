package com.freetime.idlecoding.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Lightweight boss marker that does not depend on bitmap or drawable assets.
 *
 * Bosses are represented by their configured emoji. This keeps the UI working even when
 * no sprite files are bundled with the app and avoids loading images from assets/sprites.
 */
@Composable
fun BossIcon(
    bossId: String,
    modifier: Modifier = Modifier,
    silhouette: Boolean = false,
    fallbackEmoji: String? = null,
) {
    val symbol = fallbackEmoji ?: "👾"
    val color = if (silhouette) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.titleLarge,
            color = color,
        )
    }
}
