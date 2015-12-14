package com.sepgcc.site.controller;

import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.dto.Project;
import com.sepgcc.site.service.FileService;
import com.sepgcc.site.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController extends BaseController {

    private static final Logger log = Logger.getLogger(UploadController.class);

    @Resource
    private ProjectService projectService;
    @Resource
    private FileService fileService;

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
//        Project project = projectService.loadProjectById(projectId);
//        modelMap.put("project", project);
//        return new ModelAndView("upload");
        return new ModelAndView("uploadtest");
    }

    @RequestMapping(value = {"/uploadFile"}, method = RequestMethod.POST)
    public @ResponseBody List<FileMeta> uploadFile(MultipartHttpServletRequest request) throws Exception {
        Map<String, MultipartFile> mpfMap = request.getFileMap();
        List<FileMeta> fileMetaList = new ArrayList<FileMeta>(mpfMap.size());
        for (MultipartFile mpf : mpfMap.values()) {
            FileMeta fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileType(mpf.getContentType());
            fileMeta.setBytes(mpf.getBytes());
            String fileId = fileService.saveFile(fileMeta);
            if (StringUtils.isNotBlank(fileId)) {
                fileMeta.setFileId(fileId);
                fileMetaList.add(fileMeta);
            }
        }
        return fileMetaList;
    }
}