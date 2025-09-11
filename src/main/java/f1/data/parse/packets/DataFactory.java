package f1.data.parse.packets;

import java.nio.ByteBuffer;

public interface DataFactory<T> {

    T build(ByteBuffer byteBuffer);
}
