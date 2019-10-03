package com.management.admin.modules.infoDevice.controller;

import com.management.admin.common.persistence.Page;
import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.infoDevice.entity.NonConfidentialInfoDevice;
import com.management.admin.modules.infoDevice.service.NonConfidentialInfoDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/sys/info/nonConfidential")
@Controller
public class NonConfidentialInfoDeviceController extends BaseApi {
    @Autowired
    private NonConfidentialInfoDeviceService nonConfidentialInfoDeviceService;

    @RequestMapping(value = "/getDeviceName",method = RequestMethod.POST)
    @ResponseBody
    public Object getDeviceName(@RequestParam String type) throws Exception {
        return nonConfidentialInfoDeviceService.getDeviceName(nonConfidentialInfoDeviceService.findLabel(type));
    }

    @RequestMapping(value = "/getSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(@RequestParam String param) throws Exception {
        if (param.equals("dept")) {
            return nonConfidentialInfoDeviceService.getSubFromDept();
        } else {
            return nonConfidentialInfoDeviceService.getSubFromDict(param);
        }
    }

    @RequestMapping(value = "/getDeptSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getDeptSub(@RequestParam String id) throws Exception {
        return nonConfidentialInfoDeviceService.getDeptSub(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(@RequestBody NonConfidentialInfoDevice nonConfidentialInfoDevice) throws Exception {
        try {
            Page<NonConfidentialInfoDevice> page = new Page<>();
            page.setResultList(nonConfidentialInfoDeviceService.selectDictListByPage(nonConfidentialInfoDevice));
            page.setTotal(nonConfidentialInfoDeviceService.selectSearchCount(nonConfidentialInfoDevice));
            return retMsg.Set(MsgType.SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }
}
