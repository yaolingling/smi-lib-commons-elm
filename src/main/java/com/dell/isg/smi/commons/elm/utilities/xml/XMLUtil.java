/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.utilities.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;

public class XMLUtil {

    /**
     * Parses an xml from the filesystem using JAXB
     * 
     * @param xmlPath
     * @param jaxbModelClasses
     * @return
     * @throws JAXBException
     */
    public static Object parseXML(String xmlPath, Class[] jaxbModelClasses) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbModelClasses);
        Unmarshaller u = jc.createUnmarshaller();
        Object xmlElement = null;
        try {
            xmlElement = u.unmarshal(new File(xmlPath));
        } catch (JAXBException e) {
            throw e;
        }
        return xmlElement;
    }


    /**
     * Parses an xml from the filesystem using JAXB and ignores specified namespace. This is requred when we are parsing an xml file which does not specify namespace and the same
     * needs to be unmarshalled
     * 
     * @param xmlPath
     * @param namespace
     * @param jaxbModelClasses
     * @return
     * @throws Exception
     */
    public static Object parseXMLIgnoreNamespace(String xmlPath, String namespace, Class[] jaxbModelClasses) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(jaxbModelClasses);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        // Create the XMLReader
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader reader = factory.newSAXParser().getXMLReader();
        // The filter class to set the correct namespace
        XMLFilterImpl xmlFilter = new XMLNamespaceFilter(reader, namespace);
        reader.setContentHandler(unmarshaller.getUnmarshallerHandler());
        InputStream inStream = new FileInputStream(new File(xmlPath));
        SAXSource source = new SAXSource(xmlFilter, new InputSource(inStream));
        // Get the element
        Object xmlElement = unmarshaller.unmarshal(source);
        return xmlElement;
    }


    /**
     * 
     * @param xmlData
     * @param jaxbModelClasses
     * @return
     * @throws JAXBException
     * @throws ConfigurationNotFoundException
     */
    public static Object parseXMLFromStream(InputStream xmlData, Class[] jaxbModelClasses) throws JAXBException, RuntimeCoreException {
        JAXBContext jc = JAXBContext.newInstance(jaxbModelClasses);
        Unmarshaller u = jc.createUnmarshaller();
        Object xmlElement = null;
        try {
            xmlElement = u.unmarshal(xmlData);
        } catch (JAXBException e) {
            throw new RuntimeCoreException(e);
        }
        return xmlElement;
    }


    /**
     * Saves the xml using jaxb implementation
     * 
     * @param xmlPath
     * @param jaxbModelClasses
     * @param element
     * @throws JAXBException
     */
    public static void saveXML(String xmlPath, Class[] jaxbModelClasses, Object element) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbModelClasses);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(element, new File(xmlPath));
    }


    /**
     * 
     * @param schemaFile
     * @param buf
     */
    public static void validate(InputStream schemaFile, byte[] buf) {
        Source xmlFile = new StreamSource(new ByteArrayInputStream(buf));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {

            Schema schema = schemaFactory.newSchema(new StreamSource(schemaFile));
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
        } catch (Exception e) {
            throw new RuntimeCoreException(e);
        }
    }


    /**
     * 
     * @param object
     * @param jaxbClasses
     * @return
     */
    public static byte[] getXML(Object object, Class[] jaxbClasses) {
        try {
            JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream sbos = new ByteArrayOutputStream();
            m.marshal(object, sbos);
            return sbos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeCoreException("Unable to create XML String");
        }
    }

    /**
     * XMLNamespaceFilter
     */
    public static class XMLNamespaceFilter extends XMLFilterImpl {
        private String namespace;


        public XMLNamespaceFilter(XMLReader arg0, String namespace) {
            super(arg0);
            this.namespace = namespace;
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(this.namespace, localName, qName, attributes);
        }
    }


    public static String convertFileToXMLString(String xmlSource) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(xmlSource));
            String strLine = null;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                stringBuilder.append(strLine);
            }
            return stringBuilder.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
