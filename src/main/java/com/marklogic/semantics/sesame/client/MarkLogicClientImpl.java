package com.marklogic.semantics.sesame.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * implements client using MarkLogic java api client
 *
 * @author James Fuller
 */
public class MarkLogicClientImpl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String host = "localhost";

    private int port = 8200;

    private String user = "admin";

    private String password = "admin";

    private String auth = "DIGEST";

    private long start=1;

    private long pageLength=1000;

    protected static DatabaseClientFactory.Authentication authType = DatabaseClientFactory.Authentication.valueOf(
            "DIGEST"
    );

    protected DatabaseClient databaseClient;

    public MarkLogicClientImpl() {
        this.databaseClient = DatabaseClientFactory.newClient(host, port, user, password, authType);
    }

    public MarkLogicClientImpl(String host, int port, String user, String password, String auth) {
        this.databaseClient = DatabaseClientFactory.newClient(host, port, user, password, DatabaseClientFactory.Authentication.valueOf(auth));
    }

    public static DatabaseClientFactory.Authentication getAuthType() {
        return authType;
    }

    public void setAuthType(DatabaseClientFactory.Authentication authType) {
        MarkLogicClientImpl.authType = authType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
        this.authType = DatabaseClientFactory.Authentication.valueOf(
                auth
        );
    }

    public DatabaseClient getDatabaseClient() {
        return databaseClient;
    }

    public InputStream performSPARQLQuery(String queryString) throws JsonProcessingException {
        SPARQLQueryManager smgr = getDatabaseClient().newSPARQLQueryManager();
        SPARQLQueryDefinition qdef = smgr.newQueryDefinition(queryString);
        JacksonHandle results = smgr.executeSelect(qdef, new JacksonHandle());
        ObjectMapper objectMapper = new ObjectMapper();
        return new ByteArrayInputStream(objectMapper.writeValueAsBytes(results.get()));
    }
}