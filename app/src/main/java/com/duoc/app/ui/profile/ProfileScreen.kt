
package com.duoc.app.ui.profile

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.app.data.repository.AvatarManager
import com.duoc.app.data.repository.UsuarioLocalRepository

private val AzulPrincipal = Color(0xFF205C95)
private val AzulOscuro = Color(0xFF174666)
private val FondoCeleste = Color(0xFFEDF5FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val avatarManager = remember(context) {
        AvatarManager(context)
    }

    val usuarioRepository = remember(context) {
        UsuarioLocalRepository(context)
    }

    val nombre = remember {
        usuarioRepository.obtenerNombre()
    }

    val correo = remember {
        usuarioRepository.obtenerCorreo()
    }

    var avatarUri by remember {
        mutableStateOf(avatarManager.obtenerAvatar())
    }

    var avatarVersion by remember {
        mutableIntStateOf(0)
    }

    val avatarBitmap = remember(avatarUri, avatarVersion) {
        avatarUri?.let { uri ->
            try {
                context.contentResolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it)?.asImageBitmap()
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    val selectorImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            try {
                avatarManager.guardarAvatar(uri)
                avatarUri = avatarManager.obtenerAvatar()
                avatarVersion++

                Toast.makeText(
                    context,
                    "Foto de perfil actualizada",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "No se pudo guardar la foto",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        containerColor = FondoCeleste,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi perfil",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulPrincipal
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AzulPrincipal
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        if (avatarBitmap != null) {
                            Image(
                                bitmap = avatarBitmap,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Avatar predeterminado",
                                modifier = Modifier.size(115.dp),
                                tint = AzulPrincipal
                            )
                        }
                    }

                    Text(
                        text = nombre.ifBlank { "Usuario" },
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = correo.ifBlank { "Correo no disponible" },
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick = {
                            selectorImagen.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = AzulPrincipal
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            "Cambiar foto",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                text = "Información personal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AzulOscuro
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(Modifier.width(16.dp))

                        Column {
                            Text(
                                "Nombre completo",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )

                            Text(
                                nombre.ifBlank { "No registrado" },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = AzulOscuro
                            )
                        }
                    }

                    HorizontalDivider()

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(Modifier.width(16.dp))

                        Column {
                            Text(
                                "Correo electrónico",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )

                            Text(
                                correo.ifBlank { "No registrado" },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = AzulOscuro
                            )
                        }
                    }

                    HorizontalDivider()

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(Modifier.width(16.dp))

                        Column {
                            Text(
                                "Estado de la cuenta",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )

                            Text(
                                "Sesión local activa",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = AzulOscuro
                            )
                        }
                    }
                }
            }

            Text(
                text = "Tus datos se guardan temporalmente en este dispositivo.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
