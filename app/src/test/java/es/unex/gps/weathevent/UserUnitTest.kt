package es.unex.gps.weathevent

import es.unex.gps.weathevent.Model.User
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals
import org.junit.Test
class UserUnitTest {

    private fun validarNombre(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun validarUsuarioOContrasena(username: String): Boolean {
        return if (username.isEmpty()) {
            false
        } else !username.contains(" ")
    }

    private fun validarEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    @Test
    fun testUser() {
        // Crear una instancia de User
        val user = User(1L, "John Doe", "john.doe", "a@a.es", "password123")

        // Verificar si los atributos son correctos
        assertEquals(1L, user.userId)
        assertEquals("John Doe", user.name)
        assertTrue(validarNombre(user.name))
        assertEquals("john.doe", user.username)
        assertTrue(validarUsuarioOContrasena(user.username))
        assertEquals("a@a.es", user.email)
        assertTrue(validarEmail(user.email))
        assertEquals("password123", user.password)
        assertTrue(validarUsuarioOContrasena(user.password))
    }
}