package com.sepgcc.site.controller;

import com.sepgcc.site.dao.UploadSubmit;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.service.FileService;
import com.sepgcc.site.service.ProjectService;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController extends BaseController {

    private static final Logger log = Logger.getLogger(UploadController.class);

    private static final int PAGE_SIZE = 10;

    @Resource
    private ProjectService projectService;
    @Resource
    private FileService fileService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute User user) throws Exception {
        return new ModelAndView("list");
    }

    @RequestMapping(value = {"/ajax/projectlist"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Paginate<Project>> list(@ModelAttribute User user, int page) throws Exception {
        int index = (page - 1 )* PAGE_SIZE;
        int limit = PAGE_SIZE;
        List<Project> projectList = projectService.queryWithLimit(index, limit);
        int projectCount = projectService.countAll();

        Paginate<Project> paginate = new Paginate<Project>();
        paginate.setPageCount(projectCount / PAGE_SIZE + 1);
        paginate.setList(projectList);

        AjaxResponse<Paginate<Project>> response = new AjaxResponse<Paginate<Project>>();
        response.setCode(200);
        response.setData(paginate);

        return response;
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

    @RequestMapping(value = {"/uploadFile"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/ajax/submitUpload"}, method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody AjaxResponse submitUpload(
            @ModelAttribute User user,
            HttpServletRequest request,
            @RequestBody UploadSubmit uploadSubmit) throws Exception {

        if (uploadSubmit.getItems() != null) {
            for (Map.Entry<String, List<String>> entry : uploadSubmit.getItems().entrySet()) {
                // create upload
                // bind files with upload
            }
        }
        return new AjaxResponse();
    }
}
