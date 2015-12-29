package com.sepgcc.site.service;

import com.google.common.collect.Lists;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
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
        try {
            Project project = projectService.loadById(projectId, Lists.newArrayList(0, 1));
            List<Upload> uploadList = uploadService.queryByProjectId(projectId);
            if (CollectionUtils.isNotEmpty(uploadList)) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ZipOutputStream zip = new ZipOutputStream(out);

                for (Upload upload : uploadList) {
                    User user = userService.loadById(upload.getUserId());
                    if (user == null) {
                        continue;
                    }

                    for (ProjectItemValue projectItemValue : upload.getItemValueList()) {
                        for (FileMeta fileMeta : projectItemValue.getFileMetaList()) {
                            byte[] content = FileUtils.readFromDisk(fileMeta.getStoragePath());
                            appendToZip(zip,
                                    FileUtils.joinPath(user.getNickname(), projectItemValue.getName(), fileMeta.getFileName()),
                                    content);
                        }
                    }
                }

                zip.close();
                out.close();
                FileMeta zipFileMeta = new FileMeta();
                zipFileMeta.setFileName(project.getName() + ".zip");
                zipFileMeta.setFileType("application/zip, application/octet-stream");
                zipFileMeta.setBytes(out.toByteArray());
                return zipFileMeta;
            }
        } catch (Exception e) {
            log.error("compressProjectFile error", e);
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
