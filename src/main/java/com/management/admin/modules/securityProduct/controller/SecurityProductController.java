package com.management.admin.modules.securityProduct.controller;

import com.management.admin.common.persistence.Page;
import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.securityProduct.entity.SecurityProduct;
import com.management.admin.modules.securityProduct.service.SecurityProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zch
 * @Description 报废安全保密产品
 * @date 2019/8/21 22:29
 */
@RequestMapping("/api/sys/product/product")
@Controller
public class SecurityProductController extends BaseApi {
    @Autowired
    private SecurityProductService securityProductService;

    @RequestMapping(value = "/getSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(@RequestParam String param) throws Exception {
        if (param.equals("dept")) {
            return securityProductService.getSubFromDept();
        } else {
            return securityProductService.getSubFromDict(param);
        }
    }

    @RequestMapping(value = "/getDeptSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getDeptSub(@RequestParam String id) throws Exception {
        return securityProductService.getDeptSub(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(@RequestBody SecurityProduct securityProduct) throws Exception {
        try {
            Page<SecurityProduct> page = new Page<>();
            page.setResultList(securityProductService.selectDictListByPage(securityProduct));
            page.setTotal(securityProductService.selectSearchCount(securityProduct));
            return retMsg.Set(MsgType.SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }
}
