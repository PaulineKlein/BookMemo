package com.pklein.bookmemo;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pklein.bookmemo.data.Book;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class NavigateBetweenStepsTest {


    @Rule
    public ActivityTestRule<SeeSelectedBooksActivity> mActivityTestRule =
            new ActivityTestRule<SeeSelectedBooksActivity>(SeeSelectedBooksActivity.class){

        @Override
        protected Intent getActivityIntent() {

            Book BookToLookFor = new Book(-1, "no_book_found", "", "", "MANGA", -1, -1, -1, -1, -1, -1, -1);
            Intent intent = new Intent();
            intent.putExtra("BookToLookFor", BookToLookFor);
            return intent;
        }
    };

    @Test
    public void clickOn_oneStep() {

        // we should have a blank page as there is no book inside :
        onView(withId(R.id.TV_title))
                .check(doesNotExist());

        //check if the recyclerview is display :
        onView(withId(R.id.recyclerview_seeAll))
                .check(matches(isDisplayed()));
    }
}
