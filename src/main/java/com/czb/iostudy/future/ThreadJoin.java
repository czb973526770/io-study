package com.czb.iostudy.future;

import com.crazymakercircle.util.Logger;

/**
 * @author ：chenzb
 * @date ：2020/2/21 23:16
 * @description：
 */
public class ThreadJoin {
    public static final int SLEEP_GAP = 500;

    public static void main (String args[]) {

        Thread hThread = new HotWarterThread();
        Thread wThread = new WashThread();

        hThread.start();
        wThread.start();
        try {
            // 合并烧水-线程
            hThread.join();
            // 合并清洗-线程
            wThread.join();

            Thread.currentThread().setName("主线程");
            Logger.info("泡茶喝");
        } catch (InterruptedException e) {
            Logger.info(getCurThreadName() + "发生异常被中断.");
        }
        Logger.info(getCurThreadName() + " 运行结束.");
    }

    public static String getCurThreadName () {
        return Thread.currentThread().getName();
    }

    static class HotWarterThread extends Thread {

        public HotWarterThread () {
            super("** 烧水-Thread");
        }

        public void run () {
            Logger.info("烧水中");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.info(" 烧水完成.");
            Logger.info(getCurThreadName() + " 运行结束.");

        }

    }

    static class WashThread extends Thread {


        public WashThread () {
            super("$$ 清洗-Thread");
        }

        public void run () {
            Logger.info("清洗中");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.info("清洗完成");
            Logger.info(getCurThreadName() + " 运行结束.");

        }

    }

}
