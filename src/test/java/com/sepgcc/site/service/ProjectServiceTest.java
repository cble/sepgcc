package com.sepgcc.site.service;

import com.sepgcc.site.constants.SiteConstants;
import com.sepgcc.site.dto.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/spring/appcontext-*.xml"
})
public class ProjectServiceTest {

    @Resource
    private ProjectService projectService;

    @Test
    public void testLoadProjectById() throws Exception {
        Project project = projectService.loadById(1, 99, SiteConstants.ADMIN_AVAILABLE_PROJECT_STATUS);
        Assert.notNull(project, "oops...");
    }
}
