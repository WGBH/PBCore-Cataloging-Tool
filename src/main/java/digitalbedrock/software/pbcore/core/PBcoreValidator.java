package digitalbedrock.software.pbcore.core;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

public class PBcoreValidator {

    private static final URL XSD = Thread.currentThread().getContextClassLoader().getResource("pbcore-2.1.xsd");
    private final Validator validator;

    public PBcoreValidator() throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(XSD);
        validator = schema.newValidator();
    }

    public boolean isValid(File xml) {
        try {
            this.validate(xml);
            return true;
        } catch (SAXException | IOException ex) {
        }
        return false;
    }

    public void validate(File xml) throws SAXException, IOException {
        validator.validate(new StreamSource(xml));
    }

    public void validate(String xml) throws SAXException, IOException {
        validator.validate(new StreamSource(new StringReader(xml)));
    }

}
