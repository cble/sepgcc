package com.sepgcc.site.utils;

import com.sepgcc.site.constants.FileConstants;
import com.sepgcc.site.dao.entity.FileMetaDO;
import com.sepgcc.site.dto.FileMeta;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final BeanCopier toFileMetaDOCopier = BeanCopier.create(FileMeta.class, FileMetaDO.class, false);
    private static final BeanCopier toFileMetaCopier = BeanCopier.create(FileMetaDO.class, FileMeta.class, false);

    public static String joinPath(String... paths) {
        if (paths == null) {
            return null;
        }
        List<String> pathList = new ArrayList<String>();
        for (String path : paths) {
            String s = path.trim();
            if (s.startsWith(FileConstants.SEP)) {
                s = s.substring(1, s.length());
            }
            if (s.endsWith(FileConstants.SEP)) {
                s = s.substring(0, s.length() - 1);
            }
            pathList.add(path);
        }
        return StringUtils.join(pathList, FileConstants.SEP);
    }

    /**
     * md5(md5(userId) + checksum(file))
     * @param fileMeta
     * @return
     */
    public static String generateId(FileMeta fileMeta) {
        try {
            if (fileMeta != null) {
                String header = Md5Utils.md5(String.valueOf(fileMeta.getUserId()).getBytes("UTF-8"));
                String body = Md5Utils.md5(fileMeta.getBytes());
                return Md5Utils.md5((header + body).getBytes());
            }
        } catch (Exception e) {
            // TODO log
        }
        return null;
    }

    public static String generateStoragePath(String fileId) {
        return joinPath(
                String.valueOf(fileId.charAt(0)),
                String.valueOf(fileId.charAt(1)),
                fileId);
    }

    public static FileMeta toFileMeta(MultipartFile mpf, int userId) throws Exception {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileName(mpf.getOriginalFilename());
        fileMeta.setFileType(mpf.getContentType());
        fileMeta.setBytes(mpf.getBytes());
        fileMeta.setUserId(userId);
        return fileMeta;
    }

    public static FileMeta toFileMeta(FileMetaDO fileMetaDO) {
        FileMeta fileMeta = new FileMeta();
        toFileMetaCopier.copy(fileMetaDO, fileMeta, null);
        return fileMeta;
    }

    public static FileMetaDO toFileMetaDO(FileMeta fileMeta) {
        FileMetaDO result = new FileMetaDO();
        toFileMetaDOCopier.copy(fileMeta, result, null);
        return result;
    }

    public static void saveToDisk(String storagePath, String fileName, byte[] bytes) throws IOException {
        String realPath = joinPath(FileConstants.FILE_BASE, storagePath);
        createDir(realPath);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(joinPath(realPath, fileName)), bytes);
    }

    public static byte[] readFromDisk(String path) throws IOException {
        return IOUtils.toByteArray(new FileInputStream(path));
    }

    public static void createDir(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
