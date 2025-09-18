package f1.data.parse.packets.session;

import java.util.Arrays;

public class SessionTimeBuffer {

    private final int[] buffer;

    private int index = 0;

    //Default value is 90 minutes to account for F1 2019 and 2020 practice lengths.
    private final int initialValue = 5400;
    private final int capacity = 30;

    public SessionTimeBuffer() {
        this.buffer = new int[capacity];
        Arrays.fill(this.buffer, initialValue);
    }

    public void add(int sessionTimeLeft) {
        this.buffer[this.index] = sessionTimeLeft;
        this.index++;
        if (this.index == this.capacity) this.index = 0;
    }

    public boolean contains(int sessionTimeLeft) {
        for (int j : this.buffer) {
            if (j == sessionTimeLeft) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        Arrays.fill(this.buffer, this.initialValue);
    }
}
