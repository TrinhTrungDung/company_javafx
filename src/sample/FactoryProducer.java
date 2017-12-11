package sample;

public class FactoryProducer {
    public static AbstractFactory getFactory(String choice){

        if(choice.equalsIgnoreCase("department")){
            return new DepartmentFactory();

        }else if(choice.equalsIgnoreCase("position")){
            return new PositionFactory();
        }

        return null;
    }
}