package com.example.pep.iot;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * lucene测试
 *
 * @author LiuGang
 * @since 2022-04-18 16:58
 */
@Slf4j
public class LuceneTests {


    @Test
    public void index() throws IOException {

        List<Document> documentList = new ArrayList<>();
        String desc = "Students should be allowed to go out with their friends, but not allowed to drink beer";

        Document document = new Document();
        document.add(new StringField("title", "Students should be allowed", Field.Store.YES));
        document.add(new TextField("content", desc, Field.Store.YES));
        documentList.add(document);

        Document second = new Document();
        String secondDesc = "My friend Jerry went to school to see his students but found them drunk which is not allowed";
        second.add(new StringField("title", "My friend Jerry went to school", Field.Store.YES));
        second.add(new TextField("content", secondDesc, Field.Store.YES));
        documentList.add(second);

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path path = Paths.get("/Users/pep/Desktop/lucene");
        FSDirectory directory = FSDirectory.open(path);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.addDocuments(documentList);
        indexWriter.commit();
//        //删除文档二
//        indexWriter.deleteDocuments(new Term("content", "school"));
//        //再次提交
//        indexWriter.commit();
        indexWriter.close();
    }

    @Test
    public void search() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("id", analyzer);
        Query query = queryParser.parse("should");
//        TermQuery termQuery= new TermQuery(new Term("title", "学习"));
        Path path = Paths.get("/Users/pep/Desktop/lucene");
        FSDirectory directory = FSDirectory.open(path);
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        TopDocs docs = indexSearcher.search(query, 10);
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = indexSearcher.doc(docId);
            System.out.println(document);
        }


        reader.close();

    }


}
