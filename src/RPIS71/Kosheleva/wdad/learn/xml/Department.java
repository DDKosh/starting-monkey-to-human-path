package RPIS71.Kosheleva.wdad.learn.xml;

import java.util.ArrayList;

public class Department {
    private String name;
    private ArrayList<Employee> employees;

    public Department(){
        this.employees = new ArrayList<>();
    }

    public Department(String name){
        this(name, new ArrayList<>());
    }

    public Department(String name, ArrayList<Employee> employees){
        this.name = name;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
