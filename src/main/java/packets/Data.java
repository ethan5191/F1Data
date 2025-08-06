package packets;

public abstract class Data {

    protected static final int BIT_MASK_8 = 0xFF;
    protected static final int BIT_MASK_16 = 0xFFFF;
    protected static final long BIT_MASK_32 = 0xFFFFFFFFL;

    //used for debugging to verify the object and size of the array.
//    protected void printMessage(String name, int size) {
//        System.out.println(name + size);
//    }
}
