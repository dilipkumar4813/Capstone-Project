package iamdilipkumar.com.spacedig.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import iamdilipkumar.com.spacedig.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ApodTest {

    @Rule
    public ActivityTestRule<SpaceListActivity> mActivityTestRule = new ActivityTestRule<>(SpaceListActivity.class);

    @Test
    public void apodTest() throws InterruptedException {
        Thread.sleep(5000);

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_main_staggered), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        Thread.sleep(5000);

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_share), withContentDescription("Share"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.collapsing_toolbar)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

}
