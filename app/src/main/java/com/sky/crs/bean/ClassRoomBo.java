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

public class ClassRoomBo {
    public String name;
    public String number;
    public int seat;
    public int steated;
    public int type;

    public boolean isHot() {
        return steated > seat / 2;
    }

    public String getSeat() {
        return steated + "/" + seat;
    }

    public int getTypeIc() {
        int res = R.mipmap.common;
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
                res = R.mipmap.common;
                break;
        }

        return res;
    }
}
