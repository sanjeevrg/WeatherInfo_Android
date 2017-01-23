package com.sanjeevg.weatherinfo;

/**
 * Created by sanjeevg on 23/01/17.
 */
import junit.framework.TestCase;

import org.json.JSONObject;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;
import java.lang.reflect.Method;

public class FetchWeatherTest extends AndroidTestCase {
    private Context instrumentationCtx;
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * @return The {@link Context} of the test project.
     */
    private Context getTestContext()
    {
        try
        {
            Method getTestContext = ServiceTestCase.class.getMethod("getTestContext");
            return (Context) getTestContext.invoke(this);
        }
        catch (final Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    public final void testSomething() {
//        EmptyActivity activity;
        try {
            Context mContext = null;
            JSONObject data = FetchWeather.getJSON(getContext(), "Bangalore");
            assertEquals(data.getInt("cod"), 200);

        }catch (Exception e){
            return ;
        }
    }

}
