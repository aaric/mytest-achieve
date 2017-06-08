package com.incarcloud.rooster.landu;

import com.incarcloud.rooster.datapack.util.DataPackUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 3.2.2 取得车辆当前检测数据(0x1621)<br>
 *     【命令字】+【远程诊断仪串号（设备号）】+【TripID】+【VID】+【VIN 码】+【故障分级内容】<br>
 *     【故障分级内容】::= 【严重等级】+【个数】+【故障码】
 *
 * @author Aaric, created on 2017-06-08T16:12.
 * @since 1.0-SNAPSHOT
 */
public class P0x1621GetVehicleCheckDataTest {

    private ByteBuf buffer;

    @Before
    public void begin() throws IOException {
        // 准备数据
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        FileInputStream fio = new FileInputStream(new File(desktopPath, "vehicle.dat"));
        byte[] array = new byte[fio.available()];
        fio.read(array, 0, fio.available());
        fio.close();
        // 测试数据
        buffer = Unpooled.wrappedBuffer(array);
    }

    @After
    public void end() {
        ReferenceCountUtil.release(buffer);
    }

    @Test
    public void testParse() throws Exception {
        /* 假定无格式问题数据，开始解析数据内容 */
        System.out.println("-------------------------begin");
        int offset;
        // 1.丢弃协议头
        buffer.skipBytes(8);
        // 2.判断命令字-0x1621
        offset = buffer.readerIndex();
        if(0x16 == buffer.getByte(offset) && 0x21 == buffer.getByte(offset+1)) {
            // 丢弃命令字
            buffer.skipBytes(2);

            // 3.OBD 串号（设备号）
            String obdCode = DataPackUtil.readString(buffer);
            System.out.printf("obdCode: %s\n", obdCode);

            // 4.TripID
            int tripId = DataPackUtil.readDWord(buffer);
            System.out.printf("tripId: %d\n", tripId);

            // 5.VID
            String vid = DataPackUtil.readString(buffer);
            System.out.printf("vid: %s\n", vid);

            // 6.VIN码
            String vin = DataPackUtil.readString(buffer);
            System.out.printf("vin: %s\n", vin);

            // 7.故障分级内容
        }
        System.out.println("-------------------------end");
        System.out.printf("readerIndex: %d, writerIndex: %d\n", buffer.readerIndex(), buffer.writerIndex());
    }
}
