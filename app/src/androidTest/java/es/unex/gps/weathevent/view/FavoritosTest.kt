package es.unex.gps.weathevent.view

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.gps.weathevent.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FavoritosTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun verFavoritosAlAnadirDesdeListaDeBusqueda(){
        val materialButton = Espresso.onView(
            withId(R.id.registro)
        )
        materialButton.perform(ViewActions.click())

        val appCompatEditText = Espresso.onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(
            ViewActions.replaceText("test fav"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText2 = Espresso.onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(
            ViewActions.replaceText("testAnadirFavorito"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText3 = Espresso.onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(
            ViewActions.replaceText("test@test.es"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText4 = Espresso.onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())

        val materialButton2 = Espresso.onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(ViewActions.click())

        val frameLayout = Espresso.onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(ViewAssertions.matches(isDisplayed()))

        val bottomNavigationItemView = Espresso.onView(
            allOf(
                withId(R.id.buscarFragment),
                ViewMatchers.withContentDescription("Búsqueda"),
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
        bottomNavigationItemView.perform(ViewActions.click())

        Thread.sleep(15000)

        val textInputEditText = Espresso.onView(
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
        textInputEditText.perform(
            ViewActions.replaceText("Montijo"),
            ViewActions.closeSoftKeyboard()
        )

        val materialTextView = Espresso.onView(
            allOf(
                withId(R.id.favoriteIcon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_item),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(ViewActions.click())

        val bottomNavigationItemView2 = Espresso.onView(
            allOf(
                withId(R.id.favoritosFragment),
                ViewMatchers.withContentDescription("Favoritos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(ViewActions.click())

        val rvFavoritos = Espresso.onView(
            withId(R.id.rvFavoritos)
        )
        rvFavoritos.check(ViewAssertions.matches(isDisplayed()))

        val textView = Espresso.onView(
            allOf(
                withId(R.id.cityName2), ViewMatchers.withText("Montijo"),
                withParent(withParent(withId(R.id.cv_Item))),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Montijo")))
    }

    @Test
    fun verFavoritosAlAnadirDesdeInformacionDeLocalidad(){

        val materialButton = Espresso.onView(
            withId(R.id.registro)
        )
        materialButton.perform(ViewActions.click())

        val appCompatEditText = Espresso.onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(
            ViewActions.replaceText("test fav 2"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText2 = Espresso.onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(
            ViewActions.replaceText("testAnadirFavorito2"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText3 = Espresso.onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(
            ViewActions.replaceText("test@test.es"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText4 = Espresso.onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())

        val materialButton2 = Espresso.onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(ViewActions.click())

        val frameLayout = Espresso.onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(ViewAssertions.matches(isDisplayed()))

        val bottomNavigationItemView = Espresso.onView(
            allOf(
                withId(R.id.buscarFragment),
                ViewMatchers.withContentDescription("Búsqueda"),
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
        bottomNavigationItemView.perform(ViewActions.click())

        Thread.sleep(15000)

        val textInputEditText = Espresso.onView(
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
        textInputEditText.perform(
            ViewActions.replaceText("Montijo"),
            ViewActions.closeSoftKeyboard()
        )

        val materialTextView = Espresso.onView(
            allOf(
                withId(R.id.cityName), ViewMatchers.withText("Montijo"),
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
        materialTextView.perform(ViewActions.click())

        val materialTextView2 = Espresso.onView(
            allOf(
                withId(R.id.imageFav),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(ViewActions.click())

        Espresso.pressBack()

        val bottomNavigationItemView2 = Espresso.onView(
            allOf(
                withId(R.id.favoritosFragment),
                ViewMatchers.withContentDescription("Favoritos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(ViewActions.click())

        val rvFavoritos = Espresso.onView(
            withId(R.id.rvFavoritos)
        )
        rvFavoritos.check(ViewAssertions.matches(isDisplayed()))

        val textView = Espresso.onView(
            allOf(
                withId(R.id.cityName2), ViewMatchers.withText("Montijo"),
                withParent(withParent(withId(R.id.cv_Item))),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Montijo")))
    }

    @Test
    fun eliminarFavoritos(){
        val materialButton = Espresso.onView(
            withId(R.id.registro)
        )
        materialButton.perform(ViewActions.click())

        val appCompatEditText = Espresso.onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(
            ViewActions.replaceText("test eliminar"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText2 = Espresso.onView(
            withId(R.id.usernameRegistro)
        )
        appCompatEditText2.perform(
            ViewActions.replaceText("testEliminarFavorito"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText3 = Espresso.onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(
            ViewActions.replaceText("test@test.es"),
            ViewActions.closeSoftKeyboard()
        )

        val appCompatEditText4 = Espresso.onView(
            withId(R.id.passRegistro)
        )
        appCompatEditText4.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())

        val materialButton2 = Espresso.onView(
            withId(R.id.confirmarRegistro)
        )
        materialButton2.perform(ViewActions.click())

        val frameLayout = Espresso.onView(
            withId(R.id.bottom_navigation)
        )
        frameLayout.check(ViewAssertions.matches(isDisplayed()))

        val bottomNavigationItemView = Espresso.onView(
            allOf(
                withId(R.id.buscarFragment),
                ViewMatchers.withContentDescription("Búsqueda"),
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
        bottomNavigationItemView.perform(ViewActions.click())

        Thread.sleep(15000)

        val textInputEditText = Espresso.onView(
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
        textInputEditText.perform(
            ViewActions.replaceText("Montijo"),
            ViewActions.closeSoftKeyboard()
        )

        val materialTextView = Espresso.onView(
            allOf(
                withId(R.id.favoriteIcon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_item),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(ViewActions.click())

        val bottomNavigationItemView2 = Espresso.onView(
            allOf(
                withId(R.id.favoritosFragment),
                ViewMatchers.withContentDescription("Favoritos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(ViewActions.click())

        val cardView = Espresso.onView(
            allOf(
                withId(R.id.cv_Item),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rvFavoritos),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(ViewActions.click())

        val appCompatImageView2 = Espresso.onView(
            allOf(
                withId(R.id.imageFav),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(ViewActions.click())

        Espresso.pressBack()

        Espresso.onView(withId(R.id.rvFavoritos))
            .check(ViewAssertions.matches(not(ViewMatchers.hasDescendant(isDisplayed()))))
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