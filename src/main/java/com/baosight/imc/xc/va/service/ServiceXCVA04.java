package com.baosight.imc.xc.va.service;

import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: lx
 * @date: 2024/10/31 15:36
 * @description: 统计-柱状图
 */
public class ServiceXCVA04 extends ServiceBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    public EiInfo initBarChart(EiInfo inInfo){
        List<Map> dataList = dao.query("XCVA01.queryTodayData", new HashMap());

        //原始数据分类
        List<Map> data1 = new ArrayList<>();
        List<Map> data2 = new ArrayList<>();
        List<Map> data3 = new ArrayList<>();
        List<Map> data4 = new ArrayList<>();

        for(Map data : dataList){
            int violationTime = Integer.valueOf((String) data.get("violationTime")).intValue();
            if(violationTime >= 0 && violationTime <= 600){
                data1.add(data);
            };
            if(violationTime > 600 && violationTime <= 1200){
                data2.add(data);
            };
            if(violationTime > 1200 && violationTime <= 1800){
                data3.add(data);
            };
            if(violationTime > 1800 && violationTime <= 2400){
                data4.add(data);
            };
        }

        List<Map> typeList = dao.query("XCVA01.queryViolationType", new HashMap());

        Map<String, List<Map>> type1 = data1
                .stream().collect(Collectors.groupingBy(map -> (String)map.get("violationType")));
        Map<String, List<Map>> type2 = data2
                .stream().collect(Collectors.groupingBy(map -> (String)map.get("violationType")));
        Map<String, List<Map>> type3 = data3
                .stream().collect(Collectors.groupingBy(map -> (String)map.get("violationType")));
        Map<String, List<Map>> type4 = data4
                .stream().collect(Collectors.groupingBy(map -> (String)map.get("violationType")));

        //前端展示数据项
        LinkedList<String> source1 = new LinkedList<>();
        source1.add("time");
        LinkedList<String> source2 = new LinkedList<>();
        source2.add("00:00~06:00");
        LinkedList<String> source3 = new LinkedList<>();
        source3.add("06:00~12:00");
        LinkedList<String> source4 = new LinkedList<>();
        source4.add("12:00~18:00");
        LinkedList<String> source5 = new LinkedList<>();
        source5.add("18:00~00:00");

        for(Map type : typeList){
            source1.add((String)type.get("itemCname"));

            if(data1.size() <= 0){
                source2.add("0");
            }else {
                List<Map> temp = type1.get(type.get("itemCode"));
                if(temp != null && temp.size() > 0){
                    source2.add(String.valueOf(temp.size()));
                }else {
                    source2.add("0");
                }
            }

            if(data2.size() <= 0){
                source3.add("0");
            }else {
                List<Map> temp = type2.get(type.get("itemCode"));
                if(temp != null && temp.size() > 0){
                    source3.add(String.valueOf(temp.size()));
                }else {
                    source3.add("0");
                }
            }

            if(data3.size() <= 0){
                source4.add("0");
            }else {
                List<Map> temp = type3.get(type.get("itemCode"));
                if(temp != null && temp.size() > 0){
                    source4.add(String.valueOf(temp.size()));
                }else {
                    source4.add("0");
                }
            }

            if(data4.size() <= 0){
                source5.add("0");
            }else {
                List<Map> temp = type4.get(type.get("itemCode"));
                if(temp != null && temp.size() > 0){
                    source5.add(String.valueOf(temp.size()));
                }else {
                    source5.add("0");
                }
            }

        }

        LinkedList<List> source = new LinkedList<>();

        source.add(source1);
        source.add(source2);
        source.add(source3);
        source.add(source4);
        source.add(source5);

        Map<String, Object> map = new HashMap<>();
        map.put("source",source);
        Gson gson = new Gson();
        String dataStr = gson.toJson(map);
        EiInfo outInfo = new EiInfo();
        outInfo.set("data",dataStr);
        return outInfo;
    }
}
