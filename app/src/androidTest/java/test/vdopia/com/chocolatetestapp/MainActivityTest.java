package test.vdopia.com.chocolatetestapp;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() throws InterruptedException{
        Application.getInstance();
        mActivityTestRule.launchActivity(new Intent());
        while (Application.getInstance().getPartnerList().isEmpty() == false) {
            System.out.println("MainActivity test running");
            Thread.sleep(45000);
        }
        System.out.println("MainActivity test finished");
    }

    /*
    @RunWith(AndroidJUnit4.class)
    public class ExampleInstrumentedTest {
        @Test
        public void useAppContext() {
            // Context of the app under test.
            Context appContext = InstrumentationRegistry.getTargetContext();
            assertEquals("test.vdopia.com.chocolatetestapp", appContext.getPackageName());
            //doesn't work
            appContext.startActivity(new Intent(appContext, MainActivity.class));
        }
    }
    */

}
