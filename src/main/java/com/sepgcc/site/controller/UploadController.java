package com.sepgcc.site.controller;

import com.sepgcc.site.dto.Project;
import com.sepgcc.site.service.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UploadController extends BaseController {

    private static final Logger log = Logger.getLogger(UploadController.class);

    @Resource
    private ProjectService projectService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView list(ModelMap modelMap) throws Exception {
        List<Project> projectList = projectService.queryAll();
        int projectCount = projectService.countAll();
        modelMap.put("projectList", projectList);
        modelMap.put("projectCount", projectCount);
        return new ModelAndView("list");
    }

    @RequestMapping(value = {"/notice"}, method = RequestMethod.GET)
    public ModelAndView notice(int projectId, ModelMap modelMap) throws Exception {
        Project project = projectService.loadProjectById(projectId);
        modelMap.put("project", project);
        return new ModelAndView("notice");
    }

    @RequestMapping(value = {"/upload"}, method = RequestMethod.GET)
    public ModelAndView upload(int projectId, ModelMap modelMap) throws Exception {
        Project project = projectService.loadProjectById(projectId);
        modelMap.put("project", project);
        return new ModelAndView("upload");
    }
}
