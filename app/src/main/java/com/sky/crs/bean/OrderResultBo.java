package com.sky.crs.bean;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/21 1:09
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import com.sky.crs.R;

import static com.sky.crs.R.mipmap.common;

public class OrderResultBo {
    public String name;
    public String number;
    public int seat;
    public int type;
    public String studentid;
    public int dealtype;

    public int getTypeIc() {
        int res = common;
        switch (type) {
            case 0:
                res = R.mipmap.media;
                break;
            case 1:
                res = R.mipmap.jf;
                break;
            case 2:
                res = R.mipmap.shiyan;
                break;
            case 3:
                res = R.mipmap.meet;
                break;
            case 4:
                res = common;
                break;
        }

        return res;
    }

    public int getdealtype() {
        int res = R.drawable.lo;
        switch (dealtype) {
            case 0:
                res = R.drawable.lo;
                break;
            case 1:
                res = R.drawable.pas;
                break;
            case 2:
                res = R.drawable.su;
                break;
        }

        return res;
    }
}
