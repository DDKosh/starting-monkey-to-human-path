package RPIS71.Kosheleva.wdad.learn.xml;

import java.util.ArrayList;

public class Organization {
    private String name;
    private ArrayList<Department> departments;

    public Organization(){
        this.departments = new ArrayList<>();
    }

    public Organization(String name){
        this(name, new ArrayList<>());
    }

    public Organization(String name, ArrayList<Department> departments){
        this.name = name;
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
