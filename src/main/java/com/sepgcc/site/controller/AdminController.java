package com.sepgcc.site.controller;

import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.service.FileDownloadService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminController extends BaseController {

    private static final Logger log = Logger.getLogger(AdminController.class);

    @Resource
    private FileDownloadService fileDownloadService;

    @RequestMapping(value = "/admin/download", method = RequestMethod.GET)
    public void download(int projectId, HttpServletResponse response) {
        try {
            FileMeta out = fileDownloadService.compressProjectFile(projectId);
            response.setContentType(out.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=download.zip"); // TODO use out.filename
            FileCopyUtils.copy(out.getBytes(), response.getOutputStream());
        }catch (Exception e) {
            log.error("download error", e);
        }
    }
}
