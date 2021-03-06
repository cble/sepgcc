package com.sepgcc.site.service;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileDownloadService {

    private static final Logger log = Logger.getLogger(FileDownloadService.class);

    @Resource
    private UploadService uploadService;
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;

    public FileMeta compressProjectFile(int projectId) {
        ZipOutputStream zip = null;
        try {
            Project project = projectService.loadById(projectId, 99, SiteConstants.ADMIN_AVAILABLE_PROJECT_STATUS);
            List<Upload> uploadList = uploadService.queryByProjectId(projectId);
            if (CollectionUtils.isNotEmpty(uploadList)) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                zip = new ZipOutputStream(out);

                for (Upload upload : uploadList) {
                    User user = userService.loadById(upload.getUserId());
                    if (user == null) {
                        continue;
                    }

                    for (ProjectItemValue projectItemValue : upload.getItemValueList()) {
                        for (FileMeta fileMeta : projectItemValue.getFileMetaList()) {
                            byte[] content = FileUtils.readFromDisk(fileMeta.getStoragePath());
                            appendToZip(zip,
                                    FileUtils.joinPath(project.getName(), user.getNickname(), projectItemValue.getName(), fileMeta.getFileName()),
                                    content);
                        }
                    }
                }

                zip.close();
                FileMeta zipFileMeta = new FileMeta();
                zipFileMeta.setFileName(project.getName() + ".zip");
                zipFileMeta.setFileType("application/zip, application/octet-stream");
                zipFileMeta.setBytes(out.toByteArray());
                return zipFileMeta;
            }
        } catch (Exception e) {
            log.error("compressProjectFile error", e);
        } finally {
            try {
                if (zip != null) {
                    zip.close();
                }
            } catch(IOException e) {
                log.error("compressProjectFile error", e);
            }
        }
        return null;
    }

    private void appendToZip(ZipOutputStream out, String path, byte[] content) {
        try {
            out.putNextEntry(new ZipEntry(path));
            out.write(content);
            out.closeEntry();
        } catch (Exception e) {
            log.error("appendToZip failed");
        }
    }
}
