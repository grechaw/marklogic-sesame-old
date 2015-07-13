package com.marklogic.semantics.sesame.config;

import org.junit.Ignore;
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

    @Ignore
    public void testGetRepository() throws Exception {
        MarkLogicRepositoryConfig config = new MarkLogicRepositoryConfig();
        //config.setMethod(MarkLogicRepositoryConfig.HTTP_GET);
        //config.setURL(this.service);
        //config.addParameter("apikey", this.apikey);

        RepositoryFactory factory = new MarkLogicRepositoryFactory();
        Repository repo = factory.getRepository(config);

    }
}