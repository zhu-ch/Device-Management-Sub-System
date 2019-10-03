package com.management.admin.modules.storage.controller;

import com.management.admin.common.persistence.Page;
import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.storage.entity.NonConfidentialStorage;
import com.management.admin.modules.storage.service.NonConfidentialStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @author zch
 * @date 2019/8/18 9:37
 */
@RequestMapping("/api/sys/storage/nonConfidential")
@Controller
public class NonConfidentialStorageController extends BaseApi {
    @Autowired
    private NonConfidentialStorageService nonConfidentialStorageService;

    @RequestMapping(value = "/getSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(@RequestParam String param) throws Exception {
        if (param.equals("dept")) {
            return nonConfidentialStorageService.getSubFromDept();
        } else {
            return nonConfidentialStorageService.getSubFromDict(param);
        }
    }

    @RequestMapping(value = "/getDeptSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getDeptSub(@RequestParam String id) throws Exception {
        return nonConfidentialStorageService.getDeptSub(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(@RequestBody NonConfidentialStorage nonConfidentialStorage) throws Exception {
        try {
            System.out.println(nonConfidentialStorage);
            Page<NonConfidentialStorage> page = new Page<>();
            page.setResultList(nonConfidentialStorageService.selectDictListByPage(nonConfidentialStorage));
            page.setTotal(nonConfidentialStorageService.selectSearchCount(nonConfidentialStorage));
            return retMsg.Set(MsgType.SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }
}
