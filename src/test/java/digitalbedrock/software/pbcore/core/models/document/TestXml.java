package digitalbedrock.software.pbcore.core.models.document;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Ignore
public class TestXml {

    IndexWriter iw;
    IndexReader ir;
    
    private Schema schema;

    
    @Before
    public void Setup() throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = factory.newSchema(Thread.currentThread().getContextClassLoader().getResource("pbcore-2.1.xsd"));
        
        IndexWriterConfig c=new IndexWriterConfig();
     
        Directory dir=FSDirectory.open(Paths.get("/tmp/y"));
        iw=new IndexWriter(dir,c);
        ir=DirectoryReader.open(dir);
      
    }
    
    
    @After
    public void x() throws IOException{
        iw.close();
        ir.close();
    }

    @Test
    public void testDescriptionDocument() throws JAXBException, IOException {
        JAXBContext ctx = JAXBContext.newInstance("digitalbedrock.software.pbcore.core.models.document");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);
        JAXBElement x = (JAXBElement) unmarshaller.unmarshal(new File(Thread.currentThread().getContextClassLoader().getResource("samples/banks_1of5.xml").getFile()));
        PbcoreDescriptionDocumentType pcore= (PbcoreDescriptionDocumentType)x.getValue();
        
        Document doc=new Document();
        doc.add(new TextField("path", "/kjhjksf/", Field.Store.YES) );
        doc.add(new TextField("title", "marco paulo cova", Field.Store.NO) );
        doc.add(new TextField("xpto", "zczxczxczc", Field.Store.NO) );
       
        iw.addDocument(doc);
        iw.commit();
        
          IndexSearcher is=new IndexSearcher(ir);
        
        
        Assert.assertTrue(x.getValue() instanceof PbcoreDescriptionDocumentType);
    }

    @Test
    public void testInstantiationDocument() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance("digitalbedrock.software.pbcore.core.models.document");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);
        JAXBElement x = (JAXBElement) unmarshaller.unmarshal(new File(Thread.currentThread().getContextClassLoader().getResource("samples/location_simple1_NUA_cass00321_01.xml").getFile()));
        Assert.assertTrue(x.getValue() instanceof InstantiationType);
    }

    @Test
    public void testCollection() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance("digitalbedrock.software.pbcore.core.models.document");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);
        JAXBElement x = (JAXBElement) unmarshaller.unmarshal(new File(Thread.currentThread().getContextClassLoader().getResource("samples/pbcore_collection.xml").getFile()));
        Assert.assertTrue(x.getValue() instanceof PbcoreCollectionType);
    }

}
