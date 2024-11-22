$(function () {

    IPLATUI.EFGrid = {
        "result2": {
            /*
            * 列头悬浮固定
            * */
            isFloat: true,
            /*
            * 工具类分页
            * */
            pageable: {
                pageSize: 20,
                pageSizes: [20, 50, 100, 300, 500]
            },
            /**
             * EFGrid新增后触发的事件
             */
            afterAdd: function (e) {
                $.each(e.items, function (index, item) {
                    item.internalCode = "";
                });
            },
            loadComplete: function (grid) {
                $("#QUERY").on("click", function () {
                    result2Grid.dataSource.page(1);
                });
            },
            //双击选中
            onRowDblClick: function (e) {
                var windowId = $("#inqu2_status-0-windowId").val();
                if (IPLAT.isBlankString(windowId)) {
                    // 设置默认值
                    windowId = "unitInfo";
                }
                //双击选中前先把双击的数据勾选上
                result2Grid.setCheckedRows(e.row);
                //关闭下拉框
                window.parent[windowId + "Window"].close();
            }
        }
    };
});
