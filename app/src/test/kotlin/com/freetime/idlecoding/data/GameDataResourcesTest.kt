package com.freetime.idlecoding.data

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.freetime.idlecoding.repository.GameDataRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], application = Application::class)
class GameDataResourcesTest {
    @Test
    fun `all asset display references resolve without changing game data`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = GameDataRepository(context, Json { ignoreUnknownKeys = true })
        val paths = assetFiles(context, "data").filter { it.endsWith(".json") }
        assertTrue("Game assets must be included in the test", paths.isNotEmpty())
        for (path in paths) {
            val original = context.assets.open(path).bufferedReader().use { Json.parseToJsonElement(it.readText()) }
            val resolved = Json.parseToJsonElement(repository.loadAsset(path))
            assertResolved(context, path, original, resolved)
        }
    }

    private fun assetFiles(context: Context, path: String): List<String> {
        val children = context.assets.list(path).orEmpty()
        return if (children.isEmpty()) listOf(path)
        else children.flatMap { assetFiles(context, "$path/$it") }
    }

    private fun assertResolved(context: Context, path: String, original: JsonElement, resolved: JsonElement) {
        when (original) {
            is JsonObject -> {
                assertTrue(path, resolved is JsonObject)
                resolved as JsonObject
                assertEquals(path, original.keys, resolved.keys)
                original.forEach { (key, value) -> assertResolved(context, "$path.$key", value, resolved.getValue(key)) }
            }
            is JsonArray -> {
                assertTrue(path, resolved is JsonArray)
                resolved as JsonArray
                assertEquals(path, original.size, resolved.size)
                original.forEachIndexed { index, value -> assertResolved(context, "$path[$index]", value, resolved[index]) }
            }
            is JsonPrimitive -> {
                if (original.isString && original.content.startsWith("@string/")) {
                    val name = original.content.removePrefix("@string/")
                    val id = context.resources.getIdentifier(name, "string", context.packageName)
                    assertTrue("$path: missing $name", id != 0)
                    assertEquals(path, JsonPrimitive(context.getString(id)), resolved)
                    assertFalse(path, (resolved as JsonPrimitive).content.startsWith("@string/"))
                } else {
                    assertEquals("$path: game data changed", original, resolved)
                }
            }
        }
    }
}
