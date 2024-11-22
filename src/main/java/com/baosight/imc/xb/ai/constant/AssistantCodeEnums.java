package com.baosight.imc.xb.ai.constant;

import lombok.Getter;
import lombok.Setter;

public enum AssistantCodeEnums {

    INTELLIGENCE_INSIGHT("1", "dialog@1851139401251282945", "市场情报洞察"),
    REPORT("2", "dialog@1851794981867479041", "订阅搜索"),
    QUOTATION("3", "dialog@1844274746272223233", "厚板智能报价"),
    FUNCTION_RECOMMEND("4", "dialog@1844641710056378369", "功能推荐"),
    DATA_QUERY("5", "dialog@1856535366737059841", "数据查询"),
    ORDER_CARD("6", "dialog@1851130818660917249", "交互式订货"),
    ;

    @Getter
    @Setter
    private String intention;

    @Getter
    @Setter
    private String assistantCode;

    @Getter
    @Setter
    private String name;

    AssistantCodeEnums(String intention, String assistantCode, String name) {
        this.intention = intention;
        this.assistantCode = assistantCode;
        this.name = name;
    }

    public static String getCodeByIntention(String intention) {
        if (intention == null) {
            return null;
        }
        for (AssistantCodeEnums value : AssistantCodeEnums.values()) {
            if (value.getIntention().equals(intention)) {
                return value.getAssistantCode();
            }
        }
        return null;
    }

}
