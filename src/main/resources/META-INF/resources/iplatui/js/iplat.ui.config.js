/**
 * IPLATUI组件的全局配置
 * @author 黄可
 */
(function ($) {
    var extend = $.extend;

    var notification_width,notification_left_distance;

    //判断是否为移动端
    function isMobile() {
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/(iPhone|iPod|Android|ios)/i) || ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        }
        else {
            return false;
        }
    }

    if (isMobile()){
        notification_width = "90%";
        notification_left_distance = "5%";
    }else{
        notification_width = "60%";
        notification_left_distance = "20%";
    }

    // 定义IPLAT模块，统一管理平台提供的方法
    var IPLAT = window.IPLAT || {};

    // KendoUI原生属性配置管理，业务JS内可自定义配置
    var IPLATUI = window.IPLATUI || {};

    // $ 默认表示根结点的id
    var rootId = "$";

    extend(IPLATUI, {
        TOP_DOMAIN: "",
        APM_URL: "${IPLAT_APM_URL}",
        APM_ANALYSIS: "${IPLAT_APM_ANALYSIS}",

        ES_SPAN: 60,
        ES_REFRESH: 5,

        Config: {
            Layout: { // 按钮的文字 图标显示控制
                TEXT: "1",
                ICON: "2",
                ICON_AND_TEXT: "3",
                MENU_WITH_FORM_ENAME: false
            },

            Timer: {
                dblclick: 300 // 判断双击的时间间隔
            },

            // 配置Notification提示
            Notification: {
                LEVEL: "INFO", // INFO: 0, DEBUG: 5, WARNING: 15, ERROR: 25 小于设定LEVEL的通知不会显示
                AUTO_HIDE_AFTER: 6000, // 配置消息展示时间
                SHOW_INITLOAD_MSG: false, // 控制页面初始加载信息的显示
                SHOW_MSG: true, // 控制显示EiInfo中的msg
                SHOW_MSG_KEY: false, // SHOW_MSG为true时，配置为false可隐藏msgKey
                SHOW_DETAIL_MSG: true, // 默认显示EiInfo中的detailMsg
                COPY: true, // 复制按钮是否显示
                LOCK: true, // 固定按钮是否显示
                APM: true, // APM按钮是否显示
                OFF: false, // 全局控制的开关
                WIDTH: notification_width, // 整体宽度
                LEFT: notification_left_distance
            },

            // 模态对话框
            Modal: {
                ALERT_MIN_WIDTH: 300, // 警告对话框配置的默认最小宽度 (>=300)
                CONFIRM_MIN_WIDTH: 350, // 确认对话框配置的默认最小宽度 (>=300)
                PROMPT_MIN_WIDTH: 350, // 输入对话框配置的默认最小宽度 (>=300)

                ALERT_TITLE: "警告", // 警告对话框配置的默认标题
                CONFIRM_TITLE: "确认", // 确认对话框配置的默认标题
                PROMPT_TITLE: "提示" // 输入对话框配置的默认标题
            },

            EiInfo: {
                version: "2.0"
            },

            EFPage: {
                fitHeightClass: "i-fit-height", // 高度适应到底部的元素的jQuery Class选择器
                topOffset: 200, // 滚动的偏移量，超出后将出现region导航和回到顶部的按钮
                scrollTop: true, // 默认显示回到顶部
                regionNav: true, // 默认显示Region导航
                paddingBottom: 8 // EFPage页面内form的底部padding
            },
            EFRegion: {
                borderH: 2 // EFRegion的上下border的高度
            },

            EFFlexBox: {},
            EFSiderBar: {},
            EFCard: {},

            EFGrid: {
                isFloat: false, // 是否打开Grid标题和底部辅助横向滚动条悬浮
                sumTypeFloat: false,//是否小计总计行悬浮
                allowCopy: true,
                autoBind: true, // 是否主动进行查询
                // height: 350,
                resizable: true,
                sortable: false, // 默认是false，为true时，进行单列排序

                // sortable: {
                //     mutex: true
                //     mode: "multiple", // 默认是"single",只能单列排序，"multiple"时多列排序
                //     allowUnsort: false, // 默认是true，允许出现非排序状态；asc normal desc
                //     initialDirection: "asc"// 初始的排序顺序
                // },

                // selectable: false, // 禁止选中，行没有选中样式；不能使用grid的select接口
                selectable: true, // 单行选中，单元格的复制，此种场景没有列选功能
                // selectable: "multiple,row", // 多行选中，此种场景没有列选功能，表格无法在手机上滚动
                // selectable: "multiple,cell", // 多单元格选中，此种场景有、列选功能，表格无法在手机上滚动

                editable: true, // 编辑权限 incell
                headerAttributes: {
                    style: "text-align:center"
                },
                pageable: {
                    // refresh按钮 input跳转 翻页文字展示 在此配置
                    // refresh: true, // 默认不显示刷新按钮,
                    input: true, // 可输入页码的输入框，输入页码跳转，不可和numeric同时使用
                    numeric: false, // 默认显示页码按钮  [1][2][3]...[8][9][10]
                    pageSize: 10, // DataSource设置会覆盖此处设置
                    pageSizes: [10, 20, 50, 100] // "all"] // 分页配置
                },
                textareaIsEdit: "${IPLAT4J_UI_GRID_TEXTAREA_EIDTOR}",
                // IPLAT平台封装的配置项，非kendoui标准属性
                autoDraw: "yes", // Grid中的列渲染模式
                checkMode: "multiple, cell", // 行的勾选模式
                needAuth: true, // 增删改按钮是否需要注册授权
                copyToAdd: true, // 行复制新增功能
                readonly: false, // Grid只读控制
                enable: true, // Grid的编辑，提交控制
                rowNo: false, // 是否显示行序号
                rowNoText: "序号", // 行序号的列头文本
                rowNoAlign: "center", // 行序号默认居中显示

                pagerPosition: "top", // 翻页条默认配置在上方，EFGrid上方工具条按钮较多时，应用根据具体情况将翻页条放置在底部
                showCount: false, // 后台是否查询总数
                finalSumTypeScrollPosition: "bottom",

                encoded: false, // 默认关闭转义
                columnWidth: 120,
                minColumnWidth: 50, // 列resize的最小值
                rowHeight: 24,

                exportGrid: true, // 默认导出当前页面的数据
                editHelper: false, // 勾选时，可编辑的单元格突出显示
                validateHelper: true, // 单元格编辑校验出错时，红框突出提示
                MAX_COUNT: 1000,  // pageSize < 0 MAX_COUNT = 1000
                QUERY_ALL: -999999// 查询所有记录
            },

            EFPopup: {
                filterPopupGrid: true, // 控制EFPopupColumn, EFPopupInput 弹出的Grid的列是否显示过滤条件
                showCount: false
            },

            EFSelect: {
                animation: { // 下拉的动画效果（注释掉后则使用默认的向下展开动画效果，取消动画效果需将配置改为 animation: false）
                    // close: {
                    //     effects: "fadeOut zoom:out",
                    //     duration: 300
                    // },
                    // open: {
                    //     effects: "fadeIn zoom:in",
                    //     duration: 300
                    // }
                },
                useTemplateFilter: true
            },


            EFDate: {
                format: "yyyy-MM-dd",
                culture: "zh-CN",
                max: new Date(2199,11,31),
                animation: {
                    // close: {
                    //     effects: "fadeOut zoom:out", //关闭时动画特效类型，多个特效用空格隔开。
                    //     duration: 400 //关闭时动画持续的时间，单位是毫秒。
                    // },
                    // open: {
                    //     effects: "fadeIn zoom:in",
                    //     duration: 400
                    // }
                }
            },

            EFDateTime: extend({}, this.EFDate, {
                format: "yyyy-MM-dd HH:mm:ss",
                max: new Date(2199,11,31)
            }),

            EFTime: extend({}, this.EFDate, {
                format: "HH:mm:ss"
            }),

            EFDateSpan: {
                extChar: ""
            },

            EFTree: {
                PID: "inqu_status-0-node", // 展开结点时，查询条件为结点的Id，默认存储在EiInfo的inqu_status-0-node（三段式）位置
                EXPAND_LEVEL: "inqu_status-0-expandLevel", // 默认展开层级会将参数存储在EiInfo的inqu_status-0-expandLevel（三段式）位置
                ROOT_ID: rootId // $ 默认表示根结点的id
            },

            EFTreeList: {
                ROOT_ID: rootId // $ 默认表示根结点的id
            },

            EFTab: {},

            EFMenu: {
                orientation: "vertical",
                openOnClick: true,
                direction: "bottom left"
            },

            EFWindow: {
                width: "80%", // 适合弹窗显示一个完整的页面 "1000", "80%", 1000都是可接受的值
                height: "80%",
                left: "10%",
                top: "10%",

                minWidth: "60%", // 适合显示局部的div内容
                minHeight: "60%",
                minLeft: "20%",
                minTop: "20%"
            },

            EFInput: {},

            EFColumn:{
                windowClose: false
            },

            EFUpload: {
                initUrl: "/EU/DM/EUDM07.jsp",    // 查询单个附件，或者附件组的信息 JSON格式
                saveUrl: "/EU/DM/EUDM04.jsp",    // 附件上传的Servlet
                downloadUrl: "/EU/DM/EUDM06.jsp", // 附件下载的Servlet
                removeUrl: "/EU/DM/EUDM08.jsp",  // 附件删除的Servlet
                downloadZip: "/EU/DM/EUDM14.jsp"  // 多附件下载压缩包的Servlet
            },

            EFAutoComplete: {},

            // 列快捷菜单
            EFColumnShortcutMenu: [
                // type => 类别, name => 名称, icon => 字体图标, invokeFn => 调用方法
                {
                    type: 'sortAsc',
                    name: '升序',
                    icon: 'fa-sort-alpha-asc',
                    invokeFn: function (gridInstance, column) {
                        gridInstance.dataSource.data().sort(function (a, b) {
                            return a[column] > b[column];
                        });
                        gridInstance.dataSource._change({action: 'sync'});
                    }
                },
                {
                    type: 'sortDesc',
                    name: '降序',
                    icon: 'fa-sort-alpha-desc',
                    invokeFn: function (gridInstance, column) {
                        gridInstance.dataSource.data().sort(function (a, b) {
                            return a[column] < b[column];
                        });
                        gridInstance.dataSource._change({action: 'sync'});
                    }
                },
                {
                    type: 'autoFit',
                    name: '宽度自适应',
                    icon: 'fa-arrows-h',
                    invokeFn: function (gridInstance, column) {
                        gridInstance.autoFitColumn(column);
                    }
                }
            ],

            Form: {
                reuse: false
            }
        }
    });

    extend(window, {
        IPLAT: IPLAT,
        IPLATUI: IPLATUI
    });
})(jQuery);