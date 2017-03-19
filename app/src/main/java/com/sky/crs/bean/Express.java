package com.sky.crs.bean;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/18 2:18
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Express {

    HashMap<String, String> map;

    public Express() {
        initData();
    }

    private void initData() {
        map = new HashMap<>();
        map.put("aae全球专递", "aae");
        map.put("安捷快递", "anjie");
        map.put("安信达快递", "anxindakuaixi");
        map.put("彪记快递", "biaojikuaidi");
        map.put("申通", "shentong");
        map.put("顺丰", "shunfeng");
        map.put("天天快递", "tiantian");
        map.put("中通速递", "zhongtong");
        map.put("圆通速递", "yuantong");
        map.put("韵达快运", "yunda");
        map.put("宅急送", "zhaijisong");
        map.put("中铁快运", "zhongtiekuaiyun");
        map.put("邮政包裹挂号信", "youzhengguonei");
        map.put("ups", "ups");
        map.put("全峰快递", "quanfengkuaidi");
        map.put("联邦快递（国内）", "lianb");
        map.put("京广速递", "jinguangsudikuaijian");
        map.put("汇通快运", "huitongkuaidi");
        map.put("广东邮政物流", "guangdongyouzhengwuliu");
        map.put("国通快递", "guotongkuaidi");
        map.put("ems快递", "ems");
        map.put("德邦物流", "debangwuliu");
        map.put("中国东方（COE）", "coe");
        map.put("芝麻开门", "zhimakaimen");
    }

    public String getExpressCode(String key) {
        return map.get(key);
    }

    public List<String> getExCompanys() {
        List<String> cms = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            cms.add(entry.getKey());
        }
        return cms;
    }
}
