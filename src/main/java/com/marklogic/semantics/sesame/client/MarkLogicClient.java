package com.marklogic.semantics.sesame.client;

import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.query.resultio.TupleQueryResultParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MarkLogicClient {


	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected static final Charset UTF8 = Charset.forName("UTF-8");

	protected  static final TupleQueryResultFormat format = TupleQueryResultFormat.JSON;

	private static Executor executor = Executors.newCachedThreadPool();

	private MarkLogicClientImpl mcimpl;

	private ValueFactory f;

	public MarkLogicClient() {
		this.mcimpl = new MarkLogicClientImpl();
		this.f = new ValueFactoryImpl();
	}


	public MarkLogicClient(String host, int port, String user, String password,String auth) {
		this.mcimpl = new MarkLogicClientImpl(host,port,user,password,auth);
		this.f = new ValueFactoryImpl();
	}

	public ValueFactory getValueFactory() {
		return f;
	}

	public TupleQueryResult sendTupleQuery(String queryString) throws IOException {
		InputStream stream = mcimpl.performSPARQLQuery(queryString);
		TupleQueryResultParser parser = QueryResultIO.createParser(format, getValueFactory());
		MarkLogicTupleResult tRes = new MarkLogicTupleResult(parser,stream);
		execute(tRes);
		return tRes;
	}

	protected void execute(Runnable command) {
		executor.execute(command);
	}
}
