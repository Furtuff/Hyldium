package com.tuff.hyldium.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.model.ItemModel;

public class Search {
	public static Directory index;
	public static StandardAnalyzer analyzer;
	public static IndexWriter w;
	private final static int hitsPerPage = 20;
	
	public Search() {
		getIndex();
	}
	
	
	private StandardAnalyzer getAnalyser() {
		if(analyzer == null) {
			analyzer = new StandardAnalyzer();
		}
		return analyzer;
	}
	
	private Directory getIndex() {
		if (index == null) {
			index = new RAMDirectory();
		}
		return index;
	}

	public List<ItemModel> search(String arg) throws Exception {
		StandardAnalyzer analyzer = getAnalyser();

		// 1. create the index
		Directory index = getIndex();
		// 2. query
		String querystr = arg.length() > 0 ? arg + "*~" : "lucene";

		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.
		String[] fields = {"title","ref"};
		
		Query tq = new QueryParser("title", analyzer).parse(querystr);
		Query rq = new QueryParser("ref",analyzer).parse(querystr);
		BooleanQuery bq = new BooleanQuery.Builder()
		.add(tq,BooleanClause.Occur.SHOULD)
		.add(rq,BooleanClause.Occur.SHOULD)
		.build();
		// 3. search
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(bq, hitsPerPage);
		ScoreDoc[] hits = docs.scoreDocs;

		// 4. display results
		// 4. display results
		List<ItemModel> list = new ArrayList<>();
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			list.add(new ItemModel(Dao.getItem(Long.valueOf(d.get("isbn")))));
		}

		reader.close();
		return list;
	}

	public static void addDocList(IndexWriter wr,String title,String ref, String isbn) throws IOException {
		Document doc = new Document();
		if(ref == null) {
			ref = "W";
		}
		if(title == null) {
			title = "W";
		}
		doc.add(new TextField("ref",ref,Field.Store.YES));
		doc.add(new TextField("title", title, Field.Store.YES));
		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		wr.addDocument(doc);
	}
	public static void addDoc(String title,String ref, String isbn) {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(index, config );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = new Document();
		doc.add(new TextField("ref",ref,Field.Store.YES));
		doc.add(new TextField("title", title, Field.Store.YES));
		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		try {
			indexWriter.addDocument(doc);
			indexWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void removeDoc(String isbn) {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(index, config );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Query queries = null;
		try {
			queries = new QueryParser("isbn", analyzer).parse(isbn);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			indexWriter.deleteDocuments(queries);
			indexWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
