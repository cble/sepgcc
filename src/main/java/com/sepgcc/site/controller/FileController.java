package com.sepgcc.site.controller;

import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.exceptions.FileTypeNotSupportException;
import com.sepgcc.site.exceptions.ResourceNotFoundException;
import com.sepgcc.site.service.FileService;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController extends BaseController {

    private static final Logger log = Logger.getLogger(FileController.class);

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
        if (out != null && FileUtils.isImageFile(out)) {
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

    @RequestMapping(value = {"/uploadfile"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, String> uploadFile(
            @ModelAttribute User user,
            MultipartHttpServletRequest request) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        MultipartFile mpf = request.getFileMap().values().iterator().next(); // 目前只会有一个文件
        try {
            FileMeta fileMeta = FileUtils.toFileMeta(mpf, user.getId());
            String fileId = fileService.saveFile(fileMeta);
            if (StringUtils.isNotBlank(fileId)) {
                result.put("state", "SUCCESS");
                result.put("fileId", fileId);
                result.put("fileName", fileMeta.getFileName());
                if (FileUtils.isImageFile(fileMeta)) {
                    result.put("url", "/img/" + fileId);
                }
            }
        } catch (FileTypeNotSupportException e) {
            result.put("state", e.getMessage());
        }
        return result;
    }
}
