
package com.duoc.app

import com.duoc.app.utils.Validaciones
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class RegistroTest {

    @Test
    fun registroConContrasenaSegura() {
        assertTrue(Validaciones.contrasenaValida("Duoc2026!"))
    }

    @Test
    fun registroConContrasenaInsuficiente() {
        assertFalse(Validaciones.contrasenaValida("abc12"))
    }

    @Test
    fun registroSinContrasena() {
        assertFalse(Validaciones.contrasenaValida(""))
    }
}
