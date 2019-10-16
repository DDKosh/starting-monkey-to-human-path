package RPIS71.Kosheleva.wdad.learn.xml;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlTask {

    //возвращает среднюю заработную плату сотрудников организации
    public int salaryAverage() throws IOException, SAXException, ParserConfigurationException {
        int salaryAvr = 0;
        List<Employee> employeeList = getEmployeeList("src/main/java/RPIS71/Kosheleva/wdad/learn/xml/potato.xml");
        for (int i = 0; i < employeeList.size(); i++) {
            salaryAvr += employeeList.get(i).getSalary().getValue();
        }
        salaryAvr /= employeeList.size();
        return salaryAvr;
    }

    //возвращает среднюю заработную плату сотрудников организации по имени отдела
    public int salaryAverage(String departmentName) throws IOException, SAXException, ParserConfigurationException {
        int salaryAvr = 0;
        int count = 0;
        List<Employee> employeeList = getEmployeeList("src/main/java/RPIS71/Kosheleva/wdad/learn/xml/potato.xml");
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getDepartmentName().equals(departmentName)) {
                salaryAvr += employeeList.get(i).getSalary().getValue();
                count++;
            }
        }
        salaryAvr /= count;
        return salaryAvr;
    }

    //изменяет должность сотрудника
    public void setJobTitle(String firstName, String secondName, String newJobTitle) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        List<Employee> employeeList = getEmployeeList("src/main/java/RPIS71/Kosheleva/wdad/learn/xml/potato.xml");
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getFirstName().equals(firstName) && employeeList.get(i).getSecondName().equals(secondName)) {
                employeeList.get(i).setJobTitle(newJobTitle);
            }
        }
        writeToXml(employeeList);
    }

    //изменяет заработну плату сотруднка
    public void setSalary(String firstName, String secondName, int newSalary) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        List<Employee> employeeList = getEmployeeList("src/main/java/RPIS71/Kosheleva/wdad/learn/xml/potato.xml");
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getFirstName().equals(firstName) && employeeList.get(i).getSecondName().equals(secondName)) {
                employeeList.get(i).getSalary().setValue(newSalary);
            }
        }
        writeToXml(employeeList);
    }

    //удаляет информацию о сотруднике
    public void fireEmployee(String firstName, String secondName) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        List<Employee> employeeList = getEmployeeList("src/main/java/RPIS71/Kosheleva/wdad/learn/xml/potato.xml");
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getFirstName().equals(firstName) && employeeList.get(i).getSecondName().equals(secondName)) {
                employeeList.remove(employeeList.get(i));
            }
        }
        writeToXml(employeeList);
    }

    //получение списка сотрудников
    public List<Employee> getEmployeeList(String path) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);


        Element employersElement = (Element) document.getElementsByTagName("employers").item(0);
        String department = employersElement.getAttribute("department");

        NodeList employeeNodeList = document.getElementsByTagName("employee");
        List<Employee> employeeList = new ArrayList<Employee>();

        for (int i = 0; i < employeeNodeList.getLength(); i++) {
            if (employeeNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element employeeElement = (Element) employeeNodeList.item(i);

                Employee employee = new Employee();
                employee.setDepartmentName(department);
                employee.setNumber(Integer.valueOf(employeeElement.getAttribute("number")));

                NodeList childNodes = employeeElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNodes.item(j);

                        switch (childElement.getNodeName()) {
                            case "firstname": {
                                employee.setFirstName(childElement.getTextContent());
                            }
                            break;
                            case "secondname": {
                                employee.setSecondName(childElement.getTextContent());
                            }
                            break;
                            case "hiredate": {
                                employee.setHireDate(childElement.getTextContent());
                            }
                            break;
                            case "salary": {
                                employee.getSalary().setValue(Integer.valueOf(childElement.getTextContent()));
                                employee.getSalary().setCurrency(childElement.getAttribute("currency"));
                            }
                            break;
                            case "jobtitle": {
                                employee.setJobTitle(childElement.getTextContent());
                            }
                        }
                    }
                }

                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    //запись в xml
    public void writeToXml(List<Employee> employeeList) throws TransformerException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element organization = document.createElement("organization");
        Element employers = document.createElement("employers");
        Element employee;
        Element firstname;
        Element secondname;
        Element hiredate;
        Element salary;
        Element jobtitle;

        document.appendChild(organization);
        organization.appendChild(employers);
        organization.setAttribute("name", "Компания");
        employers.setAttribute("department", "Отдел");

        for (int i = 0; i < employeeList.size(); i++) {

            employee = document.createElement("employee");
            firstname = document.createElement("firstname");
            secondname = document.createElement("secondname");
            hiredate = document.createElement("hiredate");
            salary = document.createElement("salary");
            jobtitle = document.createElement("jobtitle");


            employers.appendChild(employee);
            employee.appendChild(firstname);
            employee.appendChild(secondname);
            employee.appendChild(hiredate);
            employee.appendChild(salary);
            employee.appendChild(jobtitle);

            firstname.appendChild(document.createTextNode(employeeList.get(i).getFirstName()));
            secondname.appendChild(document.createTextNode(employeeList.get(i).getSecondName()));
            hiredate.appendChild(document.createTextNode(employeeList.get(i).getHireDate()));
            salary.appendChild(document.createTextNode(String.valueOf(employeeList.get(i).getSalary().getValue())));
            jobtitle.appendChild(document.createTextNode(employeeList.get(i).getJobTitle()));

            employee.setAttribute("number", String.valueOf(employeeList.get(i).getNumber()));
            salary.setAttribute("currency", employeeList.get(i).getSalary().getCurrency());
        }


        DOMImplementation implementation = document.getImplementation();
        DOMImplementationLS implementationLS = (DOMImplementationLS) implementation.getFeature("LS", "3.0");
        LSSerializer serializer = implementationLS.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", true);
        LSOutput out = implementationLS.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(Files.newOutputStream(Paths.get("src/main/java/RPIS71/Kosheleva/wdad/learn/xml/potato.xml")));
        serializer.write(document, out);
    }
}