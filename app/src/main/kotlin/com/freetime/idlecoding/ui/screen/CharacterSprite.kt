package com.freetime.idlecoding.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

private val SMALL_RACES = setOf("halfling", "gnome", "dwarf")

/**
 * Compatibility loader used by the house renderer.
 *
 * Character art is generated in memory instead of being loaded from PNG files. Non-body
 * layers are transparent, while the body layer contains a small coding-themed marker.
 */
internal fun loadLayer(context: Context, path: String): ImageBitmap? {
    if (!path.startsWith("generated/body/")) return null

    val bitmap = Bitmap.createBitmap(
        CharacterLayerPaths.FRAME_W,
        CharacterLayerPaths.FRAME_H,
        Bitmap.Config.ARGB_8888,
    )
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 18f
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }
    canvas.drawText("</>", CharacterLayerPaths.FRAME_W / 2f, 24f, paint)
    return bitmap.asImageBitmap()
}

/** Generated character-layer keys retained for the house renderer API. */
internal data class CharacterLayerPaths(
    val body: String,
    val eyes: String,
    val head: String?,
    val beard: String?,
    val action: String,
    val smallRace: Boolean,
) {
    companion object {
        const val FRAME_W = 64
        const val FRAME_H = 36
    }
}

internal fun characterLayerPaths(
    race: String,
    skinTone: Int,
    hairStyle: Int,
    hairColor: String,
    eyeStyle: Int,
    beardStyle: Int,
    beardColor: String,
): CharacterLayerPaths {
    val raceKey = race.lowercase()
    return CharacterLayerPaths(
        body = "generated/body/$raceKey/$skinTone",
        eyes = "generated/eyes/$eyeStyle",
        head = if (hairStyle == 0) null else "generated/hair/$hairStyle/$hairColor",
        beard = if (beardStyle == 0) null else "generated/beard/$beardStyle/$beardColor",
        action = "generated/action/default",
        smallRace = raceKey in SMALL_RACES,
    )
}

/**
 * Image-free character representation. Appearance settings are still accepted so save data
 * stays compatible, but the UI no longer requires character sprite files in assets.
 */
@Composable
fun CharacterSprite(
    race: String,
    skinTone: Int,
    hairStyle: Int,
    hairColor: String,
    eyeStyle: Int,
    beardStyle: Int,
    beardColor: String,
    modifier: Modifier = Modifier,
) {
    val raceSymbol = when (race.lowercase()) {
        "elf" -> "⌘"
        "orc" -> "#"
        "gnome" -> "{}"
        "dwarf" -> "$_"
        "halfling" -> "<>"
        else -> "</>"
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = raceSymbol,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/** Valid skin tone range for a given race; retained for existing save/customization data. */
fun skinToneRange(race: String): IntRange = when (race.lowercase()) {
    "elf", "gnome" -> 1..6
    "orc" -> 6..9
    else -> 1..4
}

val HAIR_COLORS = ('a'..'k').map { it.toString() }
val BEARD_COLORS = HAIR_COLORS
