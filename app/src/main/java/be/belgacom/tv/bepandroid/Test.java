package be.belgacom.tv.bepandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

/**
 * Created by Akash.Sharma on 4/26/2017.
 */

public class Test extends ContextThemeWrapper
{


    private static Test test;

    private Test()
    {}

    public static Test makeTest()
    {
         if(test==null)
         {
             test = new Test();
             return test;
         }
         else
         {
             return test;
         }
    }


    public void showToast(String message , Context mContext)
    {
        //Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
    }
}
