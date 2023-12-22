package es.unex.gps.weathevent.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.gps.weathevent.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EventosTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun CreacionDeUnEvento() {
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
        appCompatEditText2.perform(replaceText("testCrearEvento"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test123"), closeSoftKeyboard())

        val materialButton7 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton7.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.listEventFragment), withContentDescription("Eventos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.add_event_button), withContentDescription("Añadir evento"),
                childAtPosition(
                    allOf(
                        withId(R.id.listEventFragment),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.event_name_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Evento Test"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.location_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("La Coronada"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.date_picker_btn), withText("Seleccionar fecha"),
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
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.hour_picker_btn), withText("Seleccionar hora"),
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
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

        Thread.sleep(15000)

        val materialButton6 = onView(
            allOf(
                withId(R.id.save_event_btn), withText("Guardar"),
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
        materialButton6.perform(click())

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.rv_list_events),
                        withParent(withId(R.id.listEventFragment))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))
    }

    @Test
    fun BorradoDeUnEvento() {
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
        appCompatEditText2.perform(replaceText("testBorrarEvento"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test123"), closeSoftKeyboard())

        val materialButton7 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton7.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.listEventFragment), withContentDescription("Eventos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.add_event_button), withContentDescription("Añadir evento"),
                childAtPosition(
                    allOf(
                        withId(R.id.listEventFragment),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.event_name_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Evento Test"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.location_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("La Coronada"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.date_picker_btn), withText("Seleccionar fecha"),
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
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.hour_picker_btn), withText("Seleccionar hora"),
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
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

        Thread.sleep(15000)

        val materialButton6 = onView(
            allOf(
                withId(R.id.save_event_btn), withText("Guardar"),
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
        materialButton6.perform(click())

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.rv_list_events),
                        withParent(withId(R.id.listEventFragment))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.delete_button), withContentDescription("Borrar evento"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cv_event),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        onView(withId(R.id.rv_list_events))
            .check(matches(Matchers.not(hasDescendant(isDisplayed()))))
    }

    @Test
    fun VerNotificacionDeSituacionMeteorologicaDeUnEventoRegistrado(){
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
        appCompatEditText2.perform(replaceText("testVerEvento"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(replaceText("test@test.es"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(replaceText("test123"), closeSoftKeyboard())

        val materialButton7 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton7.perform(click())

        val frameLayout = onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(matches(isDisplayed()))
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.listEventFragment), withContentDescription("Eventos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.add_event_button), withContentDescription("Añadir evento"),
                childAtPosition(
                    allOf(
                        withId(R.id.listEventFragment),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.event_name_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Ver notificación evento test"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.location_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("La Coronada"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.date_picker_btn), withText("Seleccionar fecha"),
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
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.hour_picker_btn), withText("Seleccionar hora"),
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
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

        Thread.sleep(15000)

        val materialButton6 = onView(
            allOf(
                withId(R.id.save_event_btn), withText("Guardar"),
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
        materialButton6.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.cv_event),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rv_list_events),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())

        Thread.sleep(2000)

        val textView = onView(
            allOf(
                withId(R.id.ubication_text), withText("La Coronada"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("La Coronada")))

        val button = onView(
            allOf(
                withId(R.id.export_button), withText("Exportar evento"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.indiceuv_title_view), withText("Índice UV"),
                withParent(
                    allOf(
                        withId(R.id.indiceuv_card),
                        withParent(withId(R.id.thrid_row))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Índice UV")))

        val linearLayout = onView(
            allOf(
                withId(R.id.humedad_card),
                withParent(
                    allOf(
                        withId(R.id.thrid_row),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))
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
