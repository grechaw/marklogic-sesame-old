package com.marklogic.semantics.sesame;

import com.marklogic.semantics.sesame.client.MarkLogicClient;
import com.marklogic.semantics.sesame.query.MarkLogicTupleQuery;
import info.aduna.iteration.ConvertingIteration;
import info.aduna.iteration.ExceptionConvertingIteration;
import info.aduna.iteration.Iteration;
import org.openrdf.IsolationLevel;
import org.openrdf.model.*;
import org.openrdf.query.*;
import org.openrdf.repository.*;
import org.openrdf.rio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import static org.openrdf.query.QueryLanguage.SPARQL;

public class MarkLogicRepositoryConnection implements RepositoryConnection {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String EVERYTHING = "CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o }";

    private static final String EVERYTHING_WITH_GRAPH = "SELECT * WHERE {  ?s ?p ?o . OPTIONAL { GRAPH ?ctx { ?s ?p ?o } } }";

    private static final String SOMETHING = "ASK { ?s ?p ?o }";

    private static final String NAMEDGRAPHS = "SELECT DISTINCT ?_ WHERE { GRAPH ?_ { ?s ?p ?o } }";

    private final boolean quadMode;

    private MarkLogicClient client;

    private MarkLogicRepository repository;

    public MarkLogicRepositoryConnection(MarkLogicRepository repository,MarkLogicClient client) {
        this(repository, client, false);
    }

    public MarkLogicRepositoryConnection(MarkLogicRepository repository, MarkLogicClient client, boolean quadMode) {
        this.client = client;
        this.repository=repository;
        this.quadMode=quadMode;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    @Override
    public ParserConfig getParserConfig() {
        return null;
    }

    @Override
    public void setParserConfig(ParserConfig config) {
    }

    @Override
    public ValueFactory getValueFactory() {
        return null;
    }

    @Override
    public boolean isOpen() throws RepositoryException {
        return false;
    }

    @Override
    public void close() throws RepositoryException {
    }

    @Override
    public Query prepareQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }

    // prepareQuery entrypoint
    @Override
    public Query prepareQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    // prepareTupleQuery
    public TupleQuery prepareTupleQuery(String queryString) throws RepositoryException, MalformedQueryException {
        return prepareTupleQuery(QueryLanguage.SPARQL,queryString);
    }

    @Override
    public TupleQuery prepareTupleQuery(QueryLanguage ql, String queryString) throws RepositoryException, MalformedQueryException {
        if (QueryLanguage.SPARQL.equals(ql))
            return prepareTupleQuery(ql, queryString, "");
        throw new UnsupportedQueryLanguageException("Unsupported query language " + ql);
    }

    @Override
    public TupleQuery prepareTupleQuery(QueryLanguage ql, String queryString, String baseURI) throws RepositoryException, MalformedQueryException {
        if (QueryLanguage.SPARQL.equals(ql)) {
            return (TupleQuery)new MarkLogicTupleQuery(client,baseURI,queryString);
        }
        throw new UnsupportedQueryLanguageException("Unsupported query language " + ql);
    }

    //prepareGraphQuery
    @Override
    public GraphQuery prepareGraphQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }
    @Override
    public GraphQuery prepareGraphQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    //prepareBooleanQuery
    @Override
    public BooleanQuery prepareBooleanQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }
    @Override
    public BooleanQuery prepareBooleanQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    //prepareUpdate
    @Override
    public Update prepareUpdate(QueryLanguage ql, String update) throws RepositoryException, MalformedQueryException {
        return null;
    }
    @Override
    public Update prepareUpdate(QueryLanguage ql, String update, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    //
    @Override
    public RepositoryResult<Resource> getContextIDs() throws RepositoryException {
        try {
            TupleQuery query = prepareTupleQuery(SPARQL, NAMEDGRAPHS, "");
            TupleQueryResult result = query.evaluate();
            return new RepositoryResult<Resource>(
                    new ExceptionConvertingIteration<Resource, RepositoryException>(
                            new ConvertingIteration<BindingSet, Resource, QueryEvaluationException>(result) {

                                @Override
                                protected Resource convert(BindingSet bindings)
                                        throws QueryEvaluationException
                                {
                                    return (Resource)bindings.getValue("_");
                                }
                            })
                    {

                        @Override
                        protected RepositoryException convert(Exception e) {
                            return new RepositoryException(e);
                        }
                    });
        }
        catch (MalformedQueryException e) {
            throw new RepositoryException(e);
        }
        catch (QueryEvaluationException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public RepositoryResult<Statement> getStatements(Resource subj, URI pred, Value obj, boolean includeInferred, Resource... contexts) throws RepositoryException {
        return null;
    }

    //
    @Override
    public boolean hasStatement(Resource subj, URI pred, Value obj, boolean includeInferred, Resource... contexts) throws RepositoryException {
        return false;
    }
    @Override
    public boolean hasStatement(Statement st, boolean includeInferred, Resource... contexts) throws RepositoryException {
        return false;
    }
    @Override
    public void exportStatements(Resource subj, URI pred, Value obj, boolean includeInferred, RDFHandler handler, Resource... contexts) throws RepositoryException, RDFHandlerException {

    }

    //
    @Override
    public void export(RDFHandler handler, Resource... contexts) throws RepositoryException, RDFHandlerException {

    }

    @Override
    public long size(Resource... contexts) throws RepositoryException {
        return 0;
    }

    @Override
    public boolean isEmpty() throws RepositoryException {
        return false;
    }

    @Override
    public boolean isAutoCommit() throws RepositoryException {
        return false;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws RepositoryException {

    }

    @Override
    public boolean isActive() throws UnknownTransactionStateException, RepositoryException {
        return false;
    }

    @Override
    public IsolationLevel getIsolationLevel() {
        return null;
    }

    @Override
    public void setIsolationLevel(IsolationLevel level) throws IllegalStateException {

    }

    @Override
    public void begin() throws RepositoryException {

    }

    @Override
    public void begin(IsolationLevel level) throws RepositoryException {

    }

    @Override
    public void commit() throws RepositoryException {

    }

    @Override
    public void rollback() throws RepositoryException {

    }

    @Override
    public void add(InputStream in, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(Reader reader, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(URL url, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(File file, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(Resource subject, URI predicate, Value object, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void add(Statement st, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void add(Iterable<? extends Statement> statements, Resource... contexts) throws RepositoryException {

    }

    @Override
    public <E extends Exception> void add(Iteration<? extends Statement, E> statements, Resource... contexts) throws RepositoryException, E {

    }

    @Override
    public void remove(Resource subject, URI predicate, Value object, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void remove(Statement st, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void remove(Iterable<? extends Statement> statements, Resource... contexts) throws RepositoryException {

    }

    @Override
    public <E extends Exception> void remove(Iteration<? extends Statement, E> statements, Resource... contexts) throws RepositoryException, E {

    }

    @Override
    public void clear(Resource... contexts) throws RepositoryException {

    }

    //
    @Override
    public RepositoryResult<Namespace> getNamespaces() throws RepositoryException {
        return null;
    }
    @Override
    public String getNamespace(String prefix) throws RepositoryException {
        return null;
    }
    @Override
    public void setNamespace(String prefix, String name) throws RepositoryException {

    }
    @Override
    public void removeNamespace(String prefix) throws RepositoryException {

    }
    @Override
    public void clearNamespaces() throws RepositoryException {

    }
}
