package RPIS71.Kosheleva.wdad.learn.xml;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestXmlTask {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        XmlTask xmlTask = new XmlTask();
        int sal =  xmlTask.salaryAverage("IT");
        System.out.println(sal);

        xmlTask.setJobTitle("Иван", "Иванов", "assistant");
        xmlTask.setSalary("Иван", "Иванов", 12432);
        xmlTask.fireEmployee("Иван", "Иванов");


    }

}
