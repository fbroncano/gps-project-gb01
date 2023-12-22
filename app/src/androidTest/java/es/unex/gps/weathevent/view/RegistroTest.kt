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
class RegistroTests {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun RegistroExitoConDatosValidosDeUnUsuario() {
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
        appCompatEditText2.perform(replaceText("testRegistro"), closeSoftKeyboard())

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
    }

    @Test
    fun ComprobacionDeTodosLosMensajesDeErrorDelRegistro() {
        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.errorNombre), withText("El nombre no puede estar vacío."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("El nombre no puede estar vacío.")))

        val textView2 = onView(
            allOf(
                withId(R.id.errorUserRegistro), withText("El usuario no puede estar vacío."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("El usuario no puede estar vacío.")))

        val textView3 = onView(
            allOf(
                withId(R.id.errorEmailRegistro), withText("El correo electrónico no es válido."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("El correo electrónico no es válido.")))

        val textView4 = onView(
            allOf(
                withId(R.id.errorPassRegistro), withText("La contraseña no puede estar vacía."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("La contraseña no puede estar vacía.")))

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
        appCompatEditText.perform(replaceText("test prueba"), closeSoftKeyboard())

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
        appCompatEditText2.perform(replaceText("test s"), closeSoftKeyboard())

        val materialButton3 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton3.perform(click())

        val textView5 = onView(
            allOf(
                withId(R.id.errorUserRegistro), withText("No puede contener espacios en blanco."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("No puede contener espacios en blanco.")))

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.usernameRegistro), withText("test s"),
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
        appCompatEditText3.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.usernameRegistro), withText("test s"),
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
        appCompatEditText4.perform(replaceText("testError"))

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.usernameRegistro), withText("testError"),
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
        appCompatEditText5.perform(closeSoftKeyboard())

        val appCompatEditText6 = onView(
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
        appCompatEditText6.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText7 = onView(
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
        appCompatEditText7.perform(replaceText("test s"), closeSoftKeyboard())

        val materialButton4 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton4.perform(click())

        val textView6 = onView(
            allOf(
                withId(R.id.errorPassRegistro), withText("No puede contener espacios en blanco."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("No puede contener espacios en blanco.")))

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.passRegistro), withText("test s"),
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
        appCompatEditText8.perform(click())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.passRegistro), withText("test s"),
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
        appCompatEditText9.perform(replaceText("test"))

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.passRegistro), withText("test"),
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
        appCompatEditText10.perform(closeSoftKeyboard())

        val materialButton5 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton5.perform(click())
    }

    @Test
    fun ComprobacionDelFuncionamientoDelBotonQueLlevaAIniciarSesion() {
        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            withId(R.id.inicarsesionRegistro)
        )
        materialButton2.perform(click())

        val button = onView(
            withId(R.id.entrar)
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
