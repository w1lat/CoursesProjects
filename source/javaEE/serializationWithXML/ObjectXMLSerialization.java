package javaEE.serializationWithXML;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by Таня on 18.05.2015.
 */
public class ObjectXMLSerialization {

    public static final String PATHNAME = "temp/fileclass javaEE.serializationWithXML.SMS.xml";
    public Object object;

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException, NoSuchFieldException, InstantiationException {

        File file = new File(PATHNAME);
        SMS sms = (SMS) loadFromXML(file);
        System.out.println(sms.toString());
        SMS message = new SMS("hello");
        saveToXML(message);



    }

    public static void saveToXML(Object object) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("temp/file" + object.getClass() + ".xml");
        Writer writer = new FileWriter(file);
        writer.write("<?xml version=\"1.0\"?>");
        writer.flush();
        writer.close();
        writer = new FileWriter(file, true);
        writer.write("<" + object.getClass().toString().substring(6) + ">" + "\n");
        writer.flush();
        writer.close();
        packObject(object, file);
        Writer writer1 = new FileWriter(file, true);
        writer1.write("</" + object.getClass().toString().substring(6) + ">");
        writer1.flush();
        writer1.close();

    }

    public static void packObject(Object object, File file) {
        boolean hasDefConstructor = false;
        if(object != null) {

            Class cl = object.getClass();
            Constructor[] constructors = cl.getConstructors();
            for(Constructor c : constructors){
                if(c.getParameterTypes().length > 0){
                    hasDefConstructor = true;
                }
            }
            if(hasDefConstructor) {
                Writer writer = null;
                try {
                    writer = new FileWriter(file, true);
                    Field[] fields = object.getClass().getDeclaredFields();
                    for (Field f : fields) {
                        f.setAccessible(true);
                        String res = null;
                        if (f.get(object) != null&& !f.toString().contains("static")) {
                            switch (f.getType().toString()) {
                                case "int":
                                    res = ("<" + f.getName() + ">");
                                    System.out.println("int" + res + f.get(object).toString());
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "float":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "char":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n\r");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "double":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "boolean":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "byte":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "long":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                case "class java.lang.String":
                                    res = ("<" + f.getName() + ">");
                                    writer.write(res);
                                    writer.flush();
                                    writer.write(f.get(object).toString());
                                    writer.flush();
                                    res = ("</" + f.getName() + ">\n");
                                    writer.write(res);
                                    writer.flush();
                                    break;
                                default:
                                    if (f.get(object) != null) {
                                        System.out.println("default" + f.getName());
                                        writer.write("<" + f.getName() + ">\n");
                                        writer.flush();
                                        packObject((Object) f.get(object), file);
                                        writer.write("</" + f.getName() + ">\n");
                                        writer.flush();
                                        break;
                                    }

                            }
                        }
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object loadFromXML(File file) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        Element root = document.getDocumentElement();
        Object object = createObject(root, null);
        return object;
    }

    private static Object createObject(Element root, String clas) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        boolean hasDefConstructor = false;
        NodeList children = root.getChildNodes();
        Class cl = null;
        if (clas == null) {
            try {
                cl = Class.forName(root.getTagName().trim());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                cl = Class.forName(clas);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Constructor[] constructors = cl.getConstructors();
        for(Constructor c : constructors){
            if(c.getParameterTypes().length > 0){
                hasDefConstructor = true;
            }
        }
        Object object = null;
        if(hasDefConstructor) {
            object = cl.newInstance();
            for (int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);
                Field[] fields = object.getClass().getDeclaredFields();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String fieldName = node.getNodeName().trim();
                    for (Field f : fields) {
                        f.setAccessible(true);
                        if (f.getName().equals(fieldName) && !f.toString().contains("static")) {
                            Class fieldType = f.getType();
                            String content = node.getTextContent();
                            switch (fieldType.toString()) {
                                case "int":
                                    f.setInt(object, Integer.parseInt(content));
                                    break;
                                case "float":
                                    f.setFloat(object, Float.parseFloat(content));
                                    break;
                                case "char":
                                    f.setChar(object, content.charAt(0));
                                    break;
                                case "double":
                                    f.setDouble(object, Double.parseDouble(content));
                                    break;
                                case "boolean":
                                    f.setBoolean(object, Boolean.valueOf(content));
                                    break;
                                case "byte":
                                    f.setByte(object, Byte.valueOf(content));
                                    break;
                                case "long":
                                    f.setLong(object, Long.parseLong(content));
                                    break;
                                case "class java.lang.String":
                                    f.set(object, content);
                                    break;
                                case "short":
                                    f.setShort(object, Short.parseShort(content));
                                    break;
                                default:
                                    Element element = (Element) node;
                                    f.set(object, createObject(element, fieldType.toString().substring(6)));
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return object;
    }
}
