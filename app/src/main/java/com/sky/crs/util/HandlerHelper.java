package com.sky.crs.util;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/13 1:21
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.os.AsyncTask;

public class HandlerHelper {

    public static <Result> void post(final SimpleAsyncTask<Result> task) {

        new AsyncTask<Void, Integer, Result>() {


            @Override
            protected Result doInBackground(Void... params) {
                return task.doInBackground();
            }

            @Override
            protected void onPostExecute(Result result) {
                task.onPostExecute(result);
            }
        }.execute();
    }

    public interface SimpleAsyncTask<Result> {

        Result doInBackground();

        void onPostExecute(Result result);
    }
}
