/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.ToolChain;
import model.ToolScript;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author 28851274
 */
public class ConfigParser {

    /**
     * Load tool script list from default source:<br/>
     * tool_script.xml
     *
     * @return
     */
    public List<ToolChain> loadToolScript() {
        String path = System.getProperty("ai-config");
        if (path == null) {
            System.err.println(" doesn't input -Dai-config=file path");
            throw new RuntimeException("doesn't input -Dai-config=file path ");
        }
        return loadToolScript(path);
    }

    /**
     * Load tool script list from path file source: <br />
     *
     * @param path tool script file path
     * @return
     */
    public List<ToolChain> loadToolScript(String path) {
        if (path == null) {
            throw new NullPointerException(" no available path");
        }
        try {

            File fXmlFile = new File(path);
            if (!fXmlFile.exists()) {
                throw new NullPointerException(" file path doesn't exist");
            }

            List<ToolChain> li = new ArrayList<ToolChain>();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList groupList = doc.getElementsByTagName("group");

            Element groupElement;
            NodeList toolList;
            Element toolElement;
            for (int temp = 0; temp < groupList.getLength(); temp++) {

                groupElement = (Element) groupList.item(temp);
                

                String name = groupElement.getAttribute("name");
                String platform = groupElement.getAttribute("platform");
                if (name == null || name.trim().isEmpty()) {
                    throw new RuntimeException(" no name attribute in groud tag.");
                }
                toolList = groupElement.getElementsByTagName("tool");
                ToolScript[] tl = new ToolScript[toolList.getLength()];
                
                for (int j = 0; j < toolList.getLength(); j++) {
                    toolElement = (Element) toolList.item(j);
                    if (toolElement.getNodeType() == Node.ELEMENT_NODE) {
                        toolElement = (Element) toolElement;
                        String command = toolElement.getTextContent();
                        String parameters = toolElement.getAttribute("parameters");
                        String scriptName = toolElement.getAttribute("name");
                        System.out.println("command : " + toolElement.getTextContent());
                        ToolScript ts = new ToolScript(scriptName, command, parameters.split(" "));
                        tl[j] = ts;
                    }
                }

                li.add(new ToolChain(name.trim(), ToolChain.fromString(platform), tl));
            }

            return li;

        } catch (IOException | NullPointerException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
            throw new RuntimeException(" parse file error ", e);
        }

    }
}
