package com.sepgcc.site.controller;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController extends BaseController {

    private static final Logger log = Logger.getLogger(IndexController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        log.info("in IndexController");
        return new ModelAndView("login");
    }
}
