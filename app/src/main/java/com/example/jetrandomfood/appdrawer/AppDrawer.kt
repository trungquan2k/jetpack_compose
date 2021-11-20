package com.example.jetrandomfood.appdrawer


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.jetrandomfood.ui.screen.ListFoodsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.jetrandomfood.R
import com.example.jetrandomfood.routing.JetFoodRouter
import com.example.jetrandomfood.routing.Screen
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetrandomfood.viewmodel.MainViewModel


@Composable
fun HomePageScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    //click button
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val colors = if (isPressed) Color.Blue else Color.Red

    // shape center
    val fabShape = RoundedCornerShape(40)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(scaffoldState = scaffoldState, scope = scope)
        },
        content = {
            ListFoodsItem()
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "get random",
                    )
                },
                text = {
                    Text("Random", color = Color.White)
                },
                onClick = {
                    viewModel.onClickRanDomFood()
                },
                shape = fabShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                backgroundColor = colors
            )
        },
        drawerContent = {
            AppDrawer(currentScreen = Screen.Home,
                closeDrawerAction = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },

        bottomBar = {
            BottomAppBar(cutoutShape = fabShape,backgroundColor = Color(0xFDCD7F32)) {}

        },
    )
}


@Composable
fun TopAppBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    val drawerState = scaffoldState.drawerState
    val result = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val liked = remember { mutableStateOf(true) }
    TopAppBar(
        navigationIcon = {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Menu,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.app_name)
                    )
                },
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed) drawerState.open()
                        else drawerState.close()
                    }
                }
            )
        },
        title = { Text(text = stringResource(R.string.app_name), color = Color.White) },
        actions = {
            IconButton(onClick = {
                result.value = " Play icon clicked"
            }) {
                Icon(Icons.Filled.CheckCircle, contentDescription = "")
            }

            IconToggleButton(
                checked = liked.value,
                onCheckedChange = {
                    liked.value = it
                    if (liked.value) {
                        result.value = "Liked"
                    } else {
                        result.value = "Unliked"
                    }
                }
            ) {
                val tint by animateColorAsState(
                    if (liked.value) Color(0xFF7BB661)
                    else Color.LightGray
                )
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    tint = tint
                )
            }

            Box(
                Modifier
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = {
                    expanded.value = true
                    result.value = "More icon clicked"
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                ) {
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        result.value = "First item clicked"
                    }) {
                        Text("First Item")
                    }

                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        result.value = "Second item clicked"
                    }) {
                        Text("Second item")
                    }

                    Divider()

                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        result.value = "Third item clicked"
                    }) {
                        Text("Third item")
                    }

                    Divider()

                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        result.value = "Fourth item clicked"
                    }) {
                        Text("Fourth item")
                    }
                }
            }
        },
        backgroundColor = Color(0xFDCD7F32),
//        backgroundColor = colorResource(id = R.color.design_default_color_primary)
    )
}

@Composable
private fun ScreenNavigationButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }
    val textColor = if (isSelected) {
        colors.onSecondary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }

    val backgroundColor = if (isSelected) {
        colors.onSecondary.copy(alpha = 0.12f)
    } else {
        colors.surface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Image(
                imageVector = icon,
                contentDescription = "Screen Navigation Button",
                colorFilter = ColorFilter.tint(textColor),
                alpha = imageAlpha
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                color = textColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun AppDrawerHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.miquang),
            contentDescription = "Drawer Food",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clip(RoundedCornerShape(10))
                .border(1.dp, Color.Gray, RoundedCornerShape(10))
        )
        Text(
            text = "Ramdom Foods App",
            modifier = Modifier.align(Alignment.CenterVertically)

        )
    }
}

@Composable
fun AppDrawer(
    currentScreen: Screen,
    closeDrawerAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {
        AppDrawerHeader()
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        ScreenNavigationButton(
            icon = Icons.Filled.Home,
            label = "Home",
            isSelected = currentScreen === Screen.Home,
            onClick = {
                JetFoodRouter.navigateTo(Screen.Home)
                closeDrawerAction()
            }
        )
        ScreenNavigationButton(
            icon = Icons.Filled.Notifications,
            label = "Notification",
            isSelected = currentScreen === Screen.RanDomFood,
            onClick = {
                closeDrawerAction()
            }
        )
        AppDrawerFooter()
    }
}

@Composable
private fun AppDrawerFooter(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {
        val colors = MaterialTheme.colors
        val (settingsImage, settingsText, darkModeButton) = createRefs()
        Image(
            modifier = modifier.constrainAs(settingsImage) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(id = R.string.settings),
            colorFilter = ColorFilter.tint(colors.primaryVariant)
        )
        Text(
            fontSize = 10.sp,
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.body2,
            color = colors.primaryVariant,
            modifier = modifier
                .padding(start = 16.dp)
                .constrainAs(settingsText) {
                    start.linkTo(settingsImage.end)
                    centerVerticallyTo(settingsImage)
                }
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_moon),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = modifier
                .clickable(onClick = { })
                .constrainAs(darkModeButton) {
                    end.linkTo(parent.end)
                    bottom.linkTo(settingsImage.bottom)
                },
            colorFilter = ColorFilter.tint(colors.primaryVariant)
        )
    }
}