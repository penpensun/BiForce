/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;
import org.jdom2.input.*;
import org.jdom2.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author penpen926
 */
public class TestJDom {
    String xmlInput = "../../data/testdata/xml_test/test_input.xml";
    @Test
    public void testReadXml(){
        System.out.println("(biforce.graphs.TestJDom.testReadXml) Test starts.");
        try{
        SAXBuilder sb = new SAXBuilder();
        Document myXml = sb.build(new File(xmlInput));
        Element root = myXml.getRootElement();
        System.out.println("The name of the root element: "+root.getName());
        assertEquals("dataConfig",root.getName());
        root.getChildren();
        /* Get the child element of dataSource. */
        Element dataSource = root.getChild("dataSource");
        assertNotNull(dataSource); /* The retrieved element must not be true.*/
        System.out.println("The name of the child node1: "+dataSource.getName());
        String urlType = dataSource.getAttributeValue("url");
        System.out.println("The url of dataSource is:  "+urlType);
        /* Get the child element of document. */
        Element doc = root.getChild("document");
        assertNotNull(doc); /* The retrieved element must not be true. */
        System.out.println("The name of the child node2: "+dataSource.getName());
        String attrLevels = dataSource.getAttributeValue("levels");
        String nonExistingAttr = dataSource.getAttributeValue("non-existing");
        System.out.println("The value of attribute levels: "+attrLevels);
        System.out.println("The value of non-existing attribute:  "+nonExistingAttr);
        Element content = root.getChild("content");
        System.out.println("This is the content of the content element:  "+content.getContent(0).getValue());
        }catch(IOException e){
            System.out.println("(biforce.graphs.TestJDom.testReadXml)");
        }
        catch(JDOMException e){
            System.out.println("(biforce.graphs.TestJDom.testReadXml)");
        }
        
        double nan = Double.parseDouble("NaN");
        System.out.println("System.out.println:  "+Double.NaN);
        System.out.println(nan);
        System.out.println("(biforce.graphs.TestJDom.testReadXml) Test ends");
    }
}
