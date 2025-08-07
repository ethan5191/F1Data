public class Main {

    public static void main(String[] args) {
        try {
            new F1DataUI().run(args);
        } catch (Exception e) {
            System.out.println("Caught Exception " + e);
        }
    }
}
