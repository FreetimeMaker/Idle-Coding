package com.freetime.idlecoding.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.ui.graphics.vector.ImageVector
import com.freetime.idlecoding.R
import com.freetime.idlecoding.ui.screen.CombatTabName

sealed class Screen(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
) {
    object Skills : Screen(
        route = "skills",
        labelRes = R.string.nav_skills,
        icon = Icons.AutoMirrored.Outlined.ShowChart,
        selectedIcon = Icons.AutoMirrored.Filled.ShowChart,
    ) {
        const val openSkillRoute = "skills/open/{openSkill}"
        fun routeWithSkill(skill: String) = "skills/open/$skill"
    }

    object Combat : Screen(
        route = "combat",
        labelRes = R.string.nav_combat,
        icon = Icons.Outlined.Shield,
        selectedIcon = Icons.Filled.Shield,
    ) {
        const val openTabRoute = "combat/tab/{tab}"
        fun startWithTab(tab: CombatTabName) = "combat/tab/${tab.name}"
        const val presetDungeonRoute = "combat/preset_dungeon/{dungeonKey}"
        fun presetDungeonRoute(key: String) = "combat/preset_dungeon/$key"
        const val presetBossRoute = "combat/preset_boss/{bossKey}"
        fun presetBossRoute(key: String) = "combat/preset_boss/$key"
    }

    object Home : Screen(
        route = "home",
        labelRes = R.string.nav_home,
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
    )

    object Quests : Screen(
        route = "quests",
        labelRes = R.string.nav_quests,
        icon = Icons.AutoMirrored.Outlined.MenuBook,
        selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
    )

    object Profile : Screen(
        route = "profile",
        labelRes = R.string.nav_profile,
        icon = Icons.Outlined.AccountCircle,
        selectedIcon = Icons.Filled.AccountCircle,
    )

    object Settings : Screen(
        route = "settings",
        labelRes = R.string.settings_title,
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
    ) {
        const val homeScreenRoute = "settings/home_screen"
        const val saveSlotsRoute = "settings/save_slots"
        const val themeSettingsRoute = "settings/theme"
        const val themeEditorRoute = "settings/theme_editor?source={source}&blank={blank}"
        fun themeEditorRouteWithSource(source: String, blankName: Boolean) =
            "settings/theme_editor?source=$source&blank=$blankName"
    }

    object Shop : Screen("shop", R.string.label_shop, Icons.Filled.ShoppingCart)
    object Farming : Screen("farming", R.string.skill_farming_name, Icons.Filled.ShoppingCart)
    object Inn : Screen("inn", R.string.inn_title, Icons.Filled.ShoppingCart)

    object WorkerSkills : Screen(
        route = "worker_skills?initialSlot={initialSlot}",
        labelRes = R.string.worker_skills_title_nav,
        icon = Icons.AutoMirrored.Filled.ShowChart,
    ) {
        fun routeWithSlot(slot: Int) = "worker_skills?initialSlot=$slot"
    }

    object GuildHall : Screen("guild_hall", R.string.guild_hall_title, Icons.Filled.Group)

    object PrestigeDetail : Screen(
        route = "prestige_detail/{skill}",
        labelRes = R.string.prestige_title,
        icon = Icons.Filled.Star,
    ) {
        fun createRoute(skill: String) = "prestige_detail/$skill"
    }

    object Church : Screen("church", R.string.church_title, Icons.Filled.Star)
    object Monument : Screen("monument", R.string.monument_title, Icons.Filled.AccountBalance)

    object GuildDetail : Screen(
        route = "guild_detail/{guild}",
        labelRes = R.string.guild_hall_title,
        icon = Icons.Filled.Group,
    ) {
        fun createRoute(guild: String) = "guild_detail/$guild"
    }

    object Slayer : Screen("slayer", R.string.slayer_title, Icons.Filled.Shield)
    object Builder : Screen("builder", R.string.builder_title, Icons.Filled.Star)
    object House : Screen("house", R.string.house_title, Icons.Filled.Home)
    object BoneAltar : Screen("bone_altar", R.string.bone_altar_title, Icons.Filled.Star)
    object Carnival : Screen("carnival", R.string.carnival_title, Icons.Filled.Celebration)
    object Tower : Screen("tower", R.string.tower_title, Icons.Filled.Star)
    object SeasonalEvent : Screen("seasonal_event", R.string.seasonal_event_title, Icons.Filled.Star)

    companion object {
        val bottomNavItems = listOf(Skills, Combat, Home, Quests, Profile)
    }
}
