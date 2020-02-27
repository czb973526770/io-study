package com.czb.iostudy.future;

import com.crazymakercircle.util.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ：chenzb
 * @date ：2020/2/21 23:16
 * @description：
 */
public class JavaFutureTask {
    public static final int SLEEP_GAP = 500;

//    public static void main (String args[]) {
//        HotWarterThread hJob = new HotWarterThread();
//        FutureTask<Boolean> hTask = new FutureTask<>(hJob);
//        Thread hThread = new Thread(hTask, "** 烧水-Thread");
//        Callable<Boolean> wJob = new WashThread();
//        FutureTask<Boolean> wTask = new FutureTask<>(wJob);
//        Thread wThread = new Thread(wTask, "$$ 清洗-Thread");
//        hThread.start();
//        wThread.start();
//        Thread.currentThread().setName("主线程");
//        try {
//            boolean warterOk = hTask.get();
//            boolean cupOk = wTask.get();
//            if (warterOk && cupOk) {
//                Logger.info("泡茶喝");
//            } else {
//                Logger.info("热水或者茶具未清洗完成");
//            }
//        } catch (ExecutionException | InterruptedException e) {
//            Logger.info(getCurThreadName() + "发生异常被中断.");
//            e.printStackTrace();
//
//        }
//        Logger.info(getCurThreadName() + " 运行结束.");
//    }

    public static String getCurThreadName () {
        return Thread.currentThread().getName();
    }

    static class HotWarterThread implements Callable<Boolean> {
        @Override
        public Boolean call () throws Exception {
            Logger.info("烧水中");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            Logger.info(" 烧水完成.");
            Logger.info(getCurThreadName() + " 运行结束.");
            return true;
        }
    }

    static class WashThread implements Callable<Boolean> {
        @Override
        public Boolean call () throws Exception {
            Logger.info("清洗中");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            Logger.info("清洗完成");
            Logger.info(getCurThreadName() + " 运行结束.");
            return true;
        }
    }

}
