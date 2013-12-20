/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sony.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ToolChain;
import model.xml.ConfigParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author 28851274
 */
public class XmlParserTest {
    
    private boolean testFileCreate = false;
    private File testFile;
    
    public XmlParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<config>\n " +
"<group  name=\"test1\" >\n" +
"	<tool name=\"1001\" parameters=\"\" >\n" +
"t32mqdsp6-qt -c /opt/t32/config_sim.t32, /opt/t32/SEMC/Start_8974_trace_loader.cmm /opt/projects/amss/rhine-8974-cmcc  \n" +
"	</tool>\n" +
"	<tool name=\"2001\">\n" +
"		dpkg -l |grep semc \n" +
"	</tool>\n" +
"</group>\n" +
                "</config>";
        testFile = new File("./test.xml");
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(testFile);
            fos.write(xmlContent.getBytes());
            fos.flush();
            testFileCreate = true;
        } catch(IOException e) {
            
        } finally {
            if (fos != null)
                try {
                    fos.close();
            } catch (IOException ex) {
                Logger.getLogger(XmlParserTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @After
    public void tearDown() {
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testLoadToolScript() {
        assert(testFileCreate == true);
        ConfigParser cp = new ConfigParser();
        List<ToolChain> l = cp.loadToolScript(testFile.getAbsolutePath());
        assert(l.size() == 1);
        assert(l.get(0).getChains().length == 2);
         assert(l.get(0).getName().equals("test1"));
    }
}
