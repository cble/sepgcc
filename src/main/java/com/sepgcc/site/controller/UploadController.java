package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.exceptions.ResourceNotFoundException;
import com.sepgcc.site.service.ProjectService;
import com.sepgcc.site.service.UploadService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UploadController extends BaseController {

    private static final Logger log = Logger.getLogger(UploadController.class);

    @Resource
    private ProjectService projectService;
    @Resource
    private UploadService uploadService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute User user) throws Exception {
        return new ModelAndView("list");
    }

    @RequestMapping(value = {"/ajax/projectlist"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Paginate<Project>> projectList(@ModelAttribute User user, int page) throws Exception {
        int index = (page - 1) * SiteConstants.PAGE_SIZE;
        int limit = SiteConstants.PAGE_SIZE;
        List<Project> projectList = projectService.queryWithLimit(index, limit, SiteConstants.USER_AVAILABLE_PROJECT_STATUS);
        int projectCount = projectService.countAll(SiteConstants.USER_AVAILABLE_PROJECT_STATUS);
        for (Project project : projectList) {
            List<Upload> uploadList = uploadService.queryByProjectIdUserId(project.getId(), user.getId());
            if (CollectionUtils.isNotEmpty(uploadList)) {
                project.setUploadId(uploadList.get(0).getId());
            }
        }

        Paginate<Project> paginate = new Paginate<Project>();
        paginate.setPageCount(projectCount / (SiteConstants.PAGE_SIZE + 1) + 1);
        paginate.setList(projectList);

        return new AjaxResponse<Paginate<Project>>(HttpStatus.OK.value(), null, paginate);
    }

    @RequestMapping(value = {"/mylist"}, method = RequestMethod.GET)
    public ModelAndView myList(@ModelAttribute User user) throws Exception {
        return new ModelAndView("mylist");
    }

    @RequestMapping(value = {"/ajax/myprojectlist"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Paginate<Upload>> myProjectList(@ModelAttribute User user, int page) throws Exception {
        int index = (page - 1) * SiteConstants.PAGE_SIZE;
        int limit = SiteConstants.PAGE_SIZE;
        int uploadCount = uploadService.countByUserId(user.getId());
        List<Upload> uploadList = uploadService.queryByUserIdWithLimit(user.getId(), index, limit);

        Paginate<Upload> paginate = new Paginate<Upload>();
        paginate.setPageCount(uploadCount / (SiteConstants.PAGE_SIZE + 1) + 1);
        paginate.setList(uploadList);

        return new AjaxResponse<Paginate<Upload>>(HttpStatus.OK.value(), null, paginate);
    }

    @RequestMapping(value = {"/notice"}, method = RequestMethod.GET)
    public ModelAndView notice(@RequestParam int projectId, ModelMap modelMap) throws Exception {
        Project project = projectService.loadById(projectId, SiteConstants.USER_AVAILABLE_PROJECT_STATUS);
        if (project == null) {
            throw new ResourceNotFoundException();
        }
        modelMap.put("project", project);
        return new ModelAndView("notice");
    }

    @RequestMapping(value = {"/upload"}, method = RequestMethod.GET)
    public ModelAndView upload(@RequestParam int projectId, @ModelAttribute User user, ModelMap modelMap) throws Exception {
        Project project = projectService.loadById(projectId, SiteConstants.USER_AVAILABLE_PROJECT_STATUS);
        if (project == null) {
            throw new ResourceNotFoundException();
        }
        List<Upload> uploadList = uploadService.queryByProjectIdUserId(projectId, user.getId());
        if (CollectionUtils.isNotEmpty(uploadList)) {
            return new ModelAndView(new RedirectView("modify?uploadId=" + uploadList.get(0).getId()));
        } else {
            modelMap.put("project", project);
            return new ModelAndView("upload");
        }
    }

    @RequestMapping(value = {"/modify"}, method = RequestMethod.GET)
    public ModelAndView modify(@RequestParam int uploadId, @ModelAttribute User user, ModelMap modelMap) throws Exception {
        Upload upload = uploadService.loadById(uploadId, user.getId());
        if (upload != null) {
            modelMap.put("project", projectService.loadById(upload.getProjectId(), SiteConstants.USER_AVAILABLE_PROJECT_STATUS));
            modelMap.put("upload", upload);
            return new ModelAndView("modify");
        } else {
            return new ModelAndView(new RedirectView("index"));
        }
    }

    @RequestMapping(value = {"/ajax/submitupload"}, method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody AjaxResponse<String> submitUpload(
            @ModelAttribute User user,
            @RequestBody UploadSubmit submit) throws Exception {
        try {
            if (submit.getUploadId() > 0) {
                uploadService.modifyUpload(submit, user.getId(), submit.getUploadId());
            } else {
                uploadService.createUpload(submit, user.getId());
            }
            return new AjaxResponse<String>(HttpStatus.OK.value(), null, "/");
        } catch (IllegalArgumentException e) {
            return new AjaxResponse<String>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
    }
}
