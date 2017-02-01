package com.sepgcc.site.controller;

import com.google.common.collect.Lists;
import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.AjaxResponse;
import com.sepgcc.site.dto.Paginate;
import com.sepgcc.site.dto.Project;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserAdminController extends BaseController {

    private static final Logger log = Logger.getLogger(UserAdminController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/admin/userlist", method = RequestMethod.GET)
    public ModelAndView userList() {
        return new ModelAndView("user_list");
    }

    @RequestMapping(value = {"/ajax/admin/userlist"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Paginate<User>> userList(int page) throws Exception {
        int index = (page - 1) * SiteConstants.PAGE_SIZE;
        int limit = SiteConstants.PAGE_SIZE;
        List<User> users = userService.queryWithLimit(index, limit);
        int userCount = userService.countAll();

        Paginate<User> paginate = new Paginate<User>();
        paginate.setPageCount(userCount / (SiteConstants.PAGE_SIZE + 1) + 1);
        paginate.setList(users);

        return new AjaxResponse<Paginate<User>>(HttpStatus.OK.value(), null, paginate);
    }

    @RequestMapping(value = "/admin/resetpassword/{userId}", method = RequestMethod.GET)
    public ModelAndView resetPassword(@PathVariable int userId, ModelMap modelMap) {
        modelMap.put("userId", userId);
        return new ModelAndView("user_reset_password");
    }

    @RequestMapping(value = {"/ajax/admin/resetpassword"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Boolean> doResetPassword(@ModelAttribute User user, String newPwd) throws Exception {
        boolean result = userService.resetPassword(user.getId(), newPwd);
        return new AjaxResponse<Boolean>(HttpStatus.OK.value(), "", result);
    }

    @RequestMapping(value = "/admin/newuser", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return new ModelAndView("newuser");
    }

    @RequestMapping(value = {"/ajax/admin/newuser"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Boolean> createNewProject(@RequestBody User user) {
        try {
            int successNumber = userService.batchCreateUser(Lists.newArrayList(user));
            return new AjaxResponse<Boolean>(HttpStatus.OK.value(), null, successNumber > 0);
        } catch (IllegalArgumentException e) {
            return new AjaxResponse<Boolean>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), false);
        }
    }
}
