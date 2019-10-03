function getSub() {
    ajaxPost('http://localhost:8444/api/sys/user/getSub', {param: "dept"},
        function (result) {
        result.forEach(function (r) {
            app.departmentList.push(r);
        })
    })
}

function checkStatus() {
    if (getCookie("name") != null)
        window.open("frame.html", "_self");
}

let app = new Vue({
    el: '#app',
    data: {
        passwordData: {
            password: '',
            newPassword: '',
            newPasswordConfirm: ''
        },
        departmentList: [],
        department: [],
        fullScreenLoading: false,
        isLogin: true,
        urls: {
            checkPassword: 'http://localhost:8444/api/sys/user/login',
            changePassword: 'http://localhost:8444/api/sys/user/changePassword'
        }
    },
    created: function () {
        checkStatus();
        getSub();
    },
    methods: {
        login: function () {
            let data = {
                password: app.passwordData.password,
                dept: app.department
            };
            app.fullScreenLoading = true;
            ajaxPost(app.urls.checkPassword, data, function (d) {
                app.fullScreenLoading = false;
                if (d.code === 'success') {
                    app.$message({
                        message: '登录成功',
                        type: 'success'
                    });
                    delCookie("name");
                    setCookie("name", app.department, 0);
                    setTimeout(function () {
                        window.open("frame.html", "_self");
                    }, 2000);
                } else {
                    let msg = '';
                    if (d.message === 'NO_SUCH_DEPT')
                        msg = '无此账号信息';
                    else
                        msg = '密码错误';
                    app.$message({
                        message: msg,
                        type: 'error'
                    });
                    app.passwordData.password = '';
                }
            })
        },
        changePassword: function () {
            if (app.department === '') {
                app.$message({
                    message: '请选择单位',
                    type: 'error'
                });
                app.isLogin = true;
                return;
            }

            if (app.passwordData.newPassword !== app.passwordData.newPasswordConfirm) {
                app.$message({
                    message: '两次输入不一致',
                    type: 'error'
                });
                app.passwordData.password = '';
                app.passwordData.newPassword = '';
                app.passwordData.newPasswordConfirm = '';
                return;
            }

            let data = {
                password: app.passwordData.password,
                dept: app.department
            };
            app.fullScreenLoading = true;
            ajaxPost(app.urls.checkPassword, data, function (d) {
                if (d.code === 'success') {
                    let pw = {
                        password: app.passwordData.newPassword,
                        dept: app.department
                    };
                    ajaxPost(app.urls.changePassword, pw, function (r) {
                        app.fullScreenLoading = false;
                        if (r.code === 'success') {
                            app.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                            app.passwordData.password = '';
                            app.passwordData.newPassword = '';
                            app.passwordData.newPasswordConfirm = '';
                            app.isLogin = true;
                        } else {
                            app.$message({
                                message: '修改失败，请重试',
                                type: 'error'
                            });
                            app.passwordData.password = '';
                            app.passwordData.newPassword = '';
                            app.passwordData.newPasswordConfirm = '';
                        }
                    })
                } else {
                    let msg = '';
                    if (d.message === 'NO_SUCH_DEPT')
                        msg = '无此账号信息';
                    else
                        msg = '密码错误';
                    app.$message({
                        message: msg,
                        type: 'error'
                    });
                    app.fullScreenLoading = false;
                    app.passwordData.password = '';
                    app.passwordData.newPassword = '';
                    app.passwordData.newPasswordConfirm = '';
                }
            })
        }
    },
    computed: {
        departmentName: function () {
            let list = this.departmentList;
            for (index in list) {
                if (list[index].id === this.department) {
                    return list[index].dept_name;
                }
            }
        }
    }
});