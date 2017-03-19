package com.sky.crs.model;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/19 20:03
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.sky.crs.net.URL;

public class NoticeModel {

    private Context cxt;

    public NoticeModel(Context cxt) {
        this.cxt = cxt;
    }

    public void loadNotice(AbsCallback callBack) {
        OkGo.get(URL.BASE + URL.NOTICE)
                .params("type", 0)
                .tag(cxt)
                .execute(callBack);
    }

    public void cancle() {
        OkGo.getInstance().cancelTag(cxt);
    }
}
