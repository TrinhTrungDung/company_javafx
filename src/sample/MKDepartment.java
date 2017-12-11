package sample;

public class MKDepartment implements Department {

    @Override
    public String getDepartmentId() {
        return "MK";
    }

    @Override
    public String getDepartmentName() {
        return "Marketing";
    }

    @Override
    public String getDescription() {
        return "This department has marketers who analysing the market place.";
    }
}
