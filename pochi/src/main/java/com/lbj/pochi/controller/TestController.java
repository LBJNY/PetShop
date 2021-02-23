package com.lbj.pochi.controller;

import com.lbj.pochi.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<?> test() {
        return new Result<>("操作成功");
    }

}
