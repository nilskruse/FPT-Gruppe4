package controller;

public class IDGenerator {
    private static long currentID = 1;
    private static long maxID = 9999;

    public static long getNextID() throws IDOverFlowException {
        if (currentID > maxID){
            throw new IDOverFlowException("ID über 9999");
        }
        return currentID++;
    }
    public static void reset(){
        currentID = 1;
    }
}
