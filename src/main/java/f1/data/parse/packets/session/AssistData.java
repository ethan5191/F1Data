package f1.data.parse.packets.session;

import f1.data.utils.BitMaskUtils;

import java.nio.ByteBuffer;

public record AssistData(int steering, int braking, int gearbox, int pit, int pitRelease, int ers, int drs, int dynamicRacingLine,
                         int dynamicRacingLineType) {
    public AssistData(ByteBuffer byteBuffer) {
        this(
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get()),
                BitMaskUtils.bitMask8(byteBuffer.get())
        );
    }
}
