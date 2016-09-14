package demo.cwf.com.androidgpsdemo;

import android.app.Application;

/**
 * Created at 陈 on 2016/9/14.
 *
 * @author cwf
 * @email 237142681@qq.com
 */
public class AppContext extends Application {
    private static AppContext appContext;

    public AppContext() {
        appContext = this;
    }

    public static AppContext getAppContext() {
        if (appContext == null)
            appContext = new AppContext();
        return appContext;
    }
}
