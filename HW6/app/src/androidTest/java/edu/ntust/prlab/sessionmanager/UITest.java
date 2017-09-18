package edu.ntust.prlab.sessionmanager;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class UITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void uITest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.button_add), isDisplayed()));
        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.input_name), isDisplayed()));
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_age), isDisplayed()));
        ViewInteraction femaleButton = onView(
                allOf(withId(R.id.radio_female),
                        withParent(withId(R.id.radio_gender)), isDisplayed()));
        ViewInteraction maleButton = onView(
                allOf(withId(R.id.radio_male),
                        withParent(withId(R.id.radio_gender)), isDisplayed()));


        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK")));

        floatingActionButton.perform(click());
        appCompatEditText1.perform(click(),replaceText("s1"), closeSoftKeyboard());
        appCompatEditText2.perform(click(),replaceText("87"), closeSoftKeyboard());
        maleButton.perform(click());
        appCompatButton.perform(scrollTo(), click());



        floatingActionButton.perform(click());
        appCompatEditText1.perform(click(),replaceText("s2"), closeSoftKeyboard());
        appCompatEditText2.perform(click(),replaceText("36"), closeSoftKeyboard());
        femaleButton.perform(click());
        appCompatButton.perform(scrollTo(), click());

        floatingActionButton.perform(click());
        appCompatEditText1.perform(click(),replaceText("s0"), closeSoftKeyboard());
        appCompatEditText2.perform(click(),replaceText("44"), closeSoftKeyboard());
        maleButton.perform(click());
        appCompatButton.perform(scrollTo(), click());
        //sort
        ViewInteraction sortByID = onView(
                allOf(withId(R.id.title), withText("Sort by ID"), isDisplayed()));
        ViewInteraction sortByName = onView(
                allOf(withId(R.id.title), withText("Sort by Name"), isDisplayed()));
        ViewInteraction sortByAge = onView(
                allOf(withId(R.id.title), withText("Sort by Age"), isDisplayed()));
        ViewInteraction sortByGender = onView(
                allOf(withId(R.id.title), withText("Sort by Gender"), isDisplayed()));
        //sort

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        sortByName.perform(click());
        textViewTest(R.id.item_name,0,1,"s0");
        textViewTest(R.id.item_age,0,3,"44");
        textViewTest(R.id.item_gender,0,2,"\u2642");
        textViewTest(R.id.item_name,1,1,"s1");
        textViewTest(R.id.item_age,1,3,"87");
        textViewTest(R.id.item_gender,1,2,"\u2642");
        textViewTest(R.id.item_name,2,1,"s2");
        textViewTest(R.id.item_age,2,3,"36");
        textViewTest(R.id.item_gender,2,2,"\u2640");
        //
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        sortByGender.perform(click());
        textViewTest(R.id.item_name,0,1,"s2");
        textViewTest(R.id.item_age,0,3,"36");
        textViewTest(R.id.item_gender,0,2,"\u2640");
        textViewTest(R.id.item_name,1,1,"s1");
        textViewTest(R.id.item_age,1,3,"87");
        textViewTest(R.id.item_gender,1,2,"\u2642");
        textViewTest(R.id.item_name,2,1,"s0");
        textViewTest(R.id.item_age,2,3,"44");
        textViewTest(R.id.item_gender,2,2,"\u2642");


        //
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        sortByAge.perform(click());
        textViewTest(R.id.item_name,0,1,"s2");
        textViewTest(R.id.item_age,0,3,"36");
        textViewTest(R.id.item_gender,0,2,"\u2640");
        textViewTest(R.id.item_name,1,1,"s0");
        textViewTest(R.id.item_age,1,3,"44");
        textViewTest(R.id.item_gender,1,2,"\u2642");
        textViewTest(R.id.item_name,2,1,"s1");
        textViewTest(R.id.item_age,2,3,"87");
        textViewTest(R.id.item_gender,2,2,"\u2642");

        //
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        sortByID.perform(click());
        textViewTest(R.id.item_name,0,1,"s1");
        textViewTest(R.id.item_age,0,3,"87");
        textViewTest(R.id.item_gender,0,2,"\u2642");
        textViewTest(R.id.item_name,1,1,"s2");
        textViewTest(R.id.item_age,1,3,"36");
        textViewTest(R.id.item_gender,1,2,"\u2640");
        textViewTest(R.id.item_name,2,1,"s0");
        textViewTest(R.id.item_age,2,3,"44");
        textViewTest(R.id.item_gender,2,2,"\u2642");




        //
        onView(withId(R.id.recycler_view_member)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(withId(R.id.recycler_view_member)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(withId(R.id.recycler_view_member)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));



        RecyclerView recyclerView = (RecyclerView)this.mActivityTestRule.getActivity().findViewById(R.id.recycler_view_member);
        int count=recyclerView.getLayoutManager().getItemCount();
        assertThat(count, is(0));





    }
    private void textViewTest(int id ,int view_postion ,int item_postion,String expected_text)
    {
        ViewInteraction textView = onView(
                allOf(withId(id),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view_member),
                                        view_postion),
                                item_postion),
                        isDisplayed()));
        textView.check(matches(withText(expected_text)));
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
