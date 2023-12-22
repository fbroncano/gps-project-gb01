package es.unex.gps.weathevent

import es.unex.gps.weathevent.model.Ciudad
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CiudadUnitTest {

    @Test
    fun testCiudad() {
        // Crear una instancia de Ciudad
        val ciudad = Ciudad(1L, "Ciudad Ejemplo", true)

        // Verificar si los atributos son correctos
        assertEquals(1L, ciudad.ciudadId)
        assertEquals("Ciudad Ejemplo", ciudad.name)
        assertTrue(ciudad.isFavorite)
    }
}