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

public class OrderModel {

    private Context cxt;

    public OrderModel(Context cxt) {
        this.cxt = cxt;
    }


    public void loadClassRooms(AbsCallback callBack) {
        OkGo.get(URL.BASE + URL.CLASSROOM)
                .tag(cxt)
                .execute(callBack);
    }


    public void orderClassRooms(AbsCallback callBack, String classname, String classnum, String studentid, int classseat, int type) {
        OkGo.post(URL.BASE + URL.ORDERCLASSROOM)
                .params("classname", classname)
                .params("classnum", classnum)
                .params("studentid", studentid)
                .params("classseat", classseat)
                .params("type", type)
                .tag(cxt)
                .execute(callBack);
    }

    public void loadorderRes(AbsCallback callBack, String studentid) {
        OkGo.post(URL.BASE + URL.ORDERRES)
                .params("studentid", studentid)
                .params("admin", -1)
                .tag(cxt)
                .execute(callBack);
    }

    public void loadDealorder(AbsCallback callBack) {
        OkGo.post(URL.BASE + URL.ORDERRES)
                .params("admin", 1)
                .tag(cxt)
                .execute(callBack);
    }

    public void dealorder(AbsCallback callBack, String classnum, String studentid, int dealtype) {
        OkGo.post(URL.BASE + URL.DEALORDER)
                .params("classnum", classnum)
                .params("studentid", studentid)
                .params("dealtype", dealtype)
                .tag(cxt)
                .execute(callBack);
    }


    public void cancle() {
        OkGo.getInstance().cancelTag(cxt);
    }
}
