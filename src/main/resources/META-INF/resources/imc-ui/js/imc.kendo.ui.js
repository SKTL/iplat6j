/**
 * 构建工具栏和按钮
 */
(function ($) {
    var EiConstant = IPLAT.EiConstant,
        EiInfo = IPLAT.EiInfo,
        WindowUtil = IPLAT.WindowUtil,
        TEXT = IPLATUI.Config.Layout.TEXT,
        ICON = IPLATUI.Config.Layout.ICON,
        ICON_AND_TEXT = IPLATUI.Config.Layout.ICON_AND_TEXT,
        gridDefaultButtons = [],

        NS = ".IPLAT",
        ON_DELETE = "onDelete",
        SAVECHANGES="saveChanges",
        DELETE_ACTION = IPLAT.EFGrid.DELETE_ACTION, // 点击删除按钮时，记录删除操作
        CREATE_ACTION=IPLAT.EFGrid.CREATE_ACTION,
        UPDATE_ACTION=IPLAT.EFGrid.UPDATE_ACTION,

        isArray = $.isArray,
        each = $.each,
        extend = $.extend;

    function ismobile() {//移动端适配
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/(iPhone|iPod|Android|ios)/i) || ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        }
        else {
            return false;
        }
    }

    var flg = ismobile();
    /**
     * 初始化工具栏信息
     *
     * STEP1 处理buttons，包括 EDFA01注册的按钮，默认的按钮，js中定义的按钮
     * STEP2 处理Grid右侧Setting下拉菜单中的逻辑，包括：个性化显示列，导出数据等
     * STEP3 处理翻页条在Grid上方的逻辑
     *
     * @param options
     * @private
     */
    IPLAT.EFGrid._initToolbar = function (options) {
        var gridConfig = options.gridConfig,
            needAuth = options.needAuth;

        var toolbarConfig = extend(true, {
            "add": true,
            "cancel": true,
            "save": true,
            "delete": true,
            "insertsave":true,
            "updatesave":true
        }, options.toolbarConfig, gridConfig.toolbarConfig);

        delete gridConfig.toolbarConfig; // 去除页面上的toolbarConfig，防止被再次继承

        if (!isArray(toolbarConfig.buttons)) {
            toolbarConfig.buttons = [];
        }
        if (!isArray(toolbarConfig.default_layout)) {
            toolbarConfig.default_layout = [];
        }


        /*******************************************************
         *
         * STEP1    处理buttons，包括 EDFA01注册的按钮，默认的按钮
         *          js中定义的按钮
         *
         *******************************************************/

        // 1. 权限配置优先级最高;
        // 2. 判断IPLATUI中的配置; 在工具栏上追加按钮，覆盖原有的按钮功能
        // 3. 判断EFGrid Tag中的配置;

        if (needAuth) {
            gridDefaultButtons = [];
            // __eiInfo 当前页面上的EiInfo
            // TODO: needAuth = false的时候，注册的自定义按钮iframe GRID嵌入等隐患

            var btnDescInfo, efFormButtonDesc = __eiInfo.getAttr()[EiConstant.EF_FORM_BUTTON_DESC];

            if (IPLAT.isString(efFormButtonDesc)) {
                btnDescInfo = EiInfo.parseJSONString(efFormButtonDesc);
            } else if ($.isPlainObject(efFormButtonDesc)) {
                btnDescInfo = EiInfo.parseJSONObject(efFormButtonDesc);
            }

            // var btnDescInfo = EiInfo.parseJSONString(__eiInfo.getAttr()[EiConstant.EF_FORM_BUTTON_DESC]);

            var filteredBlockIds = _.filter(_.keys(btnDescInfo.getBlocks()), function (gridId) {
                // 后台GRID信息注册不规范，导致多个block出现
                return gridId.trim().toUpperCase() === "GRID:" + options.gridId.toUpperCase();
            });

            var customButtons = [], // 后台注册的自定义按钮
                gridButtonRegex = /^((INSERT($|_\d+$))|(SAVE($|_\d+$))|(CANCEL($|_\d+$))|(DELETE($|_\d+$))|(INSERTSAVE($|_\d+$))|(UPDATESAVE($|_\d+$)))/;
            // EFGridButtonNames = ["INSERT", "SAVE", "CANCEL", "DELETE", "SETTING"];

            var registerButtonConfig = {};

            each(filteredBlockIds, function (index, blockId) {
                var gridBlock = btnDescInfo.getBlock(blockId);

                for (var i = 0; i < gridBlock.getRows().length; i++) {
                    var buttonId = gridBlock.getCell(i, "button_ename").toString().toUpperCase();

                    if (gridBlock.getCell(i, "button_status").toString() === "1") {
                        // 只维护有权限的按钮信息
                        if (gridButtonRegex.test(buttonId)) {
                            var buttonName = buttonId.split('_')[0]; // INSERT_1
                            buttonName = buttonName === "INSERT" ? "add" : buttonName.toLowerCase();
                            registerButtonConfig[buttonName] = true;

                            gridDefaultButtons.push({
                                name: buttonName,
                                text: gridBlock.getCell(i, "button_cname").toString(),
                                icon: gridBlock.getCell(i, "uri").toString(),
                                layout: gridBlock.getCell(i, "layout").toString()
                            });

                            // toolbarConfig['default_layout'][buttonName] = gridBlock.getCell(i, "layout").toString();
                        } else { // 自定义的按钮
                            customButtons.push({
                                name: buttonId,
                                text: gridBlock.getCell(i, "button_cname").toString(),
                                icon: gridBlock.getCell(i, "uri").toString(),
                                layout: gridBlock.getCell(i, "layout").toString()
                            });
                        }
                    }
                }
            });

            toolbarConfig = _.mapObject(toolbarConfig, function (value, buttonId) {
                if (_.indexOf(_.keys(registerButtonConfig), buttonId) < 0 &&
                    _.indexOf(["add", "cancel", "save", "delete","insertsave","updatesave"], buttonId) > -1) {
                    // Grid的按钮没有注册 永远不会显示
                    return false;
                }
                return value;
            });

            // 注册的按钮在自定义按钮的前面
            toolbarConfig.buttons = customButtons.concat(toolbarConfig.buttons);
        }

        // 此时 toolbarConfig.buttons数组还未创建 toolbarConfig中只有权限信息
        var blockId = options.blockId,
            gridId = options.gridId,
            personalWindowId = "#ef_personal_window_" + blockId,
            kendoToolBarItems = []; // kendoToolBar组件需要的items参数

        // kendo默认的增删改按钮 FIXME name和id混淆使用了
        var defaultToolbar = [
            {
                name: "add",
                text: "新增",
                icon: "css:k-add",
                layout: ICON_AND_TEXT
            },

            {
                name: "save-changes",
                text: "保存",
                icon: "css:k-update",
                layout: ICON_AND_TEXT
            },

            {
                name: "cancel-changes",
                text: "取消",
                icon: "css:k-cancel",
                layout: ICON_AND_TEXT
            },

            {
                name: "delete",
                text: "删除",
                icon: "css:k-delete",
                layout: ICON_AND_TEXT,
                click: function (e) {
                    var gridInstance = $("#" + gridId).data("kendoGrid");

                    var dataSource = gridInstance.dataSource,
                        models = [];

                    if (gridInstance.trigger(ON_DELETE + NS, {event: e})) {
                        return;
                    }

                    each(gridInstance.getCheckedRows(), function (i, model) {
                        models.push(model);
                    });


                    var deleteHTML = kendo.template($("#del-template").html())(
                        {message: "确认删除此数据么?", ok: '确定', cancel: '取消'}
                    );

                    if (models.length > 0) {
                        WindowUtil({
                            title: "删除",
                            content: deleteHTML,
                            ok: function () {
                                var that = this;

                                // 存储当前的删除操作
                                gridInstance._action = DELETE_ACTION;

                                // 仅在删除失败的场景下用于恢复，业务上不能直接使用
                                gridInstance._deleteRows = gridInstance.getCheckedRowsIndex();

                                // 清空选中的列
                                for (var i = 0; i < models.length; i++) {
                                    var index = _.indexOf(gridInstance._checkedRows, models[i].uid);
                                    gridInstance._checkedRows.splice(index, 1);
                                }

                                // Grid暂不刷新，成功或者失败的回调中再刷新
                                gridInstance.removeRows(models, false);

                                dataSource.sync();

                                that.data("kendoWindow").close();
                            },
                            cancel: function () {
                                var that = this;
                                that.data("kendoWindow").close();
                            }
                        });
                    } else {
                        WindowUtil({
                            'title': "删除",
                            "content": "<div class='kendo-del-message'>没有选中的行</div>"
                        });
                    }
                }
            },

            {
                name: "insertsave-changes",
                text: "保存新增",
                icon: "css:k-update",
                layout: ICON_AND_TEXT,
                click: function (e) {
                    //阻止浏览器默认行为
                    e.preventDefault();
                    var gridInstance = $("#" + gridId).data("kendoGrid");
                    var dataSource = gridInstance.dataSource,
                        models = [],
                        row_flag=0,
                        that = this;
                    if ((that.editable && that.editable.end() || !that.editable) && !gridInstance.trigger(SAVECHANGES)) {
                        if (gridInstance.trigger(SAVECHANGES + NS, {event: e})) {
                            return;
                        }
                        each(gridInstance.getCheckedRows(), function (i, model) {
                            if(row_flag===0){
                                if(model.id===''){
                                    models.push(model);
                                }else {
                                    row_flag=i+1;
                                }
                            }
                        });
                        if(row_flag){
                            WindowUtil({
                                'title': "新增记录",
                                "content": "<div class='kendo-del-message'>第 "+row_flag+" 行已勾选记录为非新增行，请勿勾选！</div>"
                            });
                        }else {
                            if (models.length > 0) {
                                // 存储当前的新增操作
                                gridInstance._action = CREATE_ACTION;
                                each(gridInstance._data, function (i, model) {
                                    if(model.id!==''){
                                        model.dirty=false;
                                    }
                                });
                                if ((that.editable && that.editable.end() || !that.editable) && !gridInstance.trigger(SAVECHANGES)) {
                                    dataSource.sync();
                                }
                            } else {
                                WindowUtil({
                                    'title': "新增记录",
                                    "content": "<div class='kendo-del-message'>没有选中待新增的行</div>"
                                });
                            }
                        }
                    }
                }
            },

            {
                name: "updatesave-changes",
                text: "保存修改",
                icon: "css:k-update",
                layout: ICON_AND_TEXT,
                click: function (e) {
                    var gridInstance = $("#" + gridId).data("kendoGrid");
                    var dataSource = gridInstance.dataSource,
                        models = [],
                        row_flag=0,
                        that = this;
                    if ((that.editable && that.editable.end() || !that.editable) && !gridInstance.trigger(SAVECHANGES)) {
                        if (gridInstance.trigger(SAVECHANGES + NS, {event: e})) {
                            return;
                        }
                        each(gridInstance.getCheckedRows(), function (i, model) {
                            if(row_flag===0){
                                if(model.id!==''){
                                    if(model.dirty){
                                        models.push(model);
                                    }
                                }else{
                                    row_flag=i+1;
                                }
                            }
                        });
                        if(row_flag){
                            WindowUtil({
                                'title': "修改记录",
                                "content": "<div class='kendo-del-message'>第 "+row_flag+" 行已勾选记录为非修改行，请勿勾选！</div>"
                            });
                        }else {
                            if (models.length > 0) {
                                var that = this;
                                // 存储当前的修改操作
                                gridInstance._action = UPDATE_ACTION;
                                dataSource.sync();
                            } else {
                                WindowUtil({
                                    'title': "修改记录",
                                    "content": "<div class='kendo-del-message'>没有选中待修改的行</div>"
                                });
                            }
                        }
                    }
                }
            }
        ];

        // 防止js中自定义的按钮覆盖了grid 默认的4个按钮
        var _configButton = function (i, buttonName) {
            var button = defaultToolbar[i];

            if (isArray(toolbarConfig.buttons)) {
                var index = _.findIndex(toolbarConfig.buttons, function (e) {
                    return e.name === buttonName;
                });

                if (index > -1) {
                    button = extend(button,
                        toolbarConfig.buttons[index]
                    );
                    toolbarConfig.buttons.splice(index, 1);
                }
            }
            return button;
        };

        // 平台使用的按钮对象 非kendoToolBar所需的items
        var toolbarButtons = [];

        // 读取toolbarConfig中Grid的默认四个按钮的信息
        each(["add", "save-changes", "cancel-changes", "delete","insertsave-changes","updatesave-changes"], function (i, buttonName) {
            var buttonId = buttonName.split("-")[0];
            if (toolbarConfig[buttonId]) {
                var registerBtn = _.find(gridDefaultButtons, {name: buttonId}),
                    defaultBtn = _configButton(i, buttonName); // 去除buttons数组中和默认按钮name重复的元素
                if (registerBtn) {
                    if(flg){
                        toolbarButtons.push(defaultBtn);
                    }else {
                        registerBtn = extend({}, defaultToolbar[i], registerBtn);
                        registerBtn.name = buttonName;
                        toolbarButtons.push(registerBtn);
                    }
                } else {
                    toolbarButtons.push(defaultBtn);
                }

            }
        });

        if (isArray(toolbarConfig.buttons)) {
            toolbarButtons = toolbarButtons.concat(toolbarConfig.buttons);
        }

        toolbarConfig.buttons = toolbarButtons;
        var menuButtons=[]
        // 按钮图标文字显示控制 并转换成kendoToolBar所需的items
        each(toolbarButtons, function (index, buttonOptions) {
            var btnLayout = buttonOptions['layout'] || TEXT,
                btnSpan = "",
                btnText = "<span>" + (buttonOptions['text'] || "") + "</span>";

            var btnClass = IPLAT.Util.parseBtnClass(buttonOptions['icon']),
                iconCss = btnClass.css,
                btnCss = btnClass.btnClass;

            if (btnLayout !== TEXT) { // 显示图标
                if (btnLayout === ICON) {
                    iconCss = iconCss + " i-btn-only-icon";
                    btnText = "";
                }
                btnSpan = "<span class='" + iconCss + "'></span>";
            }

            var button = {
                text:btnText,
                type: "button",
                attributes: extend(buttonOptions["attributes"], {
                    "class": "kendo-xplat-" + buttonOptions.name + " xplat-float-right",
                    "id": buttonOptions.name
                }),
                template: kendo.template("<button class='i-btn-lg " + btnCss +
                    " k-grid-" + buttonOptions.name + "' type='button'>" + btnSpan + btnText + "</button>")
            };
            if(flg){
                btnSpan= "<span class='k-icon " + buttonOptions.icon.slice(4)+ "'></span>";
                if (buttonOptions.icon.slice(4) == "" ){
                    btnSpan= "<span class='sl-icon fa fa-map-o'></span>";
                }
                if(buttonOptions.icon.length==1){
                    btnSpan= "<span class='fa fa-file-o' style='padding-right: 4px'></span>";
                }
                button.attributes= extend(buttonOptions["attributes"], {
                    "class": "kendo-xplat-" + buttonOptions.name + " xplat-float-right",
                    "id": buttonOptions.name,
                    "style":"min-width: 24px"
                });
                button.template= kendo.template("<button class='i-btn-lg " + btnCss +
                    " k-grid-" + buttonOptions.name + "' type='button' style='min-width:24px; padding:2px 0px 2px 5px;'>" + btnSpan  + "</button>");
            };
            kendoToolBarItems.push(button);
        });

        /*******************************************************
         *
         * STEP1.1    按照按钮注册顺序，调整按钮渲染顺序
         *
         *******************************************************/
        if(needAuth){
            //新建一个新的kendoToolBarItems列表，用于存放顺序
            var kendoToolBarItemsIdMap=new Map;
            for(var i = 0; i < kendoToolBarItems.length; i++){
                let Item_id=kendoToolBarItems[i].attributes.id;
                let Btn_id=Item_id.split('-')[0];
                let ButtonName = Btn_id === "add" ? "INSERT" : Btn_id.toUpperCase();
                kendoToolBarItemsIdMap.set(ButtonName,i);
            }

            var kendoToolBarItemsCustomOrderList=[];
            each(filteredBlockIds, function (index, blockId) {
                var gridBlock = btnDescInfo.getBlock(blockId);

                for (var i = 0; i < gridBlock.getRows().length; i++) {
                    let buttonId = gridBlock.getCell(i, "button_ename").toString().toUpperCase();
                    if (gridButtonRegex.test(buttonId)) {
                        buttonId=buttonId.split('_')[0];
                    }
                    let index=kendoToolBarItemsIdMap.get(buttonId);
                    if(index!=null){
                        if(buttonId==="INSERT"){
                            kendoToolBarItemsCustomOrderList.unshift(kendoToolBarItems[index]);
                        }else {
                            kendoToolBarItemsCustomOrderList.push(kendoToolBarItems[index]);
                        }
                    }
                }
            });

            //替换排序后的kendoToolBarItems
            kendoToolBarItems=kendoToolBarItemsCustomOrderList;
        }


        /*******************************************************
         *
         * STEP2    处理Grid右侧Setting下拉菜单中的逻辑，包括：
         *          个性化显示列，导出数据等
         *
         *******************************************************/

        var _buildGridSettings = function (options) {
            var defaultActions = [];
            if (options.personal) { // 自定义列的顺序和显示
                defaultActions.push({
                    name: "personalGrid_" + blockId,
                    text: "自定义数据列",
                    click: function () {
                        var personalWindow = $(personalWindowId).data("kendoWindow");
                        personalWindow.center().open();
                    }
                });
            }
            // grid的配置项覆盖config配置项
            var tempExportGrid = extend({}, options, options.gridConfig);
            var exportGrid = tempExportGrid.exportGrid;


            if (exportGrid) { // 开启了grid的导出功能
                var exportOptions = {};
                var exportEiInfo = new EiInfo("");
                var exportMode = exportGrid.exportMode;
                var exportServiceName = "";
                var exportMethodName = "";

                exportOptions["exportMode"] = exportMode;
                exportOptions["frontExport"] = true;
                exportOptions["exportFileType"] = "xls";
                exportOptions["exportBlockId"] = blockId;
                exportOptions["exportFileName"] = exportGrid.exportFileName || blockId;

                if ($.isPlainObject(exportGrid)) { // Grid导出的配置项解析
                    exportOptions["exportFileType"] =
                        exportGrid.exportFileType || "xls";

                    exportOptions["exportBlockId"] =
                        exportGrid.exportBlockId || blockId;

                    if (isAvailable(exportGrid.exportServiceName) && isAvailable(exportGrid.exportMethodName)) {
                        exportServiceName = exportGrid.exportServiceName;
                        exportMethodName = exportGrid.exportMethodName;
                        if (exportMode === "front" || typeof (exportMode) == "undefined") {
                            exportOptions["frontExport"] = false; // 后台导出的标志
                        }
                    }
                }
                if (exportGrid.frontExportSettings === undefined){
                    defaultActions.push({
                        name: "exportGrid_" + blockId,
                        text: "前端导出",
                        click: function () {
                            var gridInstance = $("#" + gridId).data("kendoGrid");
                            var doExport = true;

                            if ($.isFunction(exportGrid.beforeExport)) { // 导出数据前的事件
                                doExport = exportGrid.beforeExport(gridInstance); // 业务上判断是否导出
                            }

                            if (doExport) { // 执行导出逻辑
                                // 配置导出文件名
                                if ($.isFunction(exportGrid.exportFileName)) {
                                    // 注意函数每次点击都会被调用，例如导出文件名需要设置时间戳
                                    exportOptions["exportFileName"] = exportGrid.exportFileName(gridInstance);
                                }

                                if (exportOptions["frontExport"]) {
                                    if (exportOptions.exportServiceName !== undefined){
                                        delete exportOptions.exportServiceName;
                                    }
                                    if (exportOptions.exportMethodName !== undefined){
                                        delete exportOptions.exportMethodName;
                                    }

                                    // 前端导出
                                    if ($.isFunction(exportGrid.exportEiInfo)) {
                                        // 注意exportEiInfo函数每次点击都会被调用
                                        exportEiInfo = exportGrid.exportEiInfo(gridInstance);

                                    } else if (IPLAT.isEiInfo(exportGrid.exportEiInfo)) {
                                        exportEiInfo = exportGrid.exportEiInfo;
                                    } else {
                                        // 默认为导出当前页，前端导出
                                        // TODO: 1.指明导出的列；2.指明不导出的列；3.导出显示的值（例如下拉选项的中文内容）
                                        // exportEiInfo = gridInstance.wrapEiBlock(null);
                                        exportEiInfo = gridInstance.getDisplayEiInfo(exportOptions["exportBlockId"]);
                                    }

                                    // 根据指明的导出列/不导出列 过滤exportEiInfo
                                    var existColumns = _.keys(exportEiInfo.getBlock(blockId).getBlockMeta().getMetas()),
                                        exportColumns = exportGrid['exportColumns'] || existColumns,
                                        unExportColumns = exportGrid['unExportColumns'] || [];

                                    var removedColumns = _.filter(existColumns, function (existColumn) {
                                        return exportColumns.indexOf(existColumn) < 0;
                                    }).concat(
                                        _.filter(unExportColumns, function (unExportColumn) {
                                            return existColumns.indexOf(unExportColumn) > -1;
                                        }));

                                    if (removedColumns.length > 0) {
                                        // 过滤掉不需要导出的列
                                        exportEiInfo.getBlock(blockId).removeColumns(removedColumns);
                                    }

                                    exportOptions["exportEiInfo"] = exportEiInfo.toEncryptedJSONString();
                                } else {
                                    // 使用后台服务，生成要导出的EiInfo
                                    exportOptions["exportServiceName"] = exportServiceName;
                                    exportOptions["exportMethodName"] = exportMethodName;
                                    // 后端导出
                                    // 后台导出时，不使用默认的查询条件，业务上提供自定义的查询条件
                                    if ($.isFunction(exportGrid.queryEiInfo)) {
                                        exportOptions["queryInfo"] = exportGrid.queryEiInfo(gridInstance);
                                    } else if (IPLAT.isEiInfo(exportGrid.queryEiInfo)) {
                                        exportOptions["queryInfo"] = exportGrid.queryEiInfo;
                                    } else {
                                        // 后台导出时，使用默认的查询条件
                                        exportOptions["queryInfo"] = gridInstance.getQueryInfo();
                                    }

                                    if (IPLAT.isEiInfo(exportOptions["queryInfo"])) {
                                        exportOptions["queryInfo"] = exportOptions["queryInfo"].toEncryptedJSONString();
                                    }
                                }
                                IPLAT.Util.exportGrid(exportOptions);
                            } // doExport if结束
                        } // click函数结束
                    });
                }else {
                    if (exportGrid.frontExportSettings.isShow !== false){
                        defaultActions.push({
                            name: "exportGrid_" + blockId,
                            text: exportGrid.frontExportSettings.name === undefined ? "前端导出" : exportGrid.frontExportSettings.name,
                            click: function () {
                                var gridInstance = $("#" + gridId).data("kendoGrid");
                                var doExport = true;

                                if ($.isFunction(exportGrid.beforeExport)) { // 导出数据前的事件
                                    doExport = exportGrid.beforeExport(gridInstance); // 业务上判断是否导出
                                }

                                if (doExport) { // 执行导出逻辑
                                    // 配置导出文件名
                                    if ($.isFunction(exportGrid.exportFileName)) {
                                        // 注意函数每次点击都会被调用，例如导出文件名需要设置时间戳
                                        exportOptions["exportFileName"] = exportGrid.exportFileName(gridInstance);
                                    }

                                    if (exportOptions["frontExport"]) {
                                        if (exportOptions.exportServiceName !== undefined){
                                            delete exportOptions.exportServiceName;
                                        }
                                        if (exportOptions.exportMethodName !== undefined){
                                            delete exportOptions.exportMethodName;
                                        }

                                        // 前端导出
                                        if ($.isFunction(exportGrid.exportEiInfo)) {
                                            // 注意exportEiInfo函数每次点击都会被调用
                                            exportEiInfo = exportGrid.exportEiInfo(gridInstance);

                                        } else if (IPLAT.isEiInfo(exportGrid.exportEiInfo)) {
                                            exportEiInfo = exportGrid.exportEiInfo;
                                        } else {
                                            // 默认为导出当前页，前端导出
                                            // TODO: 1.指明导出的列；2.指明不导出的列；3.导出显示的值（例如下拉选项的中文内容）
                                            // exportEiInfo = gridInstance.wrapEiBlock(null);
                                            exportEiInfo = gridInstance.getDisplayEiInfo(exportOptions["exportBlockId"]);
                                        }

                                        // 根据指明的导出列/不导出列 过滤exportEiInfo
                                        var existColumns = _.keys(exportEiInfo.getBlock(blockId).getBlockMeta().getMetas()),
                                            exportColumns = exportGrid['exportColumns'] || existColumns,
                                            unExportColumns = exportGrid['unExportColumns'] || [];

                                        var removedColumns = _.filter(existColumns, function (existColumn) {
                                            return exportColumns.indexOf(existColumn) < 0;
                                        }).concat(
                                            _.filter(unExportColumns, function (unExportColumn) {
                                                return existColumns.indexOf(unExportColumn) > -1;
                                            }));

                                        if (removedColumns.length > 0) {
                                            // 过滤掉不需要导出的列
                                            exportEiInfo.getBlock(blockId).removeColumns(removedColumns);
                                        }

                                        exportOptions["exportEiInfo"] = exportEiInfo.toEncryptedJSONString();
                                    } else {
                                        // 使用后台服务，生成要导出的EiInfo
                                        exportOptions["exportServiceName"] = exportServiceName;
                                        exportOptions["exportMethodName"] = exportMethodName;
                                        // 后端导出
                                        // 后台导出时，不使用默认的查询条件，业务上提供自定义的查询条件
                                        if ($.isFunction(exportGrid.queryEiInfo)) {
                                            exportOptions["queryInfo"] = exportGrid.queryEiInfo(gridInstance);
                                        } else if (IPLAT.isEiInfo(exportGrid.queryEiInfo)) {
                                            exportOptions["queryInfo"] = exportGrid.queryEiInfo;
                                        } else {
                                            // 后台导出时，使用默认的查询条件
                                            exportOptions["queryInfo"] = gridInstance.getQueryInfo();
                                        }

                                        if (IPLAT.isEiInfo(exportOptions["queryInfo"])) {
                                            exportOptions["queryInfo"] = exportOptions["queryInfo"].toEncryptedJSONString();
                                        }
                                    }
                                    IPLAT.Util.exportGrid(exportOptions);
                                } // doExport if结束
                            } // click函数结束
                        });
                    }
                }
                if (exportGrid.afterExportSettings === undefined){
                    // defaultActions.push({
                    //     name: "exportGrid_" + blockId,
                    //     text: "后端导出",
                    //     click: function () {
                    //         var gridInstance = $("#" + gridId).data("kendoGrid");
                    //         var doExport = true;
                    //         if ($.isFunction(exportGrid.beforeExport)) { // 导出数据前的事件
                    //             doExport = exportGrid.beforeExport(gridInstance); // 业务上判断是否导出
                    //         }
                    //         if (exportOptions["frontExport"]){
                    //             // 使用后台服务，生成要导出的EiInfo
                    //             exportOptions["exportServiceName"] = exportServiceName;
                    //             exportOptions["exportMethodName"] = exportMethodName;
                    //         }
                    //         if (doExport) { // 执行导出逻辑
                    //             if (exportServiceName === "" && exportMethodName === ""){
                    //                 exportOptions["exportServiceName"] = $("#" + gridId).data("kendoGrid").getEiInfo().get("serviceName");
                    //                 exportOptions["exportMethodName"] = "query";
                    //             }
                    //             // 配置导出文件名
                    //             if ($.isFunction(exportGrid.exportFileName)) {
                    //                 // 注意函数每次点击都会被调用，例如导出文件名需要设置时间戳
                    //                 exportOptions["exportFileName"] = exportGrid.exportFileName(gridInstance);
                    //             }
                    //
                    //             // 后端导出
                    //             // 后台导出时，不使用默认的查询条件，业务上提供自定义的查询条件
                    //             if ($.isFunction(exportGrid.queryEiInfo)) {
                    //                 exportOptions["queryInfo"] = exportGrid.queryEiInfo(gridInstance);
                    //             } else if (IPLAT.isEiInfo(exportGrid.queryEiInfo)) {
                    //                 exportOptions["queryInfo"] = exportGrid.queryEiInfo;
                    //             } else {
                    //                 // 后台导出时，使用默认的查询条件
                    //                 exportOptions["queryInfo"] = gridInstance.getQueryInfo();
                    //             }
                    //
                    //             if (IPLAT.isEiInfo(exportOptions["queryInfo"])) {
                    //                 exportOptions["queryInfo"].set("result-limit", -999999);
                    //                 exportOptions["queryInfo"] = exportOptions["queryInfo"].toEncryptedJSONString();
                    //             }
                    //             IPLAT.Util.exportGrid(exportOptions);
                    //         } // doExport if结束
                    //     } // click函数结束
                    // });
                }else {
                    if (exportGrid.afterExportSettings.isShow !== false){
                        // defaultActions.push({
                        //     name: "exportGrid_" + blockId,
                        //     text: exportGrid.afterExportSettings.name === undefined ? "后端导出" : exportGrid.afterExportSettings.name,
                        //     click: function () {
                        //         var gridInstance = $("#" + gridId).data("kendoGrid");
                        //         var doExport = true;
                        //         if ($.isFunction(exportGrid.beforeExport)) { // 导出数据前的事件
                        //             doExport = exportGrid.beforeExport(gridInstance); // 业务上判断是否导出
                        //         }
                        //         if (exportOptions["frontExport"]){
                        //             // 使用后台服务，生成要导出的EiInfo
                        //             exportOptions["exportServiceName"] = exportServiceName;
                        //             exportOptions["exportMethodName"] = exportMethodName;
                        //         }
                        //         if (doExport) { // 执行导出逻辑
                        //             if (exportServiceName === "" && exportMethodName === ""){
                        //                 exportOptions["exportServiceName"] = $("#" + gridId).data("kendoGrid").getEiInfo().get("serviceName");
                        //                 exportOptions["exportMethodName"] = "query";
                        //             }
                        //             // 配置导出文件名
                        //             if ($.isFunction(exportGrid.exportFileName)) {
                        //                 // 注意函数每次点击都会被调用，例如导出文件名需要设置时间戳
                        //                 exportOptions["exportFileName"] = exportGrid.exportFileName(gridInstance);
                        //             }
                        //
                        //             // 后端导出
                        //             // 后台导出时，不使用默认的查询条件，业务上提供自定义的查询条件
                        //             if ($.isFunction(exportGrid.queryEiInfo)) {
                        //                 exportOptions["queryInfo"] = exportGrid.queryEiInfo(gridInstance);
                        //             } else if (IPLAT.isEiInfo(exportGrid.queryEiInfo)) {
                        //                 exportOptions["queryInfo"] = exportGrid.queryEiInfo;
                        //             } else {
                        //                 // 后台导出时，使用默认的查询条件
                        //                 exportOptions["queryInfo"] = gridInstance.getQueryInfo();
                        //             }
                        //
                        //             if (IPLAT.isEiInfo(exportOptions["queryInfo"])) {
                        //                 exportOptions["queryInfo"].set("result-limit", -999999);
                        //                 exportOptions["queryInfo"] = exportOptions["queryInfo"].toEncryptedJSONString();
                        //             }
                        //             IPLAT.Util.exportGrid(exportOptions);
                        //         } // doExport if结束
                        //     } // click函数结束
                        // });
                    }
                }
            }

            return defaultActions;
        };

        var settingActions = _buildGridSettings(options);

        if (!isArray(toolbarConfig["setting"])) {
            toolbarConfig["setting"] = [];
        }

        settingActions = settingActions.concat(toolbarConfig["setting"]);
        toolbarConfig["settingActions"] = settingActions;

        if (settingActions.length > 0) { // 处理设置信息，grid右侧的下拉菜单
            kendoToolBarItems.push({
                type: "buttonGroup",
                buttons: settingActions,
                overflow: "always" // kendo toolbar anchor dropdown buttons
            });
        }

        toolbarConfig["kendoToolBarItems"] = kendoToolBarItems;


        /*******************************************************
         *
         * STEP3    处理翻页条在Grid上方的逻辑
         *
         *******************************************************/

        if (IPLAT.EFGrid._hasToolbarPager(options)) {

            if (IPLAT.EFGrid._hasToolbarButtons(toolbarConfig) && options.showCount) {
                // 有ToolBarButtons且从服务端查询总数时的翻页展示处理

                extend(true, options.pageable, {
                    messages: {
                        "display": "共 {2} 条"
                    }
                });
            }
        }

        options.toolbarConfig = toolbarConfig;

        return toolbarConfig;
    };


    // 创建操作按钮工具栏
    IPLAT.EFGrid._createToolBar = function (options, gridInstance) {
        var blockId = options.blockId,
            kendoToolBarItems = options.toolbarConfig["kendoToolBarItems"],
            toolbarId = '#ef_grid_toolbar_' + blockId,
            $grid = gridInstance.element;

        $grid.find(toolbarId).kendoToolBar({
            items: kendoToolBarItems
        });

        // 工具条渲染结束后 绑定按钮的事件
        var toolbarButtons = options.toolbarConfig["buttons"];

        each(toolbarButtons, function (index, buttonOptions) {
            if ($.isFunction(buttonOptions.click)) {
                $(toolbarId).on("click", ".kendo-xplat-" + buttonOptions.name, buttonOptions.click);
            }
        });

        if (IPLAT.EFGrid._hasToolbarSettings(options.toolbarConfig)) {
            $(toolbarId).addClass("k-iplat-setting-open");
        }

        $(toolbarId).on("click.IPLAT", '.k-grid-cancel-changes', function () {
            // 点击cancel-changes按钮，会更改所有model（tr）的uid
            gridInstance._checkedRows = [];
        });
    };

})(window.jQuery);