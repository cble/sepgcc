package com.sepgcc.site.service;

import com.sepgcc.site.dao.FileMetaDAO;
import com.sepgcc.site.dao.entity.FileMetaDO;
import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FileService {

    private static final Logger log = Logger.getLogger(FileService.class);

    @Resource
    private FileMetaDAO fileMetaDAO;

    public String saveFile(FileMeta fileMeta) {
        try {
            validate(fileMeta);

            String fileId = FileUtils.generateId(fileMeta);
            if (isFileExsists(fileId)) {
                return fileId;
            }
            String storagePath = FileUtils.generateStoragePath(fileMeta);
            FileUtils.saveToDisk(
                    FileUtils.joinPath(storagePath, fileMeta.getFileName()),
                    fileMeta.getBytes()
            );
            FileMetaDO fileMetaDO = FileUtils.toFileMetaDO(fileMeta, fileId);
            fileMetaDAO.insert(fileMetaDO);
            return fileId;
        } catch (IllegalArgumentException e) {
            log.warn(e);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public FileMeta getFile(String fileId, int userId, boolean fillContent) {
        try {
            Validate.notEmpty(fileId, "invalid fileId");

            FileMetaDO fileMetaDO = fileMetaDAO.loadById(fileId);
            if (fileMetaDO != null && fileMetaDO.getUserId() == userId) {
                FileMeta fileMeta = FileUtils.toFileMeta(fileMetaDO);
                if (fillContent) {
                    fileMeta.setBytes(FileUtils.readFromDisk(FileUtils.joinPath(fileMetaDO.getStoragePath(), fileMetaDO.getFileName())));
                }
                return fileMeta;
            }
        } catch (IllegalArgumentException e) {
            log.warn(e);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    private boolean isFileExsists(String fileId) {
        return fileMetaDAO.loadById(fileId) != null;
    }

    private void validate(FileMeta fileMeta) {
        Validate.notNull(fileMeta, "null fileMeta");
        Validate.isTrue(fileMeta.getUserId() > 0, "invalid userId");
        Validate.notEmpty(fileMeta.getFileName(), "invalid fileName");
    }
}
