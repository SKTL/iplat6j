package com.baosight.imc.xb.ai.common.utils;

import com.baosight.elim.common.utils.GlobalUtils;
import com.baosight.iplat4j.core.cache.CacheManager;
import com.baosight.iplat4j.core.data.dao.DaoFactory;
import com.baosight.iplat4j.core.data.dao.NamedSqlDao;
import com.baosight.iplat4j.core.data.ibatis.dao.Dao;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.ioc.spring.PlatApplicationContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
    static NamedSqlDao sqlDao = (NamedSqlDao) PlatApplicationContext.getBean("namedSqlDao");

    /**
     * OA4.0 待办请求sign示例
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /*Crypto crypto = new CryptoImpl();
        SecurityKey priKey = new SecurityKey("MIGIAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBG4wbAIBAQIhAOs/u4L9nBQBf8GjPAb7Oaw1TDEzq/t1yGBGjbzbANgMoUQDQgAE/vqQSX/oUYLr7mlmXNEe9REm+PMU9PsZVkmHuWYEF4HRnWxmWuZB+mnnMJsqfRO1TCxpD8J5sBn0BJTKbgXheg==");
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("syscode", "IMC");
        hashMap.put("flowid", "IMC-V1-100002");
        hashMap.put("requestname", "IMC-待办2");
        hashMap.put("workflowname", "一级责任单位制定整改计划2");
        hashMap.put("nodename", "NODE-0089");
        hashMap.put("pcurl", "http://imctest.baogang.info/imc-pa/web/TCDC6003?rectifyId=0000000061&rectifyPlanId=0000000062&_TASKID_=6463F6C1-E13F-4D73-8265-8203469C9A8A");
        hashMap.put("appurl", "https://moa.baowugroup.com/mobile-portal/news-express/uri.html?appid=com.baosight.BaoXD&param=talk?guid=918b2816-45d4-4142-a3e9-4ae01b4835db");
        hashMap.put("isremark", "0");
        hashMap.put("viewtype", "0");
        hashMap.put("creator", "153379");
        hashMap.put("createdatetime", "2022-08-08 10:30:00");
        hashMap.put("receiver", "180424");
        hashMap.put("receivedatetime", "2022-08-08 10:31:00");
        hashMap.put("receivets", "1659925802000");
        hashMap.put("signver", "V1");
        //System.out.println(jsonObject.toString());
        String jsonSign = crypto.signForJson(new Gson().toJson(hashMap), AlgEnum.SM2, priKey);
        System.out.println("json签名数据：" + jsonSign);*/
        String str = "123-123";
        System.out.print(checkStringIntegerAndLetterTh(str));
    }

    /**
     * 导出文件校验多人导出规则
     * TODO 目前先限制 一个userid 只允许正在导出一份文件 后期可以拆分为 单节点&单文件
     */
    public static EiInfo checkDownloadUser(String userId) {
        EiInfo outInfo = new EiInfo();
        Map cache = CacheManager.getCache("imc:fc:userDownloadCache");
        String existsDownloadFile = cache.get(userId) == null ? "" : (String) cache.get(userId);
        if (StringUtils.isNotBlank(existsDownloadFile)) {
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(GlobalUtils.strFmt("您有一个正在导出的文件{0}，请稍后尝试继续导出！", existsDownloadFile));
        } else {
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
            outInfo.setMsg(GlobalUtils.strFmt("PASS!"));
        }
        return outInfo;
    }

    /**
     * 判断数组中是否有重复值
     */
    public static boolean checkRepeat(String[] array) {
        Set<String> set = new HashSet<String>();
        for (String str : array) {
            set.add(str);
        }
        if (set.size() != array.length) {
            return false;//有重复
        } else {
            return true;//不重复
        }
    }

    /**
     * 判断数组中元素是否全部相同
     */
    public static boolean checkAllSame(String[] array) {
        for (int i = 1; i < array.length; i++) {
            if (!array[i - 1].equals(array[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串能否转化为数字类型
     *
     * @param str
     * @return
     */
    public static boolean checkStringToDecimal(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 验证字符串是否为数字或字母
     *
     * @param str
     * @return
     */
    public static boolean checkStringIntegerAndLetter(String str) {
        String regExp = "^[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证字符串是否为数字或字母或者-
     *
     * @param str
     * @return
     */
    public static boolean checkStringIntegerAndLetterTh(String str) {
        String regExp = "^[A-Za-z0-9-]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 验证字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean checkStringInteger(String str) {
        String regExp = "^[0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 转半角的函数(DBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input 任意字符串
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 双重判断，从map中取对应的value
     *
     * @param target
     * @param map
     * @return
     */
    public static String getValueFromMap(String target, Map<String, String> map) {
        String value = "";
        if (StringUtils.isBlank(target.trim())) {
            return value;
        }
        for (String key : map.keySet()) {
            if (map.get(key).equals(target)) {
                return map.get(key);
            }
        }
        for (String key : map.keySet()) {
            if (key.contains(target)) {
                return map.get(key);
            }
        }
        return value;
    }

    public static Map getAppMap() {
        List<Map> appsList = new ArrayList<>();
        String apps = PlatApplicationContext.getProperty("eplat.imc.fc.defaultSyncAppList");
        String[] appArray = apps.split(",");
        List<String> paramList = new ArrayList();
        for (String app : appArray) {
            paramList.add(app.toUpperCase());
        }
        Map param = new HashMap();
        param.put("appList", paramList);
        if (paramList.size() > 0) {
            Dao dao = DaoFactory.getPlatSqlDao();
            appsList = dao.query("FS00.queryAppsName", param);
        }
        Map returnMap = new HashMap();

        for (Map m : appsList) {
            returnMap.put(m.get("text"), m.get("value"));
        }

        return returnMap;
    }

    public static String getIsAuth(String isAuth) {
        if (isAuth.contains("授权")) {
            return "1";
        }
        if (isAuth.contains("不授权")) {
            return "0";
        }
        return null;
    }

    /**
     * 校验数据长度
     * TODO 该方法只适用于DB2数据库
     *
     * @param data      数据
     * @param tableName 表名
     * @return
     */
    public static String checkData(Map data, String tableName) {
        //获取数据库字段
        String sql = "SELECT COLTYPE as \"coltype\", LENGTH as \"length\", LOWER(NAME) as \"name\", REMARKS as \"remarks\"\n" +
                "        FROM SYSIBM.SYSCOLUMNS" +
                "        WHERE TBNAME = '" + tableName + "'";
        List<Map> list = sqlDao.queryForList(sql);
        //将数据库字段转换为驼峰命名
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            String input = map.get("name").toString();
            String[] parts = input.toLowerCase().split("_");
            StringBuilder output = new StringBuilder(parts[0].toLowerCase());
            for (int i2 = 1; i2 < parts.length; i2++) {
                output.append(Character.toUpperCase(parts[i2].charAt(0))).append(parts[i2].substring(1).toLowerCase());
            }
            String result = output.toString();
            list.get(i).put("name", result);
        }
        StringBuffer str = new StringBuffer();
        //校验数据长度
        data.forEach((k, v) -> {
            try {
                for (int i = 0; i < list.size(); i++) {
                    //获取数据库字段
                    Map map = list.get(i);
                    //判断字段是否存在
                    if (MapUtils.getString(map, "name", "").equals(String.valueOf(k))) {
                        //判断字段类型是否为varchar
                        if (MapUtils.getString(map, "coltype", "").trim().equals("VARCHAR")) {
                            //判断字段长度是否超过数据库字段长度
                            if (StringUtils.isNotBlank(String.valueOf(v)) &&
                                    String.valueOf(v).toString().getBytes("gbk").length > Integer.parseInt(MapUtils.getString(map, "length", ""))) {
                                String length = String.valueOf(map.get("length"));
                                str.append("字段[").append(map.get("remarks")).append("]长度超过").append(length).append("(").append(length).append("个英文，").append(Integer.parseInt(length) / 2).append("个中文)，请检查！").append("\r\n");
                            }
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        return str.toString();
    }

    public static String DateTurnWeek(String customDateStr) {
        String dayOfWeek;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
            Date customDate = inputFormat.parse(customDateStr);
            // 获取星期几
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            dayOfWeek = dayFormat.format(customDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dayOfWeek.replace("星期", "周");
    }

}
