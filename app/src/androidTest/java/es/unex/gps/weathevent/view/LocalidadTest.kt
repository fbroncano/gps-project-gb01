package es.unex.gps.weathevent.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.gps.weathevent.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LocalidadTests {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun MostrarDatosClimatologiaActualDadaLocalidad() {
        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(replaceText("test registro"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(replaceText("testTiempoActual"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test123"), closeSoftKeyboard())

        val materialButton2 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.buscarFragment), withContentDescription("Búsqueda"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(15000)

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editText),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("La Coronada"), closeSoftKeyboard())

        val materialTextView = onView(
            allOf(
                withId(R.id.cityName), withText("La Coronada"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_item),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        Thread.sleep(1500)

        val textView = onView(
            withId(R.id.temperature_view)
        )
        textView.check(matches(isDisplayed()))

        val textView2 = onView(
            withId(R.id.descripcion_view)
        )
        textView2.check(matches(isDisplayed()))

    }

    @Test
    fun MostrarDetallePrediccionMeteorologicaAUnaSemanaVistaDeUnaCiudad() {
        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(replaceText("test registro"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(replaceText("testTiempoDias"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test123"), closeSoftKeyboard())

        val materialButton2 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.buscarFragment), withContentDescription("Búsqueda"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(15000)

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editText),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("La Coronada"), closeSoftKeyboard())

        val materialTextView = onView(
            allOf(
                withId(R.id.cityName), withText("La Coronada"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_item),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        Thread.sleep(3000)

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.proximosDiasFragment), withContentDescription("Próximos días"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.pronostico_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val recyclerView2 = onView(
            withId(R.id.rvProximosDias)
        )
        recyclerView2.check(matches(isDisplayed()))
    }

    fun MostrarDetallePrediccionMeteorologicaProximasHorasDeUnMunicipio() {
        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(replaceText("test registro"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(replaceText("testTiempoHoras"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test123"), closeSoftKeyboard())

        val materialButton2 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.buscarFragment), withContentDescription("Búsqueda"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(15000)

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editText),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("La Coronada"), closeSoftKeyboard())

        val materialTextView = onView(
            allOf(
                withId(R.id.cityName), withText("La Coronada"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_item),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val recyclerView = onView(
            withId(R.id.rvProximasHoras)
        )
        recyclerView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}