package com.marklogic.semantics.sesame.config;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.config.RepositoryFactory;

/**
 * Created by jfuller on 7/10/15.
 */
public class MarkLogicRepositoryFactoryTest {

    @Ignore
    public void testGetRepositoryType() throws Exception {

    }

    @Ignore
    public void testGetConfig() throws Exception {

    }

    @Test
    public void testGetRepository() throws Exception {
        MarkLogicRepositoryConfig config = new MarkLogicRepositoryConfig();

        config.setHost("localhost");
        config.setPort(8200);
        config.setUser("admin");
        config.setPassword("admin");
        config.setAuth("DIGEST");

        RepositoryFactory factory = new MarkLogicRepositoryFactory();
        Assert.assertEquals("marklogic:MarkLogicRepository",factory.getRepositoryType());
        Repository repo = factory.getRepository(config);

    }
}