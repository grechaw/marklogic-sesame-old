package com.marklogic.semantics.sesame;

import org.junit.*;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarkLogicRepositoryConnectionTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Repository rep;

    protected RepositoryConnection conn;

    protected ValueFactory f;

    @Before
    public void setUp()
            throws Exception
    {
        logger.debug("setting up test");
        this.rep = new MarkLogicRepository("localhost",8200,"admin","admin","DIGEST");
        rep.initialize();

        f = rep.getValueFactory();
        conn = rep.getConnection();

        logger.debug("test setup complete.");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown()
            throws Exception
    {
        logger.debug("tearing down...");
        conn.close();
        conn = null;

        rep.shutDown();
        rep = null;

        logger.debug("tearDown complete.");
    }
    @Test
    public void testSPARQLQuery()
            throws Exception {

        rep.shutDown();
        rep.initialize();

        Assert.assertTrue(conn != null);
        String queryString = "select ?s ?p ?o { ?s ?p ?o } limit 2 ";
        TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
        TupleQueryResult results = tupleQuery.evaluate();

        results.hasNext();
        BindingSet bindingSet = results.next();

        Value sV = bindingSet.getValue("s");
        Value pV = bindingSet.getValue("p");
        Value oV = bindingSet.getValue("o");

        Assert.assertEquals("http://example.org/marklogic/people/Jack_Smith", sV.stringValue());
        Assert.assertEquals("http://example.org/marklogic/predicate/livesIn", pV.stringValue());
        Assert.assertEquals("Glasgow", oV.stringValue());

        results.hasNext();
        BindingSet bindingSet1 = results.next();

        Value sV1 = bindingSet1.getValue("s");
        Value pV1 = bindingSet1.getValue("p");
        Value oV1 = bindingSet1.getValue("o");

        Assert.assertEquals("http://example.org/marklogic/people/Jane_Smith", sV1.stringValue());
        Assert.assertEquals("http://example.org/marklogic/predicate/livesIn", pV1.stringValue());
        Assert.assertEquals("London", oV1.stringValue());

    }


    @Ignore
    public void testContextIDs()
            throws Exception
    {

        MarkLogicRepository mr = new MarkLogicRepository();
        mr.initialize();
        MarkLogicRepositoryConnection con = (MarkLogicRepositoryConnection) mr.getConnection();
        RepositoryResult<Statement> result = con.getStatements(RDF.TYPE, RDF.TYPE, null, true);
        try {
            Assert.assertTrue("result should not be empty", result.hasNext());
        }
        finally {
            result.close();
        }

        result = con.getStatements(RDF.TYPE, RDF.TYPE, null, false);
        try {
            Assert.assertFalse("result should be empty", result.hasNext());
        }
        finally {
            result.close();
        }
    }


}
