package sample;

class DepartmentFactory extends AbstractFactory {
    @Override
    Department getDepartment(String departmentId) {
        if (departmentId.equalsIgnoreCase("IT")) {
            return new ITDepartment();
        } else if (departmentId.equalsIgnoreCase("HR")) {
            return new HRDepartment();
        } else if (departmentId.equalsIgnoreCase("MK")) {
            return new MKDepartment();
        }

        return null;
    }

    @Override
    Position getPosition(String positionId) {
        return null;
    }
}
