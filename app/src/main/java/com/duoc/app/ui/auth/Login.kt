
package com.duoc.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.app.R
import com.duoc.app.utils.Validaciones

@Composable
fun LoginValidadoScreen(
    modifier: Modifier = Modifier,
    volver: () -> Unit,
    ingresar: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf(false) }
    var errorContrasena by remember { mutableStateOf(false) }

    val fondo = Color(0xFFEDF5FA)
    val azul = Color(0xFF205C95)
    val azulOscuro = Color(0xFF174666)
    val rojoError = Color(0xFFB3261E)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza2),
            contentDescription = "Logo InfoDerecho",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "InfoDerecho",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.titleMedium,
            color = azulOscuro
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                errorCorreo = false
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            isError = errorCorreo,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = azulOscuro,
                unfocusedTextColor = azulOscuro,
                focusedLabelColor = azul,
                unfocusedLabelColor = Color.Gray,
                focusedBorderColor = azul,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = azul
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorCorreo) {
            Text(
                text = "Ingresa un correo electrónico válido",
                color = rojoError
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorContrasena = false
            },
            label = { Text("Contraseña") },
            singleLine = true,
            isError = errorContrasena,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = azulOscuro,
                unfocusedTextColor = azulOscuro,
                focusedLabelColor = azul,
                unfocusedLabelColor = Color.Gray,
                focusedBorderColor = azul,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = azul
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorContrasena) {
            Text(
                text = "La contraseña debe tener mínimo 6 caracteres",
                color = rojoError
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                errorCorreo = !Validaciones.correoValido(correo)
                errorContrasena =
                    !Validaciones.contrasenaValida(contrasena)

                if (!errorCorreo && !errorContrasena) {
                    ingresar()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = azul
            )
        ) {
            Text(
                text = "Iniciar sesión",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = volver,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "Volver al inicio",
                color = azul,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
