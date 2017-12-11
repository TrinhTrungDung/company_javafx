package sample;

public class StaffPosition implements Position {

    @Override
    public String getPositionId() {
        return "EMP";
    }

    @Override
    public String getPositionName() {
        return "Employee";
    }
}
