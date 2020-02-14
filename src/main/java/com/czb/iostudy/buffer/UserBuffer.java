package com.czb.iostudy.buffer;

import com.crazymakercircle.util.Logger;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @author ：chenzb
 * @date ：2020/2/15 6:47
 * @description：
 */
public class UserBuffer {
    static IntBuffer intBuffer = null;

    public static void allocatTest () {
        intBuffer = IntBuffer.allocate(20);
        Logger.debug("------------after allocate------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void putTest () {
        intBuffer.put(new int[]{1,2,3,4,5});
        Logger.debug("------------after put------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void flipTest () {
        intBuffer.flip();
        Logger.debug("------------after flip------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }
    public static void getTest () {
        for (int i = 0; i < 2; i++) {
            int j = intBuffer.get();
            Logger.debug("j = " + j);
        }
        Logger.debug("------------after flip2------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
        for (int i = 0; i < 3; i++) {
            int j = intBuffer.get();
            Logger.debug("j = " + j);
        }
        Logger.debug("------------after flip3------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void rewindTest () {
        intBuffer.rewind();
        Logger.debug("------------after rewind------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }
    private static void reGetTest () {
        for (int i = 0; i < 5; i++) {
            if(i==1){
                intBuffer.mark();
            }
            int j = intBuffer.get();
            Logger.debug("j = " + j);
        }
        Logger.debug("------------after reGet------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void resetTest () {
        intBuffer.reset();
        Logger.debug("------------after reset------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
        for (int i = 1; i <= 3; i++) {
            int j = intBuffer.get();
            Logger.debug("j = " + j);
        }
        Logger.debug("------------after get------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void clearTest() {
        intBuffer.clear();
        Logger.debug("------------after clear------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }
    public static void main (String[] args) {
        Logger.debug("分配内存");

        allocatTest();

        Logger.debug("写入");
        putTest();

        Logger.debug("翻转");
        flipTest();

        Logger.debug("获取");
        getTest();

        Logger.debug("倒带");
        rewindTest();

        Logger.debug("重新读");
        reGetTest();

        Logger.debug("mark&reset");
        resetTest();

        Logger.debug("清除");
        clearTest();
    }
}
