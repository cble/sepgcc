package com.sepgcc.site.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sepgcc.site.dao.*;
import com.sepgcc.site.dao.entity.*;
import com.sepgcc.site.dto.FileMeta;
import com.sepgcc.site.dto.ProjectItemValue;
import com.sepgcc.site.dto.Upload;
import com.sepgcc.site.dto.UploadSubmit;
import com.sepgcc.site.utils.ProjectUtils;
import com.sepgcc.site.utils.UploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
    @Resource
    private TransactionTemplate transactionTemplate;

    public Upload loadById(int uploadId, int userId) {
        Upload upload = UploadUtils.toUpload(uploadDAO.loadById(uploadId));
        if (upload != null) {
            upload.setContactValueList(ProjectUtils.toProjectContactValueList(
                    projectContactDAO.queryByProjectId(upload.getProjectId()),
                    projectContactValueDAO.queryByUploadId(uploadId)
            ));

            List<ProjectFileDO> projectFileList = projectFileDAO.queryByUploadId(uploadId);
            upload.setItemValueList(ProjectUtils.toProjectItemValueList(
                    projectItemDAO.queryByProjectId(upload.getProjectId()),
                    projectFileList,
                    fileService.mGetFile(Lists.transform(projectFileList, new Function<ProjectFileDO, String>() {
                        @Override
                        public String apply(ProjectFileDO projectFileDO) {
                            return projectFileDO.getFileId();
                        }
                    }), userId)
            ));
        }
        return upload;
    }

    public List<Upload> queryByUserIdWithLimit(int userId, int index, int limit) {
        List<UploadDO> uploadDOList = uploadDAO.queryByUserIdWithLimit(userId, index, limit);
        return Lists.transform(uploadDOList, new Function<UploadDO, Upload>() {
            @Override
            public Upload apply(UploadDO uploadDO) {
                return UploadUtils.toUpload(uploadDO);
            }
        });
    }

    public int countByUserId(int userId) {
        return uploadDAO.countByUser(userId);
    }

    public int upload(final UploadSubmit submit, final int userId) throws IllegalArgumentException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int projectId = validateAndGetProjectId(submit);
                    int uploadId = createUpload(projectId, userId);
                    bindContact(projectId, uploadId, submit);
                    bindFile(projectId, uploadId, submit, userId);
                    return uploadId;
                }
            });
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

    private int createUpload(int projectId, int userId) {
        UploadDO uploadDO = new UploadDO(projectId, userId);
        int uploadId = uploadDAO.insert(uploadDO);
        Validate.isTrue(uploadId > 0, "系统异常，请重试");
        return uploadId;
    }

    private void bindContact(int projectId, int uploadId, UploadSubmit submit) {
        for (Map.Entry<String, String> entry : submit.getContacts().entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                continue;
            }

            int projectContactId = NumberUtils.toInt(entry.getKey());
            ProjectContactValueDO o = new ProjectContactValueDO(projectId, projectContactId, uploadId, entry.getValue());
            int insertId = projectContactValueDAO.insert(o);
            Validate.isTrue(insertId > 0, "系统异常，请重试");
        }
    }

    private void bindFile(int projectId, int uploadId, UploadSubmit submit, int userId) {
        for (Map.Entry<String, List<String>> entry : submit.getItems().entrySet()) {
            int projectItemId = NumberUtils.toInt(entry.getKey());
            for (String fileId : entry.getValue()) {
                FileMeta file = fileService.getFile(fileId, userId, false);
                Validate.notNull("文件上传失败，请退出后重试");
                ProjectFileDO o = new ProjectFileDO(projectId, projectItemId, uploadId, file.getFileId());
                int insertId = projectFileDAO.insert(o);
                Validate.isTrue(insertId > 0, "系统异常，请重试");
            }
        }
    }
}
