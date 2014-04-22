/**
 * This class serves to out data in different ways
 */
public class Output {
    private Output(){ throw new AssertionError("Shouldn't be instantiated"); }

    /**
     * Output object in console.
     * @param object object that should be output.
     */
    public static void println(Object object){
        System.out.println(object);
    }
}
