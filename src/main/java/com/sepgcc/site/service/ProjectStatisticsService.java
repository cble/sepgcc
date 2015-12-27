package com.sepgcc.site.service;

import com.google.common.collect.Lists;
import com.sepgcc.site.dto.*;
import com.sepgcc.site.utils.ExcelUtils;
import jxl.biff.DisplayFormat;
import jxl.write.NumberFormats;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProjectStatisticsService {

    private static final Logger log = Logger.getLogger(ProjectStatisticsService.class);

    private static final String firstColumnAttributeName = "nickname";

    @Resource
    private UploadService uploadService;
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;

    public FileMeta generateStatisticsFile(int projectId) {
        try {
            Project project = projectService.loadById(projectId);
            List<Upload> uploadList = uploadService.getUploadByProjectId(projectId);
            if (CollectionUtils.isNotEmpty(uploadList)) {
                ExcelUtils excelUtils = initExcelUtils(project);
                List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
                for (Upload upload : uploadList) {
                    User user = userService.loadById(upload.getUserId());
                    if (user == null) {
                        continue;
                    }

                    Map<String, String> row = new HashMap<String, String>();
                    row.put(firstColumnAttributeName, user.getNickname());
                    for (ProjectContactValue projectContactValue : upload.getContactValueList()) {
                        row.put("contact" + projectContactValue.getId(), projectContactValue.getContactValue());
                    }
                    for (ProjectItemValue projectItemValue : upload.getItemValueList()) {
                        row.put("item" + projectItemValue.getId(), String.valueOf(projectItemValue.getFileMetaList().size()));
                    }
                    rowList.add(row);
                }
                FileMeta reportFile = new FileMeta();
                reportFile.setFileName(project.getName() + ".xls");
                reportFile.setFileType("application/x-download");
                reportFile.setBytes(excelUtils.createExcel(rowList));
                return reportFile;
            }
        } catch (Exception e) {
            log.error("generateStatisticsFile error", e);
        }
        return null;
    }

    private ExcelUtils initExcelUtils(Project project) {
        List<String> columnTitles = Lists.newArrayList("单位名");
        List<String> columnAttriubteNames = Lists.newArrayList(firstColumnAttributeName);
        List<DisplayFormat> formats = Lists.newArrayList(NumberFormats.TEXT);
        for (ProjectContact projectContact : project.getProjectContactList()) {
            columnTitles.add(projectContact.getName());
            columnAttriubteNames.add("contact" + projectContact.getId());
            formats.add(NumberFormats.TEXT);
        }
        for (ProjectItem projectItem : project.getProjectItemList()) {
            columnTitles.add(projectItem.getName());
            columnAttriubteNames.add("item" + projectItem.getId());
            formats.add(NumberFormats.TEXT);
        }
        return new ExcelUtils(
                (String[]) columnTitles.toArray(),
                (String[]) columnAttriubteNames.toArray(),
                (DisplayFormat[]) formats.toArray(),
                new Hashtable()
        );
    }
}
