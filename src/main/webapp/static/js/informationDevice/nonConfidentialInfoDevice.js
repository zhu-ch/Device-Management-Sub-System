let defaultFiltersCondition = {
    use_situation: '',
    type: '',
    device_name: '',
    school: '',
    subject: '',
    startTime: '',
    endTime: ''
};

let app = new Vue({
    el: '#app',
    data: {
        showWindow: false,
        loading: false,
        urls: {
            getSub: 'http://localhost:8444/api/sys/info/nonConfidential/getSub',
            getDeviceName: 'http://localhost:8444/api/sys/info/nonConfidential/getDeviceName',
            getDeptSub: 'http://localhost:8444/api/sys/info/nonConfidential/getDeptSub',
            getList: 'http://localhost:8444/api/sys/info/nonConfidential/getList',
        },
        activeNames: [],
        filters: {
            selectionList: {
                use_situation: [],
                type: [],
                school: [],
                subject: [],
                device_name: []
            },
            condition: defaultFiltersCondition
        },
        table: {
            loading: false,
            selectionList: [],
            data: [],
            props: {
                searchKey: '',
                pageIndex: 1,
                pageSize: 10,
                pageSizes: [5, 10, 20, 40, 99999],
                total: 0
            }
        },
        exportData: {
            visible: false,
            src: "../management/excel/ExportData.html"
        }
    },
    created: function () {
        this.checkStatus();
    },
    methods: {
        //判断登录状态
        checkStatus() {
            if (getCookie("name") != null) {
                this.showWindow = true;
                defaultFiltersCondition.school = getCookie("name");
                return;
            }
            this.$message({
                message: "请登录",
                type: 'error'
            });
            setTimeout(function () {
                window.open("../login.html", "_self")
            }, 2000);
        },
        getSub() {
            ajaxPost(this.urls.getSub, {param: "设备名称"}, function (result) {
                app.filters.selectionList.device_name.push({'value': '', 'label': '全选'});
                result.forEach(function (r) {
                    app.filters.selectionList.device_name.push({'value': r.id, 'label': r.dicValue});
                });
            });
            ajaxPost(this.urls.getSub, {param: "类型"}, function (result) {
                app.filters.selectionList.type.push({'value': '', 'label': '全选'});
                result.forEach(function (r) {
                    app.filters.selectionList.type.push({'value': r.id, 'label': r.dicValue});
                });
            });
            ajaxPost(this.urls.getSub, {param: "使用情况"}, function (result) {
                app.filters.selectionList.use_situation.push({'value': '', 'label': '全选'});
                result.forEach(function (r) {
                    app.filters.selectionList.use_situation.push({'value': r.id, 'label': r.dicValue});
                });
            });
            ajaxPost(this.urls.getSub, {param: "dept"}, function (result) {
                result.forEach(function (r) {
                    app.filters.selectionList.school.push(r);
                })
            });
            ajaxPost(this.urls.getDeptSub, {id: this.filters.condition.school}, function (result) {
                result.forEach(function (r) {
                    app.filters.selectionList.subject.push(r);
                });
            })
        },
        refreshTable: function () {
            let app = this;
            app.table.loading = true;
            let data = {
                page: app.table.props,
                device_name: this.filters.condition.device_name,
                type: this.filters.condition.type,
                use_situation: this.filters.condition.use_situation,
                department_code: this.filters.condition.school,
                subject_code: this.filters.condition.subject,
                startTime: this.filters.condition.startTime,
                endTime: this.filters.condition.endTime
            };
            ajaxPostJSON(this.urls.getList, data, function (result) {
                app.table.loading = false;
                app.table.data = result.data.resultList;
                app.table.props.total = result.data.total;
            })
        },
        getList: function () {
            app.table.props.pageIndex = 1;
            this.refreshTable();
        },
        onSelectionChange: function (val) {
            this.table.selectionList = val;
        },
        onPageSizeChange: function (newSize) {
            this.table.props.pageSize = newSize;
            this.refreshTable();
        },
        onPageIndexChange: function (newIndex) {
            this.table.props.pageIndex = newIndex;
            this.refreshTable();
        }
    },
    mounted: function () {
        this.getSub();
        this.refreshTable();
    }
});

function getExportConditions() {
    let ID = [];
    app.table.selectionList.forEach(function (v) {
        ID.push(v["id"]);
    });
    let data = {
        fileName: "涉密信息设备",
        templateId: "ab81d835f0b146d98b4f5e06e0f651c0",//todo 编号！！！
        fieldList: [
            {
                fieldName: "单位",
                fieldType: "department"
            }, {
                fieldName: "科室/课题组",
                fieldType: "subject"
            }, {
                fieldName: "编号",
                fieldType: "number"
            }, {
                fieldName: "固定资产编号",
                fieldType: "asset_number",
            }, {
                fieldName: "类型",
                fieldType: "type",
            }, {
                fieldName: "设备名称",
                fieldType: "device_name",
            }, {
                fieldName: "责任人",
                fieldType: "person"
            }, {
                fieldName: "密级",
                fieldType: "secret_level"
            }, {
                fieldName: "产品型号",
                fieldType: "model"
            }, {
                fieldName: "设备序列号",
                fieldType: "device_number"
            }, {
                fieldName: "硬盘序列号",
                fieldType: "disk_number"
            }, {
                fieldName: "用途",
                fieldType: "usage"
            }, {
                fieldName: "放置地点",
                fieldType: "place_location"
            }, {
                fieldName: "启用时间",
                fieldType: "enablation_time"
            }, {
                fieldName: "使用情况",
                fieldType: "use_situation"
            }, {
                fieldName: "备注",
                fieldType: "remarks"
            }
        ],
        conditionsList: [],
        idList: ID,
        isScrapped: false,
        tableName: "non_confidential_information_device"
    };
    return data;
}