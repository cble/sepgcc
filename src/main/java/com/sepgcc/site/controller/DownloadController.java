package com.sepgcc.site.controller;

import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.service.FileService;
import com.sepgcc.site.utils.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DownloadController extends BaseController {

    private static final Logger log = Logger.getLogger(DownloadController.class);

    @Resource
    private FileService fileService;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam String fileId, HttpServletResponse response) {
        try {
            FileMeta out = fileService.getFile(fileId, 0, true);
            response.setContentType(out.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=" + FileUtils.encodeDownloadName(out.getFileName()));
            FileCopyUtils.copy(out.getBytes(), response.getOutputStream());
        } catch (Exception e) {
            log.error("download error", e);
        }
    }
}
