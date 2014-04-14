package com.infusion.apollo.framework.temp;

/**
 * Created by ali on 13/04/14.
 */
public interface ILog {
    void v(java.lang.String tag, java.lang.String msg);

    void v(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    void d(java.lang.String tag, java.lang.String msg);

    void d(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    void i(java.lang.String tag, java.lang.String msg);

    void i(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    void w(java.lang.String tag, java.lang.String msg);

    void w(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    void w(java.lang.String tag, java.lang.Throwable tr);

    void e(java.lang.String tag, java.lang.String msg);

    void e(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);

    void wtf(java.lang.String tag, java.lang.String msg);

    void wtf(java.lang.String tag, java.lang.Throwable tr);

    void wtf(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr);
}
