package com.sepgcc.site.service;

import com.sepgcc.site.dto.FileMeta;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileService {

    private static final Logger log = Logger.getLogger(FileService.class);

    public String saveFile(FileMeta fileMeta) {

        // TODO cal file path
        // TODO generate fileId
        try {
            FileCopyUtils.copy(fileMeta.getBytes(), new FileOutputStream("/tmp/" + fileMeta.getFileName()));
            return fileMeta.getFileName();
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }
}
