package sample;

public class ChiefPosition implements Position {

    @Override
    public String getPositionId() {
        return "CO";
    }

    @Override
    public String getPositionName() {
        return "Chief Officer";
    }
}
