package sample;

class PositionFactory extends AbstractFactory {

    @Override
    Position getPosition(String positionId) {
        if (positionId.equalsIgnoreCase("CO")) {
            return new ChiefPosition();
        } else if (positionId.equalsIgnoreCase("MGR")) {
            return new ManagerPosition();
        } else if (positionId.equalsIgnoreCase("EMP")) {
            return new StaffPosition();
        }

        return null;
    }

    @Override
    Department getDepartment(String departmentId) {
        return null;
    }
}
