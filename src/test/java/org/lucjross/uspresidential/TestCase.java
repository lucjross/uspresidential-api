package org.lucjross.uspresidential;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;


/**
 * Created by lucas on 11/23/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=TestApplication.class)
@WebAppConfiguration
public abstract class TestCase {

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    ApplicationContext appContext;

    private static boolean loadedTestData = false;

    @Before
    public void preloadTestData() {
        if (! loadedTestData) {
            ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
            rdp.addScripts(
                    new ClassPathResource("/sql/schema-mysql.sql"),
                    new ClassPathResource("/sql/data-mysql.sql"));
            rdp.setSeparator("\n;\n");
            rdp.execute(jdbcTemplate.getDataSource());
            loadedTestData = true;
        }

    }

    @After
    public void tearDown() {

    }
}
