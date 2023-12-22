package es.unex.gps.weathevent.api

import es.unex.gps.weathevent.data.api.Municipio
import es.unex.gps.weathevent.data.api.MunicipioResponse
import es.unex.gps.weathevent.data.api.MunicipiosResponse
import es.unex.gps.weathevent.data.api.ProvinciasResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApiServiceTest {

    // Mock del servicio de ElTiempoAPI
    @Mock
    private lateinit var apiService: ElTiempoAPI


    @Test
    fun testGetProvincias() = runBlocking {

        val respuestaEsperada = ProvinciasResponse()
        respuestaEsperada.title = "Test de provincias"

        `when`(apiService.getProvincias()).thenReturn(respuestaEsperada)

        val resultado = apiService.getProvincias()

        Mockito.verify(apiService).getProvincias()

        assertEquals(resultado.title,"Test de provincias")

    }

    @Test
    fun testGetMunicipios() = runBlocking {

        val respuestaEsperada = MunicipiosResponse()
        respuestaEsperada.provincia = "Badajoz"

        `when`(apiService.getMunicipios(Mockito.anyString())).thenReturn(respuestaEsperada)

        val resultado = apiService.getMunicipios(Mockito.anyString())

        Mockito.verify(apiService).getMunicipios(Mockito.anyString())

        assertEquals(resultado.provincia,"Badajoz")

    }

    @Test
    fun testGetMunicipio() = runBlocking {

        val municipioTest = Municipio()
        municipioTest.NOMBRE = "Badajoz"
        val respuestaEsperada = MunicipioResponse()
        respuestaEsperada.municipio = municipioTest

        `when`(apiService.getMunicipio(Mockito.anyString(), Mockito.anyString())).thenReturn(respuestaEsperada)

        val resultado = apiService.getMunicipio(Mockito.anyString(), Mockito.anyString())

        Mockito.verify(apiService).getMunicipio(Mockito.anyString(), Mockito.anyString())

        assertEquals(resultado.municipio?.NOMBRE,"Badajoz")

    }
}
