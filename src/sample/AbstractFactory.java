package sample;

abstract class AbstractFactory {
    abstract Department getDepartment(String departmentId);
    abstract Position getPosition(String positionId);
}
