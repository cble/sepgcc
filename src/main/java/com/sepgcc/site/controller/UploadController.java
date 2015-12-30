package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.service.FileService;
import com.sepgcc.site.service.ProjectService;
import com.sepgcc.site.service.UploadService;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController extends BaseController {

    private static final Logger log = Logger.getLogger(UploadController.class);

    @Resource
    private ProjectService projectService;
    @Resource
    private FileService fileService;
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
    public ModelAndView notice(int projectId, ModelMap modelMap) throws Exception {
        Project project = projectService.loadById(projectId, SiteConstants.USER_AVAILABLE_PROJECT_STATUS);
        modelMap.put("project", project);
        return new ModelAndView("notice");
    }

    @RequestMapping(value = {"/upload"}, method = RequestMethod.GET)
    public ModelAndView upload(int projectId, ModelMap modelMap) throws Exception {
        Project project = projectService.loadById(projectId, SiteConstants.USER_AVAILABLE_PROJECT_STATUS);
        modelMap.put("project", project);
        return new ModelAndView("upload");
    }

    @RequestMapping(value = {"/modify"}, method = RequestMethod.GET)
    public ModelAndView modify(int uploadId, @ModelAttribute User user, ModelMap modelMap) throws Exception {
        Upload upload = uploadService.loadById(uploadId, user.getId());
        if (upload != null) {
            modelMap.put("project", projectService.loadById(upload.getProjectId(), SiteConstants.USER_AVAILABLE_PROJECT_STATUS));
            modelMap.put("upload", upload);
            return new ModelAndView("modify");
        } else {
            return new ModelAndView(new RedirectView("index"));
        }
    }

    @RequestMapping(value = {"/uploadfile"}, method = RequestMethod.POST)
    public @ResponseBody List<FileMeta> uploadFile(
            @ModelAttribute User user,
            MultipartHttpServletRequest request) throws Exception {
        Map<String, MultipartFile> mpfMap = request.getFileMap();
        List<FileMeta> fileMetaList = new ArrayList<FileMeta>(mpfMap.size());
        for (MultipartFile mpf : mpfMap.values()) {
            FileMeta fileMeta = FileUtils.toFileMeta(mpf, user.getId());
            String fileId = fileService.saveFile(fileMeta);
            if (StringUtils.isNotBlank(fileId)) {
                fileMeta.setFileId(fileId);
                fileMetaList.add(fileMeta);
            }
        }
        return fileMetaList;
    }

    @RequestMapping(value = {"/uploadfile2"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, String> uploadFile2(
            @ModelAttribute User user,
            MultipartHttpServletRequest request) throws Exception {
        Map<String, String> fileUrl = new HashMap<String, String>();
        Map<String, MultipartFile> mpfMap = request.getFileMap();
        for (MultipartFile mpf : mpfMap.values()) {
            FileMeta fileMeta = FileUtils.toFileMeta(mpf, user.getId());
            fileMeta.setUserId(0);
            // fileMeta.setType()
            String fileId = fileService.saveFile(fileMeta);
            if (StringUtils.isNotBlank(fileId)) {
                fileMeta.setFileId(fileId);
                fileUrl.put("url", fileId);
                break;
            }
        }
        return fileUrl;
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
            return new AjaxResponse<String>(HttpStatus.OK.value(), null, "/mylist");
        } catch (IllegalArgumentException e) {
            return new AjaxResponse<String>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
    }
}
