package sample;

public class HRDepartment implements Department {

    @Override
    public String getDepartmentId() {
        return "HR";
    }

    @Override
    public String getDepartmentName() {
        return "Human Resources";
    }

    @Override
    public String getDescription() {
        return "This department is about hiring people.";
    }
}
