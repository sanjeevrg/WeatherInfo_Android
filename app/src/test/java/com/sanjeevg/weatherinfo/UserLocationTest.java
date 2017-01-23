package com.sanjeevg.weatherinfo;

/**
 * Created by sanjeevg on 23/01/17.
 */

import android.support.v7.app.AppCompatActivity;

import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;
import android.app.Activity;
public class UserLocationTest  extends Activity{

    @Test
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        this.getPreferences(Activity.MODE_PRIVATE);
        assertEquals(expected,reality);
        boolean check1 = new UserLocation(this).checkValidLocation("India","Bangalore");
        assertEquals(check1,true);
        boolean check2 = new UserLocation(this).checkValidLocation("Indus","Bangalore");
        assertEquals(check2,false);

    }



}
