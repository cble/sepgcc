package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.AjaxResponse;
import com.sepgcc.site.dto.Paginate;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.service.ProjectService;
import com.sepgcc.site.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserAdminController extends BaseController {

    private static final Logger log = Logger.getLogger(UserAdminController.class);

    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/admin/userlist", method = RequestMethod.GET)
    public ModelAndView userList() {
        return new ModelAndView("user_manager");
    }

    @RequestMapping(value = {"/ajax/admin/usermanagerlist"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Paginate<User>> userList(int page) throws Exception {
        int index = (page - 1) * SiteConstants.PAGE_SIZE;
        int limit = SiteConstants.PAGE_SIZE;
        List<User> users = userService.queryWithLimit(index, limit);
        int userCount = projectService.countAll();

        Paginate<User> paginate = new Paginate<User>();
        paginate.setPageCount(userCount / SiteConstants.PAGE_SIZE + 1);
        paginate.setList(users);

        return new AjaxResponse<Paginate<User>>(HttpStatus.OK.value(), null, paginate);
    }

    @RequestMapping(value = "/admin/importuser", method = RequestMethod.GET)
    public ModelAndView importNewUser() {
        return new ModelAndView("importuser");
    }
}
