package sample;

import java.util.concurrent.atomic.AtomicInteger;

public class Employee extends Person {
    private static final AtomicInteger id = new AtomicInteger(1);
    private String email;
    private String departmentId;
    private String positionId;

    Employee() {

    }

    Employee(String firstName, String lastName, String gender, String email, String departmentId, String positionId,
             Address address) {
        super(firstName, lastName, gender, address);
        setEmail(email);
        setDepartmentId(departmentId);
        setPositionId(positionId);
    }

    public static AtomicInteger getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    private void setDepartmentId(String departmentId) {
        AbstractFactory departmentFactory = FactoryProducer.getFactory("department");
        Department department = departmentFactory.getDepartment(departmentId);
        this.departmentId = department.getDepartmentId();
    }

    public String getPositionId() {
        return positionId;
    }

    private void setPositionId(String positionId) {
        AbstractFactory positionFactory = FactoryProducer.getFactory("position");
        Position position = positionFactory.getPosition(positionId);
        this.positionId = position.getPositionId();
    }

    @Override
    public String toString() {
        return super.getFirstName() + ", " + super.getLastName() + ", " + super.getGender() + ", " + email + ", " +
                departmentId + ", " + positionId + ", " + super.getAddress();
    }
}
