package com.czb.iostudy.io.file;

import com.crazymakercircle.util.IOUtil;
import com.crazymakercircle.util.Logger;
import com.czb.iostudy.NioDemoConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：chenzb
 * @date ：2020/2/20 22:34
 * @description：
 */
public class NIOFileCopy {
    public static void copyResouceFile() {
        String sourcePath = NioDemoConfig.FILE_RESOURCE_SRC_PATH;
        String srcDecodePath = IOUtil.getResourcePath(sourcePath);
        Logger.debug("srcDecodePath=" + srcDecodePath);

        String destPath = NioDemoConfig.FILE_RESOURCE_DEST_PATH;
        String destDecodePath = IOUtil.builderResourcePath(destPath);
        Logger.debug("destDecodePath=" + destDecodePath);
        nioCopyFile(srcDecodePath, destDecodePath);

    }

    private static void nioCopyFile (String srcDecodePath, String destDecodePath) {
        File srcFile = new File(srcDecodePath);
        File destFile = new File(destDecodePath);
        FileInputStream srcFileInputStream = null;
        FileOutputStream destFileInputStream = null;
        FileChannel srcChannel = null;
        FileChannel destChannel = null;
        try{
            if (!destFile.exists()) {
                boolean newFile = destFile.createNewFile();
            }
            srcFileInputStream = new FileInputStream(srcFile);
            destFileInputStream = new FileOutputStream(destFile);
            //从流获取channel
            srcChannel = srcFileInputStream.getChannel();
            destChannel = destFileInputStream.getChannel();

            int length = -1;
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while ((length = srcChannel.read(buf)) != -1) {

                //翻转buf,变成成读模式
                buf.flip();

                int outlength = 0;
                //将buf写入到输出的通道
                while ((outlength = destChannel.write(buf)) != 0) {
                    System.out.println("写入字节数：" + outlength);
                }
                //清除buf,变成写入模式
                buf.clear();
            }


            //强制刷新磁盘
            destChannel.force(true);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtil.closeQuietly(destChannel);
            IOUtil.closeQuietly(srcChannel);
            IOUtil.closeQuietly(destFileInputStream);
            IOUtil.closeQuietly(srcFileInputStream);
        }
    }
}
