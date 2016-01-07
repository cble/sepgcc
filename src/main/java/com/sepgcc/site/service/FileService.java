package com.sepgcc.site.service;

import com.sepgcc.site.dao.FileMetaDAO;
import com.sepgcc.site.dao.entity.FileMetaDO;
import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.exceptions.FileTypeNotSupportException;
import com.sepgcc.site.utils.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private static final Logger log = Logger.getLogger(FileService.class);

    @Resource
    private FileMetaDAO fileMetaDAO;

    public String saveFile(FileMeta fileMeta) {
        log.info(String.format("saveFile input: %s", fileMeta));
        try {
            validate(fileMeta);
            FileUtils.validateFile(fileMeta);

            String fileId = FileUtils.generateId(fileMeta);
            if (isFileExsists(fileId)) {
                return fileId;
            }
            String storagePath = FileUtils.generateStoragePath(fileId);
            fileMeta.setFileId(fileId);
            fileMeta.setStoragePath(FileUtils.joinPath(storagePath, fileMeta.getFileName()));
            FileUtils.saveToDisk(
                    storagePath,
                    fileMeta.getFileName(),
                    fileMeta.getBytes()
            );
            FileMetaDO fileMetaDO = FileUtils.toFileMetaDO(fileMeta);
            fileMetaDAO.insert(fileMetaDO);
            return fileId;
        } catch (FileTypeNotSupportException e) {
            log.warn(String.format("file type not support: %s", fileMeta));
            throw e;
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
            if (fileMetaDO != null && (fileMetaDO.getUserId() == userId || userId == 0)) {
                FileMeta fileMeta = FileUtils.toFileMeta(fileMetaDO);
                if (fillContent) {
                    fileMeta.setBytes(FileUtils.readFromDisk(fileMetaDO.getStoragePath()));
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

    public Map<String, FileMeta> mGetFile(List<String> fileIdList) {
        Map<String, FileMeta> resultMap = new HashMap<String, FileMeta>();
        try {
            Validate.notEmpty(fileIdList, "invalid fileIdList");

            for (String fileId : fileIdList) {
                FileMeta fileMeta = getFile(fileId, 0, false);
                if (fileMeta != null) {
                    resultMap.put(fileId, fileMeta);
                }
            }
        } catch (IllegalArgumentException e) {
            log.warn(e);
        } catch (Exception e) {
            log.error(e);
        }
        return resultMap;
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
