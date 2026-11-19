package com.duoc.app.ui.mainmenu

import android.graphics.BitmapFactory
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SyncLock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.duoc.app.data.repository.AvatarManager
import com.duoc.app.data.repository.UsuarioLocalRepository
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigateToNotes: () -> Unit,
    onNavigateToMaterial: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToLegalDocuments: () -> Unit,
    onNavigateToHistoryChanges: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit = {}
) {
    val esquema = MaterialTheme.colorScheme
    val AzulPrincipal = if (esquema.background == Color(0xFF121212)) Color(0xFF303030) else Color(0xFF205C95)
    val AzulOscuro = esquema.onBackground
    val FondoCeleste = esquema.background
    val AzulTarjetas = esquema.surfaceVariant
    val colorTarjeta = esquema.surface
    val colorSecundario = esquema.onSurfaceVariant

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val avatarManager = remember(context) { AvatarManager(context) }
    val usuarioRepository = remember(context) { UsuarioLocalRepository(context) }

    val nombre = remember(context) {
        usuarioRepository.obtenerNombre()
    }

    val primerNombre = nombre.trim().substringBefore(" ").ifBlank {
        "Usuario"
    }

    var busqueda by remember { mutableStateOf("") }
    var avatarVersion by remember { mutableIntStateOf(0) }
    var mostrarCodigos by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(drawerState.isOpen || mostrarCodigos) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            mostrarCodigos = false
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                avatarVersion++
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val avatarBitmap = remember(avatarVersion) {
        avatarManager.obtenerAvatar()?.let { uri ->
            try {
                context.contentResolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it)?.asImageBitmap()
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    val modulos = listOf(
        "Código Civil",
        "Código Penal",
        "Contenidos",
        "Aclaraciones",
        "Actividades"
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = colorTarjeta,
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AzulPrincipal)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    onNavigateToProfile()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (avatarBitmap != null) {
                                Image(
                                    bitmap = avatarBitmap,
                                    contentDescription = "Mi perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "Mi perfil",
                                    modifier = Modifier.size(60.dp),
                                    tint = AzulPrincipal
                                )
                            }
                        }

                        Text(
                            text = nombre.ifBlank { "Mi cuenta" },
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "AppLegislator",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 13.sp
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    NavigationDrawerItem(
                        label = { Text("Inicio") },
                        selected = !mostrarCodigos,
                        icon = { Icon(Icons.Default.Home, null) },
                        onClick = {
                            mostrarCodigos = false
                            scope.launch { drawerState.close() }
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Mis notas de estudio") },
                        selected = false,
                        icon = { Icon(Icons.Default.Edit, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToNotes()
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Material complementario") },
                        selected = false,
                        icon = { Icon(Icons.Default.Book, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToMaterial()
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Artículos legales") },
                        selected = false,
                        icon = { Icon(Icons.Default.Description, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToLegalDocuments()
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Cambios legislativos") },
                        selected = false,
                        icon = { Icon(Icons.Default.SyncLock, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToHistoryChanges()
                        }
                    )

                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

                    NavigationDrawerItem(
                        label = { Text("Mi perfil") },
                        selected = false,
                        icon = { Icon(Icons.Default.Person, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToProfile()
                        }
                    )


                    NavigationDrawerItem(
                        label = {
                            Text("Configuración")
                        },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToSettings()
                        }
                    )


                    NavigationDrawerItem(
                        label = { Text("Cerrar sesión") },
                        selected = false,
                        icon = {
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, null)
                        },
                        onClick = {
                            scope.launch { drawerState.close() }
                            onLogout()
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            containerColor = FondoCeleste,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },
                    title = {
                        Text(
                            "AppLegislator",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = onNavigateToNotifications) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notificaciones",
                                tint = Color.White
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { onNavigateToProfile() },
                            contentAlignment = Alignment.Center
                        ) {
                            if (avatarBitmap != null) {
                                Image(
                                    bitmap = avatarBitmap,
                                    contentDescription = "Ir a mi perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "Ir a mi perfil",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AzulPrincipal
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = colorTarjeta
                ) {
                    NavigationBarItem(
                        selected = !mostrarCodigos,
                        onClick = { mostrarCodigos = false },
                        icon = { Icon(Icons.Default.Home, null) },
                        label = { Text("Inicio") }
                    )

                    NavigationBarItem(
                        selected = mostrarCodigos,
                        onClick = { mostrarCodigos = true },
                        icon = { Icon(Icons.Default.Book, null) },
                        label = { Text("Códigos") }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToNotes,
                        icon = { Icon(Icons.Default.Edit, null) },
                        label = { Text("Notas") }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToNotifications,
                        icon = { Icon(Icons.Default.Notifications, null) },
                        label = { Text("Avisos") }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToProfile,
                        icon = { Icon(Icons.Default.Person, null) },
                        label = { Text("Perfil") }
                    )
                }
            }
        ) { innerPadding ->

            if (mostrarCodigos) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Códigos y contenidos",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro
                    )

                    Text(
                        "Selecciona una sección para consultar.",
                        color = AzulOscuro
                    )

                    modulos.forEach { modulo ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorTarjeta
                            )
                        ) {
                            Text(
                                text = modulo,
                                modifier = Modifier.padding(20.dp),
                                fontWeight = FontWeight.Medium,
                                color = AzulOscuro
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = AzulTarjetas
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Text(
                                    "¡Hola, $primerNombre! 👋",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AzulOscuro
                                )

                                Spacer(Modifier.height(6.dp))

                                Text(
                                    "Bienvenido de nuevo a AppLegislator.",
                                    fontSize = 14.sp,
                                    color = AzulOscuro
                                )
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = busqueda,
                            onValueChange = { busqueda = it },
                            label = { Text("Buscar leyes o artículos") },
                            placeholder = { Text("Escribe aquí...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AzulPrincipal,
                                unfocusedContainerColor = colorTarjeta,
                                focusedContainerColor = colorTarjeta
                            )
                        )
                    }

                    item {
                        Text(
                            "Explorar contenidos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                    }

                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            modulos.chunked(3).forEach { fila ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    fila.forEach { modulo ->
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(110.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = colorTarjeta
                                            ),
                                            elevation = CardDefaults.cardElevation(2.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(8.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = when (modulo) {
                                                        "Código Civil" -> "⚖"
                                                        "Código Penal" -> "▤"
                                                        "Contenidos" -> "▣"
                                                        "Aclaraciones" -> "?"
                                                        else -> "✓"
                                                    },
                                                    fontSize = 30.sp,
                                                    color = AzulPrincipal
                                                )

                                                Spacer(Modifier.height(8.dp))

                                                Text(
                                                    text = modulo,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    color = AzulOscuro
                                                )
                                            }
                                        }
                                    }

                                    repeat(3 - fila.size) {
                                        Spacer(Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            "Accesos rápidos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                    }

                    item {
                        MenuOptionCard(
                            title = "Mis Notas de Estudio",
                            subtitle = "Crea y guarda tus apuntes",
                            icon = Icons.Default.Edit,
                            onClick = onNavigateToNotes
                        )
                    }

                    item {
                        MenuOptionCard(
                            title = "Material Complementario",
                            subtitle = "Consulta jurisprudencia y doctrina",
                            icon = Icons.Default.Book,
                            onClick = onNavigateToMaterial
                        )
                    }

                    item {
                        MenuOptionCard(
                            title = "Artículos Legales",
                            subtitle = "Buscar en el archivo legal",
                            icon = Icons.Default.Description,
                            onClick = onNavigateToLegalDocuments
                        )
                    }

                    item {
                        MenuOptionCard(
                            title = "Cambios Legislativos",
                            subtitle = "Historial de cambios legislativos",
                            icon = Icons.Default.SyncLock,
                            onClick = onNavigateToHistoryChanges
                        )
                    }

                    item {
                        Text(
                            "Alertas de reformas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = colorTarjeta
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    "Actualizaciones legales",
                                    fontWeight = FontWeight.Bold,
                                    color = AzulOscuro
                                )

                                Spacer(Modifier.height(6.dp))

                                Text(
                                    "Aquí aparecerán las novedades y reformas de la legislación chilena.",
                                    fontSize = 13.sp
                                )

                                Spacer(Modifier.height(6.dp))

                                Text(
                                    "Sección informativa de ejemplo",
                                    fontSize = 11.sp,
                                    color = colorSecundario
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val esquema = MaterialTheme.colorScheme
    val AzulPrincipal = esquema.primary
    val AzulOscuro = esquema.onSurface
    val colorTarjeta = esquema.surface
    val colorSecundario = esquema.onSurfaceVariant

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorTarjeta
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AzulPrincipal,
                modifier = Modifier.size(36.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = colorSecundario
                )
            }
        }
    }
}
