var wns;
var filedS = ["pushMonth", "pushDay", "pushDate", "pushHour"]
$(function () {
    $('#QUERY').on('click', function (e) {
        resultGrid.dataSource.page(1);
    });
    $('#RESET').on('click', function (e) {
        $("#ef_grid_result").data("kendoGrid").showColumn("pushDate");
        $("#ef_grid_result").data("kendoGrid").showColumn("pushMonth");
        $("#ef_grid_result").data("kendoGrid").showColumn("pushDay");
        $("#ef_grid_result").data("kendoGrid").showColumn("pushHour");
    });
    $(document).ready(function () {
        wns = $("#updateAttrPoP");
    });

    IPLATUI.EFGrid = {
        "result": {
            pageable: {
                pageSize: 10, // DataSource设置会覆盖此处设置
                pageSizes: [10, 20, 50, 100, 500, 1000]
            },
            beforeEdit: function (e) {
                var row = e.row;
                //获取当前修改行
                $("#editRow").val(row);
                if (filedS.includes(e.field) && isEmpty(e.model.subscribeFreq)) {
                    NotificationUtil("请先选择订阅频率！", "error");
                    e.preventDefault();
                }
            },
            afterEdit: function (e) {
                if (e.field == "subscribeFreq") {
                    if (e.model.subscribeFreq === "D") {
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushMonth");
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushDay");
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushDate");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushHour");
                    }
                    if (e.model.subscribeFreq === "W") {
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushMonth");
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushDay");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushDate");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushHour");
                    }
                    if (e.model.subscribeFreq === "M") {
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushDate");
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushMonth");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushDay");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushHour");
                    }
                    if (e.model.subscribeFreq === "Y") {
                        $("#ef_grid_result").data("kendoGrid").hideColumn("pushDate");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushMonth");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushDay");
                        $("#ef_grid_result").data("kendoGrid").showColumn("pushHour");
                    }
                }
                var inInfo = new EiInfo();
                if (e.field == "subscribeRevisor") {
                    inInfo.set("subscribeRevisor", e.model.subscribeRevisor);
                    EiCommunicator.send("XBAI03", "getUserInformation", inInfo, {
                        onSuccess: function (response) {
                            if (response.getStatus() == 1) {
                                var row = $("#editRow").val();
                                let dataItems = resultGrid.getDataItems();
                                var model = dataItems[row];
                                resultGrid.setCellValue(model, "subscribeName", response.get("subscribeName"));
                                resultGrid.setCellValue(model, "subscribeSegNo", response.get("subscribeSegNo"));
                                resultGrid.setCellValue(model, "subscribeMobile", response.get("subscribeMobile"));
                                resultGrid.setCellValue(model, "subscribeEmail", response.get("subscribeEmail"));
                            } else {
                                if (response.getMsg() != "授权账号不可为空") {
                                    NotificationUtil(response);
                                    e.preventDefault();
                                    return false;
                                } else {
                                    var row = $("#editRow").val();
                                    let dataItems = resultGrid.getDataItems();
                                    var model = dataItems[row];
                                    resultBGrid.setCellValue(model, "subscribeName", " ");
                                    resultBGrid.setCellValue(model, "subscribeSegNo", " ");
                                    resultBGrid.setCellValue(model, "subscribeMobile", " ");
                                    resultBGrid.setCellValue(model, "subscribeEmail", " ");
                                }
                            }
                        }
                    }, {async: false});
                }
            },
            loadComplete: function (grid) {
                $("#CLIPBOARD").on("click", function (e) {
                    document.getElementById("clipContent").value = "";
                    var content = "";
                    if (IPLAT.Browser.isIE) {
                        content = window.clipboardData.getData("Text");
                    } else {
                        clipWindow.center().open();
                        handleFun = postHandle;
                    }

                    function postHandle(content) {
                        // 如果页面不存在数据会报错
                        if (grid._data !== undefined && grid._data.length != 0) {
                            // 清空checkBox
                            grid.unCheckAllRows();
                        }
                        // 取得剪切板内容
                        if (IPLAT.isAvailable(content)) {
                            var lines = content.split("\r\n");
                            var model = [];
                            for (var i = 0; i < lines.length; i++) {
                                var m = resultGetRow(lines[i], i + 1);
                                m != null && model.push(m);
                            }
                            msg = "";
                            detailMsg = "";
                            grid.addRows(model, false, true);
                        }
                    }

                    if (IPLAT.Browser.isIE) {
                        postHandle(content);
                    }
                });
            },
            onSuccess: function (e) {
                if (e.type !== "read") {
                    let queryInfo = resultGrid.getQueryInfo("inqu_status");
                    queryInfo.set("msg", e.eiInfo.getMsg())
                    queryInfo.set("DetailMsg", e.eiInfo.getDetailMsg())
                    resultGrid.postQuery(queryInfo);
                }
            }
        },
    }

    function resultGetRow(line, i) {
        var idField = resultGrid.dataSource.options.schema.model.id; // 主键field name
        var fields = resultGrid.dataSource.options.schema.model.fields;
        var BizModel = kendo.data.Model.define({
            seqId: idField,
            fields: fields,
        });
        var row = {};
        if (IPLAT.isAvailable(line)) {
            var arrs = line.split("\t");

            if (IPLAT.isAvailable(arrs)) {
                // 定义属性名称和默认值的对象
                var properties = {
                    subscribeInfo: "",
                    subscribeRevisor: "",
                    subscribeName: "",
                    subscribeSegNo: "",
                    subscribeMobile: "",
                    subscribeEmail: " ",
                    subscribeFreq: "",
                    pushDate: "",
                    pushMonth: "",
                    pushDay: "",
                    pushHour: "",
                };

                // 循环遍历 arrs 数组，设置属性值
                for (var j = 0; j < Math.min(arrs.length, Object.keys(properties).length); j++) {
                    var value = arrs[j] !== undefined ? (arrs[j].includes("-") ? arrs[j].split("-")[0] : arrs[j]) : "";
                    var propertyName = Object.keys(properties)[j];
                    properties[propertyName] = value;
                }

                // 将属性值设置到 row 对象中
                for (var key in properties) {
                    row[key] = properties[key];
                }

                row.uuid = "";
                row.recCreator = "";
                row.recCreatorName = "";
                row.recCreateTime = "";
                row.recRevisor = "";
                row.recRevisorName = "";
                row.recReviseTime = "";

                var inInfo = new EiInfo();
                inInfo.set("row", row);
                inInfo.set("line", i);
                var r = new BizModel(row);
                r.dirty = true;
                return r;
            }
        }
    }
});
var handleFun;
var pastedContent;

function isEmpty(obj) {
    return typeof obj === undefined || obj == null || obj === "" || obj === " ";
}

//
document.addEventListener('paste', function (evt) {
    var clipdata = evt.clipboardData || window.clipboardData;
    pastedContent = clipdata.getData('text/plain');
    if (evt.target.id == 'clipContent') {
        clipWindow.close();
        handleFun(pastedContent);
    }
});