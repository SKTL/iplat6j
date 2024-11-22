$(function () {
    $('#QUERY').on('click', function (e) {
        resultGrid.dataSource.page(1);
        resultBGrid.removeRows(resultBGrid.getDataItems());
    });
    IPLATUI.EFGrid = {
        "result": {
            onCellClick: function (e) {
                $("#inqub_status-0-sessionId").val(e.model.sessionId);
                resultBGrid.dataSource.page(1);
            }
        },
        "resultB": {
            columns: [
                {
                    field: "evaluateId",
                    template: function (data) {
                        let s = "";
                        let evaluateId = data.evaluateId;
                        // 如果 evaluateId 有值，继续处理
                        if (evaluateId) {
                            // 将 evaluateId 拆分成数组并映射每个值
                            let splits = evaluateId.split(",");
                            s = splits.map(split => {
                                // 根据条件选择对应的内容
                                if ("2000" === split || "1000" === split) {
                                    return split + "-" + data.evaluateContent;
                                } else {
                                    return split + "-" + __ei.evaluateMap[split]; // 如果没找到映射的值，返回空字符串
                                }
                            }).join("；"); // 将结果数组连接成字符串
                        }
                        return s;
                    }
                },
                {
                    field: "content",
                    template: function (data) {
                        if (!isEmpty(data.content)) {
                            let rawText = JSON.parse(data.content);
                            if ("1" === data.role) {
                                return rawText.content;
                            } else {
                                return getFirstValidContent(rawText);
                            }
                        } else {
                            return "";
                        }
                    }
                }
            ],
        }
    }

});

function isEmpty(obj) {
    return typeof obj === undefined || obj == null || obj === "" || obj === " ";
}

function getFirstValidContent(data) {
    // 遍历contents数组，优先选择text类型的内容
    for (let item of data.contents) {
        if (item.contentType === 'text') {
            return item.content;
        }
    }
    // 如果没有找到text类型，查找output类型
    for (let item of data.contents) {
        if (item.contentType === 'output') {
            return item.content.content;
        }
    }
    // 如果没有找到text或output类型，查找skill类型
    for (let item of data.contents) {
        if (item.contentType === 'skill') {
            return item.content;
        }
    }
    // 如果没有找到任何有效内容，返回null
    return null;
}