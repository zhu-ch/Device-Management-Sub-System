package com.management.admin.modules.infoDevice.controller;

import com.management.admin.common.persistence.Page;
import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.infoDevice.entity.InfoDevice;
import com.management.admin.modules.infoDevice.service.InfoDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/sys/info/confidential")
@Controller
public class InfoDeviceController extends BaseApi {
    @Autowired
    private InfoDeviceService infoDeviceService;

    @RequestMapping(value = "/getDeviceName",method = RequestMethod.POST)
    @ResponseBody
    public Object getDeviceName(@RequestParam String type) throws Exception {
        return infoDeviceService.getDeviceName(infoDeviceService.findLabel(type));
    }

    @RequestMapping(value = "/getSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(@RequestParam String param) throws Exception {
        if (param.equals("dept")) {
            return infoDeviceService.getSubFromDept();
        } else {
            return infoDeviceService.getSubFromDict(param);
        }
    }

    @RequestMapping(value = "/getDeptSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getDeptSub(@RequestParam String id) throws Exception {
        return infoDeviceService.getDeptSub(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(@RequestBody InfoDevice infoDevice) throws Exception {
        try {
            Page<InfoDevice> page = new Page<>();
            page.setResultList(infoDeviceService.selectDictListByPage(infoDevice));
            page.setTotal(infoDeviceService.selectSearchCount(infoDevice));
            return retMsg.Set(MsgType.SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }
}
