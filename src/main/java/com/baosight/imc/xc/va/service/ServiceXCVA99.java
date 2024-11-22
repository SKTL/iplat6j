package com.baosight.imc.xc.va.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;

public class ServiceXCVA99 extends ServiceBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    String schemaName = "";
    String tableName = "";
    String packageName = "";
    public EiInfo paseDDL(EiInfo inInfo) {
        String dataBaseType = inInfo.getString("dataBaseType");
        String DDL = inInfo.getString("ddl");
        packageName = inInfo.getString("packageName");

        StringBuilder beanStr = new StringBuilder();
        StringBuilder xmlStr = new StringBuilder();
        // 列信息提取
        List<String[]> columnInfo = new ArrayList<>();
        switch (dataBaseType) {
            case "dameng":
                damen(columnInfo, DDL);
                break;
            case "oracle":
                oracle(columnInfo, DDL);
                break;
            default:
                break;
        }

        beanStr.append("package "+ packageName+";\r\n" +
                "\r\n" +
                "import com.baosight.iplat4j.core.util.NumberUtils;\r\n" +
                "import java.math.BigDecimal;\r\n" +
                "import com.baosight.iplat4j.core.ei.EiColumn;\r\n" +
                "import com.baosight.iplat4j.core.data.DaoEPBase;\r\n" +
                "import java.util.HashMap;\r\n" +
                "import java.util.Map;\r\n" +
                "import com.baosight.iplat4j.core.util.StringUtils;\n" +
                "public class " + tableName.substring(0, 1) + tableName.toLowerCase().substring(1)
                + " extends DaoEPBase {\r\n" +
                "private static final long serialVersionUID = 1L;\n");
        createP1(columnInfo, beanStr);
        createP2(columnInfo, beanStr);
        createP3(tableName, beanStr);
        createP4(columnInfo, beanStr);
        createP5(columnInfo, beanStr, tableName);
        createP6(columnInfo, beanStr);
        createP7(columnInfo, beanStr);
        createP8(columnInfo, beanStr);
        createXmlP1(tableName, xmlStr);
        createXmlP2(columnInfo, xmlStr);
        createXmlP3(columnInfo, xmlStr);
        createXmlP4(xmlStr);
        createXmlP5(columnInfo, xmlStr);
        createXmlP6(xmlStr);
        createXmlP7(columnInfo, xmlStr);
        inInfo.set("bean", beanStr.toString());
        inInfo.set("xml", xmlStr.toString());
        return inInfo;
    }
    public String damen(List<String[]> columnInfo,String ddl) {
        // 表名提取
        Pattern tablePattern = Pattern.compile("CREATE TABLE ([^\\.]+)\\.([^\\s]+)\\s*\\(");
        Matcher tableMatcher = tablePattern.matcher(ddl);
        if (tableMatcher.find()) {
            schemaName = tableMatcher.group(1);
            tableName = tableMatcher.group(2);
        }

        Pattern columnPattern = Pattern.compile("\\s+(\\w+)\\s+(\\w+)\\((\\d+)(?:,(\\d+))?\\)\\s+(\\w+)?");
        Matcher columnMatcher = columnPattern.matcher(ddl);
        while (columnMatcher.find()) {
            String[] column = new String[5];
            column[0] = columnMatcher.group(1); // 列名
            column[1] = columnMatcher.group(2); // 数据类型
            column[2] = columnMatcher.group(3); // 长度
            column[3] = columnMatcher.group(4) == null ? "" : columnMatcher.group(4); // 精度
            column[4] = columnMatcher.group(5) == null ? "" : columnMatcher.group(5); // 约束
            columnInfo.add(column);
        }

        // 打印结果
        System.out.println("模式名：" + schemaName);
        System.out.println("表名：" + tableName);
        System.out.println("列信息：");
        for (String[] info : columnInfo) {
            System.out.println("  - 列名：" + info[0]);
            System.out.println("  - 数据类型：" + info[1]);
            System.out.println("  - 长度：" + info[2]);
            if (!info[3].isEmpty()) {
                System.out.println("  - 精度：" + info[3]);
            }
            System.out.println("  - 约束：" + info[4]);
        }
        return tableName;
    }
    
    public String oracle(List<String[]> columnInfo,String ddl) {
        // 表名提取
        Pattern tablePattern = Pattern.compile("CREATE TABLE\\s+\"(\\w+)\".\"(\\w+)\"\\s+");
        Matcher tableMatcher = tablePattern.matcher(ddl);
        if (tableMatcher.find()) {
            schemaName = tableMatcher.group(1);
            tableName = tableMatcher.group(2);
        }

        Pattern columnPattern = Pattern.compile("\\s+\"(\\w+)\"\\s+(\\w+)\\((\\d+)(?:,(\\d+))?\\)\\s+(.*)");
        Matcher columnMatcher = columnPattern.matcher(ddl);
        while (columnMatcher.find()) {
            String[] column = new String[5];
            column[0] = columnMatcher.group(1); // 列名
            column[1] = columnMatcher.group(2); // 数据类型
            column[2] = columnMatcher.group(3); // 长度
            column[3] = columnMatcher.group(4) == null ? "" : columnMatcher.group(4); // 精度
            column[4] = columnMatcher.group(5); // 约束
            columnInfo.add(column);
        }

        // 打印结果
        System.out.println("模式名：" + schemaName);
        System.out.println("表名：" + tableName);
        System.out.println("列信息：");
        for (String[] info : columnInfo) {
            System.out.println("  - 列名：" + info[0]);
            System.out.println("  - 数据类型：" + info[1]);
            System.out.println("  - 长度：" + info[2]);
            if (!info[3].isEmpty()) {
                System.out.println("  - 精度：" + info[3]);
            }
            System.out.println("  - 约束：" + info[4]);
        }
        return tableName;
    }

    public void createP1(List<String[]> columnInfo, StringBuilder beanStr) {
        for (String[] info : columnInfo) {
            beanStr.append(
                    "public static final String FIELD_" + info[0] + "=\"" + toCamelCase(info[0]) + "\";\n");
        }
        beanStr.append("\n");
    }

    public void createP2(List<String[]> columnInfo, StringBuilder beanStr) {
        for (String[] info : columnInfo) {
            beanStr.append(
                    "public static final String COL_" + info[0] + "=\"" + info[0] + "\";\n");
        }
        beanStr.append("\n");
    }

    public void createP3(String tableName, StringBuilder beanStr) {
        beanStr.append(
                "public static final String QUERY = \""
                        + tableName.substring(tableName.indexOf(".", 0) + 1).toLowerCase() + ".query\";\n");
        beanStr.append(
                "public static final String COUNT = \""
                        + tableName.substring(tableName.indexOf(".", 0) + 1).toLowerCase() + ".count\";\n");
        beanStr.append(
                "public static final String INSERT = \""
                        + tableName.substring(tableName.indexOf(".", 0) + 1).toLowerCase() + ".insert\";\n");
        beanStr.append(
                "public static final String UPDATE = \""
                        + tableName.substring(tableName.indexOf(".", 0) + 1).toLowerCase() + ".update\";\n");
        beanStr.append(
                "public static final String DELETE = \""
                        + tableName.substring(tableName.indexOf(".", 0) + 1).toLowerCase() + ".delete\";\n");
        beanStr.append("\n");
    }

    public void createP4(List<String[]> columnInfo, StringBuilder beanStr) {
        for (String[] info : columnInfo) {
            if ("VARCHAR2".equals(info[1]) || "VARCHAR".equals(info[1])) {
                    beanStr.append(
                            "private String " + toCamelCase(info[0]) + "= \" \";\n");
                }
            if ("NUMBER".equals(info[1])) {
                if ("0".equals(info[3])) {
                    beanStr.append(
                            "private Integer " + toCamelCase(info[0]) + "=  new Integer(0);\n");
                } else {
                    beanStr.append(
                            "private BigDecimal " + toCamelCase(info[0]) + "=  new BigDecimal(0);\n");
                }
            }
        }
        beanStr.append("\n");
    }
    
    public void createP5(List<String[]> columnInfo, StringBuilder beanStr,String tableName) {
        beanStr.append("/**\r\n" +
                "* initialize the metadata.\r\n" +
                "*/\r\n" + //
                "public void initMetaData() {\r\n" +
                "EiColumn eiColumn;");
        for (String[] info : columnInfo) {
            if ("VARCHAR2".equals(info[1]) || "VARCHAR".equals(info[1])) {
                beanStr.append(
                        "eiColumn = new EiColumn(FIELD_" + info[0] + ");\r\n" +
                                "eiColumn.setFieldLength(" + info[2] + ");\r\n" +
                                "eiMetadata.addMeta(eiColumn);\n");
            }
            if ("NUMBER".equals(info[1])) {
                beanStr.append(
                        "eiColumn = new EiColumn(FIELD_" + info[0] + ");\r\n" +
                                "eiColumn.setType(\"N\");\r\n" +
                                "eiColumn.setScaleLength(" + info[3] + ");\r\n" +
                                "eiColumn.setFieldLength(" + info[2] + ");\r\n" +
                                "eiMetadata.addMeta(eiColumn);\n");
            }
            beanStr.append("\n");
        }
        beanStr.append("}\npublic "+ tableName.substring(0, 1)+ tableName.toLowerCase().substring(1)+"() {\r\n" + //
                        "initMetaData();\r\n" + //
                        "}");
    }
    
    public void createP6(List<String[]> columnInfo, StringBuilder beanStr) {
        for (String[] info : columnInfo) {
            if ("VARCHAR2".equals(info[1]) || "VARCHAR".equals(info[1])) {
                beanStr.append(
                        "public String get"+toCamelCase2(info[0])+"() {\r\n" + //
                        "return this."+ toCamelCase(info[0])+";\r\n" + //
                                "}\n");
                        beanStr.append(
                        "public void set"+toCamelCase2(info[0])+"(String "+ toCamelCase(info[0])+") {\r\n" + //
                        "this."+ toCamelCase(info[0])+" = "+ toCamelCase(info[0])+";\r\n" + //
                        "}\n");
            }
            if ("NUMBER".equals(info[1])) {
                if ("0".equals(info[3])) {
                    beanStr.append(
                            "public Integer get"+toCamelCase2(info[0])+"() {\r\n" + //
                            "return this."+ toCamelCase(info[0])+";\r\n" + //
                                    "}\n");
                    beanStr.append(
                                "public void set"+toCamelCase2(info[0])+"(Integer "+ toCamelCase(info[0])+") {\r\n" + //
                                "this."+ toCamelCase(info[0])+" = "+ toCamelCase(info[0])+";\r\n" + //
                                "}\n");
                } else {
                    beanStr.append(
                            "public BigDecimal get" + toCamelCase2(info[0]) + "() {\r\n" + //
                                    "return this." + toCamelCase(info[0]) + ";\r\n" + //
                                    "}\n");
                    beanStr.append(
                            "public void set" + toCamelCase2(info[0]) + "(BigDecimal " + toCamelCase(info[0]) + ") {\r\n" + //
                                    "this." + toCamelCase(info[0]) + " = " + toCamelCase(info[0]) + ";\r\n" + //
                                    "}\n");
                }
            }
        }
        beanStr.append("\n");
    }

    public void createP7(List<String[]> columnInfo, StringBuilder beanStr) {
        beanStr.append("@Override\r\n" + //
                "public void fromMap(Map map) {\n");
        for (String[] info : columnInfo) {
            if ("VARCHAR2".equals(info[1]) || "VARCHAR".equals(info[1])) {
                beanStr.append(
                        "set" + toCamelCase2(info[0])
                                + "(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_" + info[0] + ")), "
                                + toCamelCase(info[0]) + "));\n");
            }
            if ("NUMBER".equals(info[1])) {
                if ("0".equals(info[3])) {
                    beanStr.append(
                            "set" + toCamelCase2(info[0]) + "(NumberUtils.toInteger(StringUtils.toString(map.get(FIELD_"
                                    + info[0] + ")), " + toCamelCase(info[0]) + "));\n");
                } else {
                    beanStr.append(
                            "set" + toCamelCase2(info[0])
                                    + "(NumberUtils.toBigDecimal(StringUtils.toString(map.get(FIELD_"
                                    + info[0] + ")), " + toCamelCase(info[0]) + "));\n");
                }
            }
        }
        beanStr.append("}\n");
    }
    
    public void createP8(List<String[]> columnInfo, StringBuilder beanStr) {
        beanStr.append("@Override\r\n" + //
                "public Map toMap() {\nMap map = new HashMap();\n");
        for (String[] info : columnInfo) {
            beanStr.append(
                    "map.put(FIELD_" + info[0] + ", StringUtils.toString(" + toCamelCase(info[0])
                            + ", eiMetadata.getMeta(FIELD_" + info[0] + ")));\n");

        }
        beanStr.append("return map;\n}\n}");
    }
    
    public void createXmlP1(String tableName, StringBuilder xmlStr) {
        xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + //
                "<!DOCTYPE sqlMap  PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n");
        xmlStr.append("<sqlMap namespace=\"" + tableName.toLowerCase() + "\">");
    }
    
    public void createXmlP2(List<String[]> columnInfo, StringBuilder xmlStr) {
        xmlStr.append("<sql id=\"condition\">");

        for (String[] info : columnInfo) {
            xmlStr.append(
                    "<isNotEmpty prepend=\" AND \" property=\"" + toCamelCase(info[0]) + "\">\r\n" + //
                            "            " + info[0] + " = #" + toCamelCase(info[0]) + "#\r\n" + //
                            "</isNotEmpty>\n");
        }
        xmlStr.append("</sql>\n");
    }
    
    public void createXmlP3(List<String[]> columnInfo, StringBuilder xmlStr) {
        xmlStr.append("<select id=\"query\" parameterClass=\"java.util.HashMap\"\r\n" + //
                "resultClass=\""+packageName+ "."+tableName.substring(0, 1) + tableName.toLowerCase().substring(1) +"\">\r\n" + //
                "        SELECT\n");

        for (String[] info : columnInfo) {
            xmlStr.append(
                    "            "+info[0] + " as \"" + toCamelCase(info[0]) + "\",\n");
        }
        xmlStr.deleteCharAt(xmlStr.lastIndexOf(","));
        xmlStr.append("          FROM " + schemaName + "." + tableName + " WHERE 1=1\r\n" + //
                "<include refid=\"condition\" />\r\n" + //
                "<dynamic prepend=\"ORDER BY\">\r\n" + //
                "<isNotEmpty property=\"orderBy\">\r\n" + //
                "              $orderBy$\r\n" + //
                "</isNotEmpty>\r\n" + //
                "</dynamic>\r\n" + //
                "</select>\n");
    }
    
    public void createXmlP4(StringBuilder xmlStr) {
        xmlStr.append("\r\n" + //
                "    <select id=\"count\" resultClass=\"int\">\r\n" + //
                "        SELECT COUNT(*) FROM " + schemaName + "." + tableName + " WHERE 1=1\r\n" + //
                "        <include refid=\"condition\" />\r\n" + //
                "    </select>");
    }
    
    public void createXmlP5(List<String[]> columnInfo, StringBuilder xmlStr) {
        xmlStr.append("<insert id=\"insert\">\r\n" + //
                "        INSERT INTO " + schemaName + "." + tableName + " (\n");

        for (String[] info : columnInfo) {
            xmlStr.append(
                    "            "+info[0] + ",\n");
        }
        xmlStr.deleteCharAt(xmlStr.lastIndexOf(","));
        xmlStr.append("            )\n        VALUES (\n");
        for (String[] info : columnInfo) {
            if ("VARCHAR2".equals(info[1]) || "VARCHAR".equals(info[1])) {
                xmlStr.append("            #" + toCamelCase(info[0]) + ":VARCHAR#,\n");
            }
            if ("NUMBER".equals(info[1])) {
                xmlStr.append("            #" + toCamelCase(info[0]) + ":NUMERIC#,\n");
            }
        }
        xmlStr.deleteCharAt(xmlStr.lastIndexOf(","));
        xmlStr.append(")\n</insert>\n");
    }
    
    public void createXmlP6(StringBuilder xmlStr) {
        xmlStr.append("    <delete id=\"delete\">\r\n" + //
                "        DELETE FROM " + schemaName + "." + tableName + " WHERE 1=1\r\n" + //
                "    </delete>\n");
    }
    
    public void createXmlP7(List<String[]> columnInfo, StringBuilder xmlStr) {
        xmlStr.append("<update id=\"update\">\r\n" + //
                        "        UPDATE "+schemaName+"."+tableName+"\r\n" + //
                        "        SET\n");

        for (String[] info : columnInfo) {
            if ("VARCHAR2".equals(info[1]) || "VARCHAR".equals(info[1])) {
                xmlStr.append("        "+info[0] + "    = #" + toCamelCase(info[0]) + "#, \n");
            }
            if ("NUMBER".equals(info[1])) {
                xmlStr.append("        "+info[0] + "    = #" + toCamelCase(info[0]) + ":NUMERIC#, \n");
            }
        }
        xmlStr.deleteCharAt(xmlStr.lastIndexOf(","));
        xmlStr.append("        WHERE 1=1\r\n" + //
                        "    </update>\n</sqlMap>");
    }
    public String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
    
    public String toCamelCase2(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext || i == 0) {
                sb.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

}
