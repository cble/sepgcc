package com.sepgcc.site.service;

import com.sepgcc.site.dao.*;
import com.sepgcc.site.dao.entity.*;
import com.sepgcc.site.dto.FileMeta;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UploadService {

    private static final Logger log = Logger.getLogger(UploadService.class);

    @Resource
    private UploadDAO uploadDAO;
    @Resource
    private ProjectItemDAO projectItemDAO;
    @Resource
    private ProjectContactDAO projectContactDAO;
    @Resource
    private ProjectContactValueDAO projectContactValueDAO;
    @Resource
    private ProjectFileDAO projectFileDAO;
    @Resource
    private FileService fileService;

    public int upload(UploadSubmit submit, int userId) throws IllegalArgumentException {
        try {
            int projectId = validateAndGetProjectId(submit);

            // TODO transacation
            UploadDO uploadDO = new UploadDO(projectId, userId);
            int uploadId = uploadDAO.insert(uploadDO);
            Validate.isTrue(uploadId > 0, "系统异常，请重试");

            for (Map.Entry<String, String> entry : submit.getContacts().entrySet()) {
                if (StringUtils.isBlank(entry.getValue())) {
                    continue;
                }

                int projectContactId = NumberUtils.toInt(entry.getKey());
                ProjectContactValueDO o = new ProjectContactValueDO(projectId, projectContactId, uploadId, entry.getValue());
                int insertId = projectContactValueDAO.insert(o);
                Validate.isTrue(insertId > 0, "系统异常，请重试");
            }

            for (Map.Entry<String, List<String>> entry : submit.getItems().entrySet()) {
                int projectItemId = NumberUtils.toInt(entry.getKey());
                for (String fileId : entry.getValue()) {
                    FileMeta file = fileService.getFile(fileId, userId, false);
                    Validate.notNull("文件上传失败，请退出后重试");
                    ProjectFileDO o = new ProjectFileDO(projectId, projectItemId, uploadId, file.getId());
                    int insertId = projectFileDAO.insert(o);
                    Validate.isTrue(insertId > 0, "系统异常，请重试");
                }
            }

            return uploadId;
        } catch (IllegalArgumentException e) {
            log.warn(String.format("upload fail: %s", e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error("upload error", e);
            throw new IllegalArgumentException("未知错误");
        }
    }

    private int validateAndGetProjectId(UploadSubmit submit) throws IllegalArgumentException {
        Validate.notNull(submit, "请求不正确");
        Validate.isTrue(submit.getContacts() != null && submit.getContacts().size() > 0, "联系人信息不能为空");
        Validate.isTrue(submit.getItems() != null && submit.getItems().size() > 0, "文件列表不能为空");
        int projectId1 = validateItems(submit.getItems());
        int projectId2 = validateContacts(submit.getContacts());
        Validate.isTrue(projectId1 == projectId2 && projectId1 > 0, "请求不正确");
        return projectId1;
    }

    private int validateContacts(Map<String, String> contacts) throws IllegalArgumentException {
        int projectId = 0;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            ProjectContactDO projectContactDO = projectContactDAO.loadById(NumberUtils.toInt(entry.getKey()));
            Validate.notNull(projectContactDO, "项目已过期，请退出后重试");
            Validate.isTrue(!projectContactDO.isRequired() || StringUtils.isNotBlank(entry.getValue()), "必要联系人信息缺失，请检查");
            projectId = projectContactDO.getProjectId();
        }
        return projectId;
    }

    private int validateItems(Map<String, List<String>> items) throws IllegalArgumentException {
        int projectId = 0;
        for (Map.Entry<String, List<String>> entry : items.entrySet()) {
            ProjectItemDO projectItemDO = projectItemDAO.loadById(NumberUtils.toInt(entry.getKey()));
            Validate.notNull(projectItemDO, "项目已过期，请退出后重试");
            Validate.isTrue(!projectItemDO.isRequired() || CollectionUtils.isNotEmpty(entry.getValue()), "必要文件缺失，请检查");
            projectId = projectItemDO.getProjectId();
        }
        return projectId;
    }
}
