package com.sepgcc.site.controller;

import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.exceptions.ResourceNotFoundException;
import com.sepgcc.site.service.FileService;
import com.sepgcc.site.utils.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @RequestMapping(value = "/img/{fileId}", method = RequestMethod.GET)
    public void getImage(@PathVariable String fileId, HttpServletResponse response) {
        FileMeta out = fileService.getFile(fileId, 0, true);
        if (out != null && isImageFile(out)) {
            try {
                response.setContentType(out.getFileType());
                FileCopyUtils.copy(out.getBytes(), response.getOutputStream());
            } catch (IOException e) {
                log.error("getImage error", e);
            }
        } else {
            throw new ResourceNotFoundException();
        }
    }

    private boolean isImageFile(FileMeta file) {
        return file.getFileType().startsWith("image");
    }
}
