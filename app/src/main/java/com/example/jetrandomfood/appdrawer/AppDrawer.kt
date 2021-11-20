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

import androidx.compose.ui.unit.dp

import com.example.jetrandomfood.ui.component.ListFoodsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.jetrandomfood.R
import com.example.jetrandomfood.routing.JetFoodRouter
import com.example.jetrandomfood.routing.Screen

import com.example.jetrandomfood.ui.component.ListRanDomFoods
import com.example.jetrandomfood.viewmodel.MainViewModel


@ExperimentalMaterialApi
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

    // check button random pressed yet
    val checkClick =  remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(scaffoldState = scaffoldState, scope = scope)
        },
        content = {
            // check if the random button has been pressed. If clicked, then set state to false otherwise true and display the list after random
            if(!checkClick.value){
                ListFoodsItem()
            }else{
                ListRanDomFoods()
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if(!checkClick.value){
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
                        checkClick.value=true
                    },
                    shape = fabShape,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    backgroundColor = colors
                )
            } else{
                ExtendedFloatingActionButton(
                    icon = {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back home page",
                        )
                    },
                    text = {
                        Text("Back Home", color = Color.White)
                    },
                    onClick = {
                        viewModel.onClickBackHome()
                        checkClick.value=false
                    },
                    shape = fabShape,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    backgroundColor = colors
                )
            }

        },
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Home,
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
                        Icons.Default.AccountCircle,
                        tint = Color.Black,
                        contentDescription = stringResource(R.string.app_name),
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
        title = { Text(text = stringResource(R.string.app_name), color = Color.Black) },
        actions = {
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
                    if (liked.value) Color.Red
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

                }
            }
        },
        backgroundColor = Color(0x6200EE)
//        backgroundColor = Color(0xFDCD7F32),
//        backgroundColor = colorResource(id = R.color.design_default_color_primary)
    )
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
            text = "Random Foods App",
            modifier = Modifier.align(Alignment.CenterVertically)

        )
    }
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
fun AppDrawer(
    currentScreen: Screen,
    closeDrawerAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFDCD7F32))
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
    }
}
