package com.management.admin.modules.securityProduct.controller;

import com.management.admin.common.persistence.Page;
import com.management.admin.common.web.BaseApi;
import com.management.admin.common.web.MsgType;
import com.management.admin.modules.securityProduct.entity.SecurityProduct;
import com.management.admin.modules.securityProduct.service.ScrappedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 安全保密产品
 * @author zch
 * @date 2019/8/21 22:29
 */
@RequestMapping("/api/sys/product/scrapped")
@Controller
public class ScrappedProductController extends BaseApi {
    @Autowired
    private ScrappedProductService scrappedProductService;

    @RequestMapping(value = "/getSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getSub(@RequestParam String param) throws Exception {
        System.out.println(param);
        if (param.equals("dept")) {
            return scrappedProductService.getSubFromDept();
        } else {
            return scrappedProductService.getSubFromDict(param);
        }
    }

    @RequestMapping(value = "/getDeptSub", method = RequestMethod.POST)
    @ResponseBody
    public Object getDeptSub(@RequestParam String id) throws Exception {
        return scrappedProductService.getDeptSub(id);
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Object getList(@RequestBody SecurityProduct securityProduct) throws Exception {
        try {
            Page<SecurityProduct> page = new Page<>();
            page.setResultList(scrappedProductService.selectDictListByPage(securityProduct));
            page.setTotal(scrappedProductService.selectSearchCount(securityProduct));
            return retMsg.Set(MsgType.SUCCESS, page);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }

    @RequestMapping(value = "/deleteListByIds", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteListByIds(@RequestBody List<SecurityProduct> list) throws Exception {
        try {
            if (scrappedProductService.deleteListByIds(list)) {
                return retMsg.Set(MsgType.SUCCESS);
            } else
                return retMsg.Set(MsgType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
    }

    @RequestMapping(value = "/scrap", method = RequestMethod.POST)
    @ResponseBody
    public Object scrap(@RequestParam("id") String id,
                        @RequestParam("scrap_time") String scrapTime,
                        @RequestParam("remarks") String remarks
    ) throws Exception {
        if(scrappedProductService.scrap(id, scrapTime,remarks))
            return retMsg.Set(MsgType.SUCCESS);
        return retMsg.Set(MsgType.ERROR);
    }
}
