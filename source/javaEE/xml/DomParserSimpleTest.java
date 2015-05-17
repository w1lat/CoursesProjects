package javaEE.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Таня on 17.05.2015.
 */
public class DomParserSimpleTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(new File("temp/file.xml"));
        Element root = document.getDocumentElement();
        //showElementInfo(root);
        String string = "";
        showXML(root);


    }

    public static void showXML(Element root) {

        NamedNodeMap attributes;
        if(root.hasAttributes()){
            System.out.print("<" + root.getTagName());
            attributes = root.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(" " + attributes.item(i).getNodeName() + "=" + "\"" + attributes.item(i).getNodeValue() + "\"");
            }
            System.out.print(">");
        }else
        System.out.printf("<%s>", root.getTagName());

        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                Text text = (Text) node;
                String content = text.getNodeValue();
                System.out.print(content);
            }else {
                Element element = (Element) node;
                showXML(element);
            }
        }
        System.out.printf("</%s>", root.getTagName());
    }



    public static void showElementInfo(Element element){
        System.out.println(element.getTagName());
    }
}
