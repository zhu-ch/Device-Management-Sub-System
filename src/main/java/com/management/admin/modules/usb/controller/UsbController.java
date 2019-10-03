package com.management.admin.modules.usb.controller;

import com.management.admin.common.persistence.Page;
import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.usb.entity.Usb;
import com.management.admin.modules.usb.service.UsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description usb
 * @author zch
 * @date 2019/8/23 22:27
 */
@RequestMapping("/api/sys/usb/usb")
@Controller
public class UsbController extends BaseApi {
    @Autowired
    private UsbService usbService;

    @RequestMapping(value = "/getSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(@RequestParam String param) throws Exception {
        if (param.equals("dept")) {
            return usbService.getSubFromDept();
        } else {
            return usbService.getSubFromDict(param);
        }
    }

    @RequestMapping(value = "/getDeptSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getDeptSub(@RequestParam String id) throws Exception {
        return usbService.getDeptSub(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(@RequestBody Usb usb) throws Exception {
        try {
            Page<Usb> page = new Page<>();
            page.setResultList(usbService.selectDictListByPage(usb));
            page.setTotal(usbService.selectSearchCount(usb));
            return retMsg.Set(MsgType.SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }
}
