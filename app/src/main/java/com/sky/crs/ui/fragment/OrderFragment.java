package com.sky.crs.ui.fragment;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/16 21:57
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.view.View;

import com.sky.crs.R;
import com.sky.crs.ui.ExpressActivity;

public class OrderFragment extends BaseFragment implements View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_order;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_express:
                startActivity(new Intent(getActivity(), ExpressActivity.class));
                break;

            default:
                break;
        }
    }
}
