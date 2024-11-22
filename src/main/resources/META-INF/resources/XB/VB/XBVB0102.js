$(function () {
    /**
     * 查询
     */
    $("#QUERY").on("click", function (e) {
        /*var segNo = $("#inqu_status-0-segNo").val();
        if (segNo != null && segNo != "") {*/
            resultGrid.dataSource.page(1);
            var btnNode = $(this);
            //禁用按钮
            btnNode.attr("disabled", true);
            var ei = new EiInfo();
            ei.setByNodeObject(document.body);
            EiCommunicator.send("XBVB0102", "query", ei, {
                onSuccess: function (ei) {
                    //释放禁用按钮
                    btnNode.attr("disabled", false);
                }, onFail: function (ei) {
                    //释放禁用按钮
                    btnNode.attr("disabled", false);
                }
            });
       /* } else {
            IPLAT.alert("请选择业务单元代码！");
        }*/
    });


    IPLATUI.EFGrid = {

        "result": {
            columns: [
                {
                    field: "manage",
                    title: "操作",
                    template: '<a href=\'#\'>查看</a>',
                    width: 50,
                },
            ],


            dataBinding: function (e) {
                if (e.items != undefined) {
                    for (var i = 0, length = e.items.length; i < length; i++) {
                        if (isAvailable(e.items[i].actualWeight)) {
                            e.items[i].actualWeight = parseFloat(e.items[i].actualWeight);
                        }
                    }
                }
            },

            /**
             * EFGrid渲染成功的回调事件
             */
            loadComplete: function (grid) {

            }
        },


        onCellClick: function (e) {
            if(e.field === "operation"&&!e.model.isNew()){
                rowIndex = e.row;
                var model = e.model;
                imgDialogWindow.open().center();

            }

        },
    }
    IPLATUI.EFWindow = {
        //业务单元代码
        "unitInfo": {
            // 关闭窗口事件
            close: function (e) {
                var $iframe = unitInfoWindow.element.children("iframe");
                // 子窗口中的jQuery对象
                var iframejQuery = $iframe[0].contentWindow.$;

                // $iframe[0].contentWindow.resultGrid
                var dataGrid = iframejQuery("#ef_grid_result2").data("kendoGrid");

                // 也可以使用如下的方式获取dataGrid
                var dataGrid = iframejQuery.data($iframe.contents().find("#ef_grid_result2")[0], "kendoGrid");

                var row = dataGrid.getCheckedRows();

                if (row.length > 0) {
                    $("#inqu_status-0-unitCode").val(row[0].segNo);
                    $("#inqu_status-0-segNo").val(row[0].segNo);
                    $("#inqu_status-0-segName").val(row[0].segName);
                    dataGrid.unCheckAllRows();
                }
            }
        },
    };
});

