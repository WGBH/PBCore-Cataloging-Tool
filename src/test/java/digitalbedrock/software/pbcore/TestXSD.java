package digitalbedrock.software.pbcore;

import java.io.File;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import digitalbedrock.software.pbcore.core.PBcoreValidator;

public class TestXSD {

    private PBcoreValidator val;

    public TestXSD() {
    }

    @Before
    public void Setup() throws URISyntaxException, SAXException {
        val = new PBcoreValidator();
    }

    @Test
    public void banks_1of5() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/banks_1of5.xml").getFile())));
    }

    @Test
    public void location_CMS_NUA_umatic00138() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/location_CMS_NUA_umatic00138.xml").getFile())));
    }

    @Test
    public void location_LTO_NUA_lto60004() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/location_LTO_NUA_lto60004.xml").getFile())));
    }

    @Test
    public void location_LTO_NUA_reel00445() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/location_LTO_NUA_reel00445.xml").getFile())));
    }

    @Test
    public void location_simple1_NUA_cass00321_01() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/location_simple1_NUA_cass00321_01.xml").getFile())));
    }

    @Test
    public void location_simple2_NUA_cass00321() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/location_simple2_NUA_cass00321.xml").getFile())));
    }

    @Test
    public void pbcore_archival_description() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/pbcore_archival_description.xml").getFile())));
    }

    @Test
    public void pbcore_asset_management() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/pbcore_asset_management.xml").getFile())));
    }

    @Test
    public void pbcore_collection() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/pbcore_collection.xml").getFile())));
    }

    @Test
    public void pbcore_digital_preservation() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/pbcore_digital_preservation.xml").getFile())));
    }

    @Test
    public void pbcore_digital_preservation_2() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/pbcore_digital_preservation_2.xml").getFile())));
    }

    @Test
    public void simple_description_document() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/simple_description_document.xml").getFile())));
    }

    @Test
    public void simple_instantiation_record() throws SAXException {
        Assert.assertTrue(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/simple_instantiation_record.xml").getFile())));
    }

    @Test
    public void _227457_pbcore() throws SAXException {
        Assert.assertFalse(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/227457_pbcore.xml").getFile())));
    }

    @Test
    public void _3087383_pbcore() throws SAXException {
        Assert.assertFalse(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/3087383_pbcore.xml").getFile())));
    }

    @Test
    public void eyesAbernathyRalph() throws SAXException {
        Assert.assertFalse(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/eyesAbernathyRalph.xml").getFile())));
    }

    @Test
    public void eyesAdamsVictoriaGray() throws SAXException {
        Assert.assertFalse(val.isValid(new File(Thread.currentThread().getContextClassLoader().getResource("samples/eyesAdamsVictoriaGray.xml").getFile())));
    }

}
