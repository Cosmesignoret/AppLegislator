
package com.duoc.app

import com.duoc.app.utils.Validaciones
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class ValidacionesTest {

    @Test
    fun contrasenaValida() {
        assertTrue(Validaciones.contrasenaValida("Clave123"))
    }

    @Test
    fun contrasenaCorta() {
        assertFalse(Validaciones.contrasenaValida("123"))
    }

    @Test
    fun contrasenaVacia() {
        assertFalse(Validaciones.contrasenaValida(""))
    }

    @Test
    fun contrasenaLongitudMinima() {
        assertTrue(Validaciones.contrasenaValida("123456"))
    }

    @Test
    fun contrasenaMenorAlMinimo() {
        assertFalse(Validaciones.contrasenaValida("12345"))
    }
}

