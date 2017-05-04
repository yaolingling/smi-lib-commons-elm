/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 * 
 */
package com.dell.isg.smi.commons.elm.utilities.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper {

    private static XmlHelper _instance;

    static {
        try {
            _instance = new XmlHelper();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }


    /**
     * @return XmlHelper
     */
    public static XmlHelper GetInstance() {
        return _instance;
    }


    /**
     * @param target
     * @return String that represent the object
     */
    public String GetXmlFromObject(Object target) {
        String result = null;

        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(target.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(target, writer);
            result = writer.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


    /*
     * public static Object xmlToObject(File file, Class jaxbModelClasses) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException { JAXBContext jc
     * = JAXBContext.newInstance(jaxbModelClasses); Unmarshaller u = jc.createUnmarshaller(); Object xmlElement = null; try { SAXSource saxSource =
     * XmlHelper.getSAXSourceFromFile(file); xmlElement = u.unmarshal(saxSource); } catch (JAXBException e) { throw e; } return xmlElement; }
     */

    public static Object xmlToObject(File file, Class jaxbModelClasses) throws JAXBException, XMLStreamException {
        return null;
        /*
         * // return object Object xmlElement = null;
         * 
         * // create an input factory with settings to prevent Xml External Entity Injection (XEE) XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
         * xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false); xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
         * 
         * // create an unmarshaller JAXBContext jaxbContext = JAXBContext.newInstance(jaxbModelClasses); Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         * 
         * // read the file contents into a streamReader using the xmlInputFactory, then unmarshall it. XMLStreamReader xmlStreamReader = null; try{ xmlStreamReader =
         * xmlInputFactory.createXMLStreamReader(new StreamSource(file)); xmlElement = unmarshaller.unmarshal(xmlStreamReader); } catch (JAXBException e){ throw e; } finally{ //
         * close the stream if it is open if(null != xmlStreamReader){ try { xmlStreamReader.close(); } catch (XMLStreamException e){} } }
         * 
         * return xmlElement;
         */
    }


    /*
     * public static SAXSource getSAXSourceFromXMLString(String inputString) throws ParserConfigurationException, SAXException{ InputSource inputSource = new InputSource(new
     * StringReader(inputString)); return getSAXSourceFromInputSource(inputSource); }
     * 
     * public static SAXSource getSAXSourceFromFile(File file) throws FileNotFoundException, ParserConfigurationException, SAXException{ FileInputStream fileInputStream = null;
     * try{ fileInputStream = new FileInputStream(file); InputSource inputSource = new InputSource(); return getSAXSourceFromInputSource(inputSource); } finally{
     * Utilities.closeStreamQuietly(fileInputStream); } }
     * 
     * private static SAXSource getSAXSourceFromInputSource(InputSource inputSource) throws ParserConfigurationException, SAXException{ SAXParserFactory spf =
     * SAXParserFactory.newInstance(); spf.setNamespaceAware(true); //spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true); SAXParser sp = spf.newSAXParser(); XMLReader
     * xmlReader = sp.getXMLReader(); xmlReader.setEntityResolver(new NullXmlEntityResolver()); SAXSource saxSource = new SAXSource(xmlReader,inputSource); return saxSource; }
     */

    public static Object xmlToObject(String xmlString, boolean validate, Class cls) {
        try {
            JAXBContext jc = JAXBContext.newInstance(cls);

            Unmarshaller u = jc.createUnmarshaller();

            Object genericXmlObject = u.unmarshal(new StreamSource(new StringReader(xmlString)));

            return genericXmlObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static Object xmlToObject(Node node, Class cls) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement root = unmarshaller.unmarshal(node, cls);

        return root.getValue();

    }


    public static String objectToComplexXmlTypeString(Object obj, String rootName) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();

        StringWriter sw = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName("", rootName), obj.getClass(), obj), sw);

        return sw.toString();

    }


    public static Object complexTypeXMLStrToObject(Class cls, String xml) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        JAXBElement biosAttrDetails = unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), cls);

        return biosAttrDetails.getValue();
    }


    /**
     * @param target
     * @param nameSpaceMap
     * @return String that represent the object
     */
    public String GetXmlFromObject(Object target, HashMap<String, String> nameSpaceMap) {
        String result = null;

        try {
            StringWriter writer = new StringWriter();
            XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            Iterator<Entry<String, String>> it = nameSpaceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                xmlStreamWriter.setPrefix(entry.getKey().toString(), entry.getValue().toString());
            }

            JAXBContext context = JAXBContext.newInstance(target.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(target, xmlStreamWriter);
            result = FixNameSpace(nameSpaceMap, writer.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


    /**
     * @param nameSpaceMap
     * @param target
     * @return String that represent the object
     */
    private String FixNameSpace(HashMap<String, String> nameSpaceMap, String target) {

        StringBuilder nameSpaceContents = new StringBuilder();
        Iterator<Entry<String, String>> it = nameSpaceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            nameSpaceContents.append("xmlns:" + entry.getKey() + "=\"" + entry.getValue() + "\" ");
        }

        return target.replaceAll("xmlns=\"\"", nameSpaceContents.toString());
    }


    /**
     * @param target
     * @param xml
     * @return Object
     */
    public Object GetObjectFromXml(Object target, String xml) {

        Object xmlObject = null;

        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
            JAXBContext context = JAXBContext.newInstance(target.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(null);
            xmlObject = target.getClass().cast(unmarshaller.unmarshal(stream));
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return xmlObject;
    }


    // comment out this one for now since it gives compile warning.
    // @SuppressWarnings("deprecation")
    // public boolean IsXMLValid(String xmlFileName, String xmlSchemaFileName)
    // throws ParserConfigurationException
    // {
    // boolean isValid = true;
    // try
    // {
    // DocumentBuilder parser = DocumentBuilderFactory.newInstance()
    // .newDocumentBuilder();
    // org.w3c.dom.Document document = parser.parse(new File(xmlFileName));
    // SchemaFactory factory = SchemaFactory
    // .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    // Source schemaFile = new StreamSource(new File(xmlSchemaFileName));
    // javax.xml.validation.Schema schema = factory.newSchema(schemaFile);
    // javax.xml.validation.Validator validator = schema.newValidator();
    //
    // try
    // {
    // validator.validate(new DOMSource(document));
    // }
    // catch (SAXException e)
    // {
    // isValid = false;
    // }
    // }
    // catch (SAXException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // catch (IOException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // return isValid;
    // }

    public static Document convertStringToXMLDocument(String xmlSource) throws ParserConfigurationException, SAXException, IOException {
        return convertStringToXMLDocument(xmlSource, false);
    }


    public static Document convertStringToXMLDocument(String xmlSource, boolean namespaceAware) throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new ByteArrayInputStream(xmlSource.getBytes());
        return convertInputStreamToXmlDocument(inputStream, namespaceAware);
    }


    public static Document convertBytesToXmlDocument(byte[] inputBytes) throws ParserConfigurationException, SAXException, IOException {
        return convertBytesToXmlDocument(inputBytes, false);
    }


    public static Document convertBytesToXmlDocument(byte[] inputBytes, boolean namespaeAware) throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        return convertInputStreamToXmlDocument(inputStream, namespaeAware);
    }


    public static Document convertInputStreamToXmlDocument(InputStream inputStream, boolean namespaceAware) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(namespaceAware);
        factory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder builder = null;
        Document document;
        builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new NullXmlEntityResolver());
        document = builder.parse(inputStream);
        return document;
    }


    public static Document convertFileToXMLDocument(String xmlSource) throws IOException, ParserConfigurationException, SAXException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(xmlSource));
            String strLine = null;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                stringBuilder.append(strLine);
            }
            return convertStringToXMLDocument(stringBuilder.toString());

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }


    public static Object findObjectInDocument(Document doc, String xPathLocation, QName qname) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    public static Object findObjectInDocument(Document doc, String nodeName) throws XPathExpressionException {
        Object result = "";
        Element element = doc.getDocumentElement();
        if (element != null) {
            NodeList nodeList = element.getElementsByTagName(nodeName);
            if (nodeList != null && nodeList.getLength() > 0) {
                if (nodeList.item(0).getNodeValue() == null) {
                    Node node = nodeList.item(0).getFirstChild();
                    if (node != null) {
                        result = node.getNodeValue();
                    }
                } else {
                    result = nodeList.item(0).getNodeValue();
                }
                System.out.println();
            }

        }
        return result;
    }


    /*
     * utility function to convert XML doc into Java String
     */

    public static String convertDocumenttoString(Document document) throws ParserConfigurationException, TransformerException, SAXException, IOException {

        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = null;
        transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }
}