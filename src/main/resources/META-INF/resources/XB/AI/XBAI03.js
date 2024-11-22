var wns;
var filedS = ["pushMonth", "pushDay", "pushDate", "pushHour"]
$(function () {
    $('#QUERY').on('click', function (e) {
        resultGrid.dataSource.page(1);
    });
    $("#QUERY_USERB").on("click", function () {
        resultUserGrid.dataSource.page(1);
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
            }, columns: [
                {
                    field: "apprResume",
                    title: "发送履历",
                    template: '<button onclick="openNodeDetail(\'#:uuid#\')" id="upload" type="button" class="i-btn-sm">发送履历</button>',
                    width: 100,
                    readonly: true,
                    align: "center",
                    required: true
                },
            ],
            beforeEdit: function (e) {
                var row = e.row;
                //获取当前修改行
                $("#editRow").val(row);
                if (filedS.includes(e.field) && isEmpty(e.model.subscribeFreq)) {
                    NotificationUtil("请先选择订阅频率！", "error");
                    e.preventDefault();
                }
                if (e.model.subscribeFreq === "D") {
                    if (e.field == "pushMonth" || e.field == "pushDay" || e.field == "pushDate") {
                        NotificationUtil("按天推送只需选择[时]！", "error");
                        e.preventDefault();
                    }
                }
                if (e.model.subscribeFreq === "W") {
                    if (e.field == "pushMonth" || e.field == "pushDay") {
                        NotificationUtil("按周推送只需选择[周，时]！", "error");
                        e.preventDefault();
                    }
                }
                if (e.model.subscribeFreq === "M") {
                    if (e.field == "pushHour" || e.field == "pushDay") {
                        NotificationUtil("按月推送只需选择[日，时]！", "error");
                        e.preventDefault();
                    }
                }
                if (e.model.subscribeFreq === "Y") {
                    if (e.field == "pushHour" || e.field == "pushDay" || e.field == "pushMonth") {
                        NotificationUtil("按年推送只需选择[月，日，时]！", "error");
                        e.preventDefault();
                    }
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
                                resultGrid.setCellValue(model, "subscribeSegNoTemp", response.get("subscribeSegNoTemp"));
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
                                    resultBGrid.setCellValue(model, "subscribeSegNoTemp", " ");
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
        "resultUser": {
            /**
             * 双击数据行时触发的事件，注意编辑状态时不会触发
             * @param e
             * e.sender     kendoGrid对象，resultGrid
             * e.model      双击的行数据，kendo.data.Model
             * e.row        当前行的行号
             * e.tr         行的tr,包括固定列和数据列 jquery对象
             */
            onRowDblClick: function (e) {
                var empNo = e.model.subscribeRevisor;//提示编号
                var name = e.model.subscribeName;//提示编号
                var subscribeSegNo = e.model.subscribeSegNo;//提示编号
                var subscribeSegNoTemp = e.model.subscribeSegNoTemp;//提示编号
                var e_mail = e.model.subscribeEmail;//提示编号
                var teleNum = e.model.subscribeMobile;//提示编号
                var row = $("#editRow").val();
                let dataItems = resultGrid.getDataItems();
                var model = dataItems[row];
                model.set("subscribeRevisor", empNo);
                model.set("subscribeName", name);
                model.set("subscribeSegNo", subscribeSegNo);
                model.set("subscribeEmail", e_mail);
                model.set("subscribeMobile", teleNum);
                model.set("subscribeSegNoTemp", subscribeSegNoTemp);
                var popupGridWindow = $("#queryEmpCode").data("kendoWindow");
                // 关闭window
                popupGridWindow.close();
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

function openNodeDetail(uuid) {
    var uuid = encodeURI(encodeURI(uuid));
    //设置url
    var url = IPLATUI.CONTEXT_PATH + "/web/XBAI0301?methodName=query&inqu_status-0-parentUuid=" + uuid;
    var popWindow = $("#window");
    popWindow.data("kendoWindow").setOptions({
        open: function () {
            popWindow.data("kendoWindow").refresh({
                url: url
            });
        },
        iframe: true
    });
    popWindow.data("kendoWindow").content("");
    popWindow.data("kendoWindow").center().open();
}