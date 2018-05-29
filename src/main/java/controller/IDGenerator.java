package controller;

public class IDGenerator {
    private static long currentID = 1;
    public static long getNextID() throws IDOverFlowException {
        if (currentID > 9999){
            throw new IDOverFlowException("ID über 9999");
        }
        return currentID++;
    }
}
