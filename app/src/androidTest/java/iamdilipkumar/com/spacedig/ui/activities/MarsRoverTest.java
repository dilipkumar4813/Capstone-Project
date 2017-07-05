package iamdilipkumar.com.spacedig.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import iamdilipkumar.com.spacedig.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MarsRoverTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void marsRoverTest() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_main_staggered), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.generalitem_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_full_screen), withText("View as full screen")));
        appCompatTextView.perform(scrollTo(), click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.generalitem_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(16, click()));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.tv_full_screen), withText("View as full screen")));
        appCompatTextView2.perform(scrollTo(), click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        pressBack();

    }

}
