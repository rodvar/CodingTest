package com.rodvar.westpactest;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.util.FragmentTestUtil.startFragment;

/**
 * Created by rodrigo on 15/06/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 19)
public class MainActivityFragmentTest {

    private MainActivityFragment fragment;

    @Before
    public void setUp() throws Exception {
        this.fragment = new MainActivityFragment();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        startFragment(this.fragment);
    }

    @Test
    public void testFragmentNotNull() {
        assertNotNull(this.fragment);
    }

    @Test
    public void testMainText() throws Exception {
        assertThat(((TextView) this.fragment.getView().findViewById(R.id.wheatherLbl)).getText().toString(), equalTo(fragment.getActivity().getString(R.string.wheatherLbl)));
    }
}
