package org.delcom.pam_p4_ifs23051.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.delcom.pam_p4_ifs23051.helper.ConstHelper
import org.delcom.pam_p4_ifs23051.helper.RouteHelper
import org.delcom.pam_p4_ifs23051.ui.theme.DelcomTheme

sealed class MenuBottomNav(
    val route      : String,
    val title      : String,
    val icon       : ImageVector,
    val iconActive : ImageVector,
) {
    object Home : MenuBottomNav(
        ConstHelper.RouteNames.Home.path,
        "Beranda",
        Icons.Outlined.Home,
        Icons.Filled.Home,
    )
    object Plants : MenuBottomNav(
        ConstHelper.RouteNames.Plants.path,
        "Tanaman",
        Icons.Outlined.Nature,
        Icons.Filled.Nature,
    )
    object FlowerLanguage : MenuBottomNav(
        ConstHelper.RouteNames.FlowerLanguage.path,
        "Bahasa Bunga",
        Icons.Outlined.LocalFlorist,
        Icons.Filled.LocalFlorist,
    )
    object Profile : MenuBottomNav(
        ConstHelper.RouteNames.Profile.path,
        "Profil",
        Icons.Outlined.Person,
        Icons.Filled.Person,
    )
}

@Composable
fun BottomNavComponent(navController: NavHostController) {
    val items = listOf(
        MenuBottomNav.Home,
        MenuBottomNav.Plants,
        MenuBottomNav.FlowerLanguage,
        MenuBottomNav.Profile,
    )
    val currentRoute = navController.currentDestination?.route

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation  = 8.dp,
                shape      = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                spotColor  = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
            ),
        shape          = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        tonalElevation = 2.dp,
        color          = MaterialTheme.colorScheme.surface,
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier       = Modifier.height(80.dp),
            tonalElevation = 0.dp,
        ) {
            items.forEach { screen ->
                val selected = currentRoute?.startsWith(screen.route) == true
                val iconSize by animateDpAsState(
                    targetValue = if (selected) 26.dp else 22.dp,
                    label       = "iconSize",
                )

                NavigationBarItem(
                    selected = selected,
                    onClick  = { RouteHelper.to(navController, screen.route, true) },
                    icon     = {
                        NavigationIcon(selected = selected, screen = screen, iconSize = iconSize)
                    },
                    label = {
                        Text(
                            text  = screen.title,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor   = MaterialTheme.colorScheme.primary,
                        selectedTextColor   = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor      = Color.Transparent,
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationIcon(
    selected : Boolean,
    screen   : MenuBottomNav,
    iconSize : androidx.compose.ui.unit.Dp = 24.dp,
) {
    Box(
        modifier        = Modifier.size(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                    .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape),
            )
        }
        Icon(
            imageVector     = if (selected) screen.iconActive else screen.icon,
            contentDescription = screen.title,
            modifier        = Modifier.size(iconSize),
            tint            = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavComponent() {
    DelcomTheme {
        BottomNavComponent(navController = NavHostController(LocalContext.current))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBottomNavDark() {
    DelcomTheme(darkTheme = true) {
        BottomNavComponent(navController = NavHostController(LocalContext.current))
    }
}