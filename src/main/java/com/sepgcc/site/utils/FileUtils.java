package com.sepgcc.site.utils;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dao.entity.FileMetaDO;
import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.exceptions.FileTypeNotSupportException;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private static final Logger log = Logger.getLogger(FileUtils.class);

    private static final BeanCopier toFileMetaDOCopier = BeanCopier.create(FileMeta.class, FileMetaDO.class, false);
    private static final BeanCopier toFileMetaCopier = BeanCopier.create(FileMetaDO.class, FileMeta.class, false);

    public static String joinPath(String... paths) {
        if (paths == null) {
            return null;
        }
        List<String> pathList = new ArrayList<String>();
        for (String path : paths) {
            String s = path.trim();
            if (s.startsWith(File.separator)) {
                s = s.substring(1, s.length());
            }
            if (s.endsWith(File.separator)) {
                s = s.substring(0, s.length() - 1);
            }
            pathList.add(path);
        }
        return StringUtils.join(pathList, File.separator);
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
            log.error("generateId error", e);
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
        String realPath = joinPath(SiteConstants.FILE_BASE, storagePath);
        createDir(realPath);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(joinPath(realPath, fileName)), bytes);
    }

    public static byte[] readFromDisk(String path) throws IOException {
        String realPath = joinPath(SiteConstants.FILE_BASE, path);
        return IOUtils.toByteArray(new FileInputStream(realPath));
    }

    public static void createDir(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String encodeDownloadName(String fileName) {
        try {
            int dotPostion = fileName.lastIndexOf(".");
            if (dotPostion > 0) {
                String name = fileName.substring(0, dotPostion);
                String extension = fileName.substring(dotPostion, fileName.length());
                return new String(name.getBytes(System.getProperty("file.encoding")), "ISO-8859-1") + extension;
            } else {
                return new String(fileName.getBytes(System.getProperty("file.encoding")), "ISO-8859-1");
            }
        } catch (Exception e) {
            return "file";
        }
    }

    public static void validateFile(FileMeta fileMeta) {
        validateFileType(fileMeta.getFileName());
        validateFileSize(fileMeta.getBytes());
    }

    private static void validateFileType(String fileName) {
        int dotPostion = fileName.lastIndexOf(".");
        if (dotPostion > 0) {
            String extension = fileName.substring(dotPostion, fileName.length());
            if (Arrays.asList(SiteConstants.FILE_EXTENSION_SUPPORT).contains(extension.toLowerCase())) {
                return;
            }
        }
        throw new FileTypeNotSupportException("文件类型暂不支持");
    }

    private static void validateFileSize(byte[] content) {
        if (content == null || content.length > SiteConstants.MAX_FILE_SIZE)
        throw new FileTypeNotSupportException("文件大小不能超过60MB");
    }

    public static boolean isImageFile(FileMeta file) {
        return file.getFileType().startsWith("image");
    }
}
