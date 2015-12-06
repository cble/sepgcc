package com.sepgcc.site.controller;

import com.sepgcc.site.dto.AjaxResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

    private static final Logger log = Logger.getLogger(AjaxController.class);

    @RequestMapping(value = "/ajax/test", method = RequestMethod.GET)
    public @ResponseBody AjaxResponse test() {
        return new AjaxResponse();
    }
}
