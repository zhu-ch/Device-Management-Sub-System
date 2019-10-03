package com.management.admin.modules.sys.controller;

import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.sys.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/sys/user")
public class UserController extends BaseApi {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam("password") String password,
                        @RequestParam("dept") String department) {
        String passwordDB = userDao.queryPassword(department);
        if (passwordDB == null)
            return retMsg.Set(MsgType.ERROR,null,"NO_SUCH_DEPT");
        if (passwordDB.equals(password))
            return retMsg.Set(MsgType.SUCCESS);
        return retMsg.Set(MsgType.ERROR,null,"WRONG_PASSWORD");
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Object changePassword(@RequestParam("password") String password,
                                 @RequestParam("dept") String department) {
        if (userDao.changePassword(department, password) == 1)
            return retMsg.Set(MsgType.SUCCESS);
        else return retMsg.Set(MsgType.ERROR);
    }

    @RequestMapping(value = "getSub",method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(){
        return userDao.getSubFromDept();
    }
}


