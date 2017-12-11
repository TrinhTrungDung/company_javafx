package sample;

public class ITDepartment implements Department {

    ITDepartment() {

    }

    @Override
    public String getDepartmentId() {
        return "IT";
    }

    @Override
    public String getDepartmentName() {
        return "Information Technology";
    }

    @Override
    public String getDescription() {
        return "This department has software developers, programmers, etc.";
    }
}
