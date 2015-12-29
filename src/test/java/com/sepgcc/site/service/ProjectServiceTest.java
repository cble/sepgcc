package com.sepgcc.site.service;

import com.google.common.collect.Lists;
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
        Project project = projectService.loadById(1, Lists.newArrayList(0, 1));
        Assert.notNull(project, "oops...");
    }
}
