package es.unex.gps.weathevent.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
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
class IniciarSesionTests {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun ComprobarTodosLosMensajesDeErrorDelLogin() {
        val materialButton = onView(
            allOf(
                withId(R.id.registro), withText("Registrarse"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.nombreRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("test Inicio"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.usernameRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.emailRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.passRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.confirmarRegistro), withText("Crear cuenta"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        pressBack()

        val materialButton3 = onView(
            allOf(
                withId(R.id.inicarsesionRegistro), withText("Iniciar sesión"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.entrar), withText("Entrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.userLogin),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("vhert"), closeSoftKeyboard())

        val textView = onView(
            allOf(
                withId(R.id.errorUserLogin), withText("El usuario no puede contener espacios"),
                withParent(
                    allOf(
                        withId(R.id.fondo),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("El usuario no puede contener espacios")))

        val materialButton5 = onView(
            allOf(
                withId(R.id.entrar), withText("Entrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.errorUserLogin), withText("El usuario no existe"),
                withParent(
                    allOf(
                        withId(R.id.fondo),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("El usuario no existe")))

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.userLogin), withText("vhert"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(click())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.userLogin), withText("vhert"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText7.perform(replaceText("test"))

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.userLogin), withText("test"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText8.perform(closeSoftKeyboard())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.passLogin),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(replaceText("test234"), closeSoftKeyboard())

        val materialButton6 = onView(
            allOf(
                withId(R.id.entrar), withText("Entrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.errorPassLogin), withText("Contraseña incorrecta"),
                withParent(
                    allOf(
                        withId(R.id.fondo),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Contraseña incorrecta")))

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.passLogin), withText("test234"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText10.perform(click())

        val appCompatEditText11 = onView(
            allOf(
                withId(R.id.passLogin), withText("test234"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText11.perform(replaceText("test"))

        val appCompatEditText12 = onView(
            allOf(
                withId(R.id.passLogin), withText("test"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText12.perform(closeSoftKeyboard())

        val materialButton7 = onView(
            allOf(
                withId(R.id.entrar), withText("Entrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
    }

    @Test
    fun IniciarSesionDeManeraCorrectaDeUnUsuarioRegistrado() {
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
        appCompatEditText2.perform(replaceText("testInicio"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton2 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))

        pressBack()

        val materialButton3 = onView(
            allOf(
                withId(R.id.inicarsesionRegistro), withText("Iniciar sesión"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.userLogin),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("testInicio"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.passLogin),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.entrar), withText("Entrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.fondo),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val frameLayout2 = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout2.check(matches(isDisplayed()))
    }

    @Test
    fun ComprobacionDelFuncionamientoDelBotonQueLlevaARegistro() {
        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val button = onView(
            withId(R.id.confirmarRegistro)
        )
        button.check(matches(isDisplayed()))
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
