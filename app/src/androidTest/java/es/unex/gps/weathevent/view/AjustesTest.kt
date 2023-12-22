package es.unex.gps.weathevent.view

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.gps.weathevent.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AjustesTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun preferenciasTest() {

        val materialButton = onView(
            withId(R.id.registro)
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(
            ViewActions.replaceText("test preferencias"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText2 = onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(
            ViewActions.replaceText("testPreferencias"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText3 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(
            ViewActions.replaceText("test@test.es"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText4 = onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())

        val materialButton2 = onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(click())

        val overflowMenuButton = onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val materialTextView = onView(
            ViewMatchers.withText("Ajustes")
        )
        materialTextView.perform(click())

        val preferencia = onView(
            ViewMatchers.withText("Celsius")
        )
        preferencia.perform(click())

        val preferencia2 = onView(
            ViewMatchers.withText("Fahrenheit")
        )
        preferencia2.perform(click())

        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.buscarFragment), ViewMatchers.withContentDescription("Búsqueda"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(10000)

        val textInputEditText = onView(
            Matchers.allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editText),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText.perform(
            ViewActions.replaceText("Montijo"),
            ViewActions.closeSoftKeyboard()
        )

        Thread.sleep(2000)

        val materialTextView2 = onView(
            Matchers.allOf(
                withId(R.id.cityName), ViewMatchers.withText("Montijo"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.search_item),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialTextView2.perform(click())

        Thread.sleep(1500)

        val textView = onView(
            Matchers.allOf(
                withId(R.id.temperature_view), ViewMatchers.withText(Matchers.endsWith("º F")),
                ViewMatchers.withParent(ViewMatchers.withParent(withId(android.R.id.content))),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText(Matchers.endsWith("º F"))))
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