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

    @Before
    public void setUp() throws Exception {

        // don't want to mess with the real DB
        String userName = jdbcTemplate.getDataSource().getConnection().getMetaData().getUserName();
        Assert.assertTrue(userName.endsWith("@localhost") || userName.endsWith("@127.0.0.1"));

        // reset the schema
        executeScript("classpath:dropSchema.sql");
        executeScript("classpath:createSchema.sql");

        // insert data
        update("classpath:insertPresident.sql", new Object[] {"guy1", "test1", 1, "url1"});
        update("classpath:insertPresident.sql", new Object[] {"guy2", "test2", 2, "url2"});

        update("classpath:insertEvent.sql", new Object[] {
                "desc1", 1, 10, "major", "foreign", "summary1", Date.valueOf("1970-1-1"), Date.valueOf("1970-1-2"), "url1"});
        update("classpath:insertEvent.sql", new Object[] {
                "desc2", 2, 5, "minor", "domestic", "summary2", Date.valueOf("1970-1-3"), Date.valueOf("1970-1-4"), "url2"});
        update("classpath:insertEvent.sql", new Object[] {
                "desc3", 1, 0, "major", "appointment", "summary3", Date.valueOf("1970-1-5"), Date.valueOf("1970-1-6"), "url3"});
        update("classpath:insertEvent.sql", new Object[] {
                "desc4", 2, 5, "minor", "misc", "summary4", Date.valueOf("1970-1-7"), Date.valueOf("1970-1-8"), "url4"});
    }

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
