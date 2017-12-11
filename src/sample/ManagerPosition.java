package sample;

public class ManagerPosition implements Position {

    @Override
    public String getPositionId() {
        return "MGR";
    }

    @Override
    public String getPositionName() {
        return "Manager";
    }
}
