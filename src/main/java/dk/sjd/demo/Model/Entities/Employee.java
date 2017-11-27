package dk.sjd.demo.Model.Entities;

public class Employee {

    private int id;
    private String name;
    private int totalHours;

    public Employee() {
    }

    public Employee(int id, String name, int totalHours) {
        this.id = id;
        this.name = name;
        this.totalHours = totalHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }
}