package com.pklein.bookmemo;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pklein.bookmemo.data.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceReadMoreTest {

    @Rule
    public ActivityTestRule<ReadMoreActivity> mActivityTestRule =
            new ActivityTestRule<ReadMoreActivity>(ReadMoreActivity.class){
                @Override
                protected Intent getActivityIntent() {

                    Book BookToLookFor = new Book(-1, "naruto", "", "", "MANGA", -1, -1, -1, -1, -1, -1, -1);
                    Intent intent = new Intent();
                    intent.putExtra("BookToLookFor", BookToLookFor);
                    return intent;
                }
            };

    private IdlingResource mIdlingResource;

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void seePage() {

        //check if the next page is open :
        onView(withId(R.id.content_book_readMore))
                .check(matches(isDisplayed()));

        //check if the title is ok :
        onView(withId(R.id.title_book_readMore))
                .check(matches(withText("naruto")))
                .check(matches(isDisplayed()));
    }

    // unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
