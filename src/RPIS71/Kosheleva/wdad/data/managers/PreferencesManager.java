package RPIS71.Kosheleva.wdad.data.managers;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class PreferencesManager {

    private static PreferencesManager preferencesManager;

    private static String createregistry;
    private static String registryaddress;
    private static String registryport;
    private static String policypath;
    private static String usecodebaseonly;
    private static String classprovider;

    public static synchronized PreferencesManager getPreferencesManager() throws ParserConfigurationException, IOException, SAXException {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager();
            getFromDocument();
        }
        return preferencesManager;
    }

    private PreferencesManager() {
    }

    public static void setPreferencesManager(PreferencesManager preferencesManager) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.preferencesManager = preferencesManager;
        writeToDoc();
    }

    public static String getCreateregistry() throws ParserConfigurationException, SAXException, IOException {
        if(Objects.isNull(createregistry)){
            getFromDocument();
        }
        return createregistry;
    }

    public static void setCreateregistry(String createregistry) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.createregistry = createregistry;
        writeToDoc();
    }

    public static String getRegistryaddress() throws ParserConfigurationException, SAXException, IOException {
        if(Objects.isNull(registryaddress)){
            getFromDocument();
        }
        return registryaddress;
    }

    public static void setRegistryaddress(String registryaddress) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.registryaddress = registryaddress;
        writeToDoc();
    }

    public static String getRegistryport() throws ParserConfigurationException, SAXException, IOException {
        if(Objects.isNull(registryport)){
            getFromDocument();
        }
        return registryport;
    }

    public static void setRegistryport(String registryport) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.registryport = registryport;
        writeToDoc();
    }

    public static String getPolicypath() throws ParserConfigurationException, SAXException, IOException {
        if(Objects.isNull(policypath)){
            getFromDocument();
        }
        return policypath;
    }

    public static void setPolicypath(String policypath) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.policypath = policypath;
        writeToDoc();
    }

    public static String getUsecodebaseonly() throws ParserConfigurationException, SAXException, IOException {
        if(Objects.isNull(usecodebaseonly)){
            getFromDocument();
        }
        return usecodebaseonly;
    }

    public static void setUsecodebaseonly(String usecodebaseonly) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.usecodebaseonly = usecodebaseonly;
        writeToDoc();
    }

    public static String getClassprovider() throws IOException, SAXException, ParserConfigurationException {
        if(classprovider == null){
            getFromDocument();
        }
        return classprovider;
    }

    public static void setClassprovider(String classprovider) throws ParserConfigurationException, SAXException, IOException {
        PreferencesManager.classprovider = classprovider;
        writeToDoc();
    }

    public static Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("src/main/java/PO72/Koloyartsev/wdad/resources/configuration/appconfig.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        return document;
    }

    public static void getFromDocument() throws IOException, SAXException, ParserConfigurationException {

        Document document = getDocument();

        Element createregistryElement = (Element) document.getElementsByTagName("createregistry").item(0);
        Element registryaddressElement = (Element) document.getElementsByTagName("registryaddress").item(0);
        Element registryportElement = (Element) document.getElementsByTagName("registryport").item(0);
        Element policypathElement = (Element) document.getElementsByTagName("policypath").item(0);
        Element usecodebaseonlyElement = (Element) document.getElementsByTagName("usecodebaseonly").item(0);
        Element classproviderElement = (Element) document.getElementsByTagName("classprovider").item(0);

        createregistry = createregistryElement.getTextContent();
        registryaddress = registryaddressElement.getTextContent();
        registryport = registryportElement.getTextContent();
        policypath = policypathElement.getTextContent();
        usecodebaseonly = usecodebaseonlyElement.getTextContent();
        classprovider = classproviderElement.getTextContent();
    }

    private static void writeToDoc() throws IOException, SAXException, ParserConfigurationException {
        Document document = getDocument();

        Element appconfigElement = document.createElement("appconfig");
        Element rmiElement = document.createElement("rmi");
        Element serverElement = document.createElement("server");
        Element registryElement = document.createElement("registry");
        Element clientElement = document.createElement("client");

        Element createregistryElement = document.createElement("createregistry");
        Element registryaddressElement = document.createElement("registryaddress");
        Element registryportElement = document.createElement("registryport");
        Element policypathElement = document.createElement("policypath");
        Element usecodebaseonlyElement = document.createElement("usecodebaseonly");
        Element classproviderElement = document.createElement("classprovider");

        document.appendChild(appconfigElement);
        appconfigElement.appendChild(rmiElement);

        rmiElement.appendChild(serverElement);
        rmiElement.appendChild(clientElement);
        rmiElement.appendChild(classproviderElement);

        serverElement.appendChild(registryElement);
        registryElement.appendChild(createregistryElement);
        registryElement.appendChild(registryaddressElement);
        registryElement.appendChild(registryportElement);

        clientElement.appendChild(policypathElement);
        clientElement.appendChild(usecodebaseonlyElement);

        createregistryElement.appendChild(document.createTextNode(createregistry));
        registryaddressElement.appendChild(document.createTextNode(registryaddress));
        registryportElement.appendChild(document.createTextNode(registryport));
        policypathElement.appendChild(document.createTextNode(policypath));
        usecodebaseonlyElement.appendChild(document.createTextNode(usecodebaseonly));
        classproviderElement.appendChild(document.createTextNode(classprovider));

        DOMImplementation implementation = document.getImplementation();
        DOMImplementationLS implementationLS = (DOMImplementationLS) implementation.getFeature("LS", "3.0");
        LSSerializer serializer = implementationLS.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", true);
        LSOutput out = implementationLS.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(Files.newOutputStream(Paths.get("src/main/java/PO72/Koloyartsev/wdad/resources/configuration/appconfig.xml")));
        serializer.write(document, out);
    }

}
