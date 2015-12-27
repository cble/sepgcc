package com.sepgcc.site.controller;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.service.FileDownloadService;
import com.sepgcc.site.service.ProjectService;
import com.sepgcc.site.service.ProjectStatisticsService;
import com.sepgcc.site.service.UploadService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ProjectAdminController extends BaseController {

    private static final Logger log = Logger.getLogger(ProjectAdminController.class);

    @Resource
    private FileDownloadService fileDownloadService;
    @Resource
    private ProjectStatisticsService projectStatisticsService;
    @Resource
    private ProjectService projectService;
    @Resource
    private UploadService uploadService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView projectlist() {
        return new ModelAndView("project_manager");
    }

    @RequestMapping(value = {"/ajax/admin/projectmanagerlist"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Paginate<Project>> projectList(int page) throws Exception {
        int index = (page - 1) * SiteConstants.PAGE_SIZE;
        int limit = SiteConstants.PAGE_SIZE;
        List<Project> projectList = projectService.queryWithLimit(index, limit);
        int projectCount = projectService.countAll();

        for (Project project : projectList) {
            project.setSuccessNumber(uploadService.countByProjectId(project.getId()));
        }

        Paginate<Project> paginate = new Paginate<Project>();
        paginate.setPageCount(projectCount / SiteConstants.PAGE_SIZE + 1);
        paginate.setList(projectList);

        return new AjaxResponse<Paginate<Project>>(HttpStatus.OK.value(), null, paginate);
    }

    @RequestMapping(value = "/admin/newproject", method = RequestMethod.GET)
    public ModelAndView newProject() {
        return new ModelAndView("newproject");
    }

    @RequestMapping(value = "/admin/projectstatistics", method = RequestMethod.GET)
    public ModelAndView projectStatistics(@RequestParam int projectId, ModelMap modelMap) {
        Project project = projectService.loadById(projectId);
        modelMap.put("project", project);
        return new ModelAndView("project_statistics");
    }

    @RequestMapping(value = "/ajax/admin/projectstatistics", method = RequestMethod.GET)
    public @ResponseBody AjaxResponse<Paginate<Project>> projectStatistics(@RequestParam int projectId, int page) {
        return new AjaxResponse<Paginate<Project>>(HttpStatus.OK.value(), null, null);
    }

    @RequestMapping(value = "/admin/downloadstatistics")
    public void downloadStatistics(@RequestParam int projectId, HttpServletResponse response) {
        try {
            FileMeta out = projectStatisticsService.generateStatisticsFile(projectId);
            response.setContentType(out.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=report.xls"); // TODO use out.filename
        } catch (Exception e) {
            log.error("download error", e);
        }
    }

    @RequestMapping(value = "/admin/downloadproject", method = RequestMethod.GET)
    public void downloadProject(@RequestParam int projectId, HttpServletResponse response) {
        try {
            FileMeta out = fileDownloadService.compressProjectFile(projectId);
            response.setContentType(out.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=project.zip"); // TODO use out.filename
            FileCopyUtils.copy(out.getBytes(), response.getOutputStream());
        } catch (Exception e) {
            log.error("downloadProject error", e);
        }
    }
}
