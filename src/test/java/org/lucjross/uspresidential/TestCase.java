package org.lucjross.uspresidential;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.FileCopyUtils;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.Arrays;


/**
 * Created by lucas on 11/23/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
@WebAppConfiguration
public abstract class TestCase {

    private static final Logger LOGGER = Logger.getLogger(TestCase.class);

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    ApplicationContext appContext;

    private void update(String resourcePath, Object[] params) throws Exception {
        Resource sqlResource = appContext.getResource(resourcePath);
        String sql = new String(FileCopyUtils.copyToByteArray(sqlResource.getInputStream()));
        LOGGER.info("Executing sql=[" + sql + "], params=[" + Arrays.toString(params) + "]");
        jdbcTemplate.update(sql, params);
    }

    private void executeScript(String resourcePath) throws Exception {
        Resource sqlResource = appContext.getResource(resourcePath);
        String multiSql = new String(FileCopyUtils.copyToByteArray(sqlResource.getInputStream()));
        String[] splitSql = multiSql.split(";(\\r\\n?|\\n)");
        for (String sql : splitSql) {
            LOGGER.info("Executing sql=[" + sql + "]");
            jdbcTemplate.execute(sql);
        }
    }

    @After
    public void tearDown() {

    }
}
