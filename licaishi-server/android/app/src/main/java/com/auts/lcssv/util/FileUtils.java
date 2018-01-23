package com.auts.lcssv.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.auts.lcssv.PhApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件工具类
 *
 * @author weiming.zeng
 * @date 2017/4/24
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static void writeToFile(File file, String content, boolean isAppend) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = new FileOutputStream(file, isAppend);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeToFile(File file, byte[] bytes, boolean isAppend) {
        FileOutputStream out = null;
        BufferedOutputStream bos = null;
//        if (file.exists()) {
//            file.delete();
//        }
        try {
            out = new FileOutputStream(file, isAppend);
            bos = new BufferedOutputStream(out);
            bos.write(bytes);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readFileString(File file) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static byte[] readFile(File file) {
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = bufferedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 复制文件目录或单个文件
     *
     * @param srcPath
     * @param destPath
     * @return true表示复制成功，false表示复制失败
     */
    public static boolean copyFile(String srcPath, String destPath) {
        LogUtils.debug(TAG, "copyFile begin");
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (!srcFile.exists()) {
            LogUtils.debug(TAG, "copyFile, source file not exist.");
            return false;
        }
        if (!srcFile.canRead()) {
            LogUtils.debug(TAG, "copyFile, source file can't read.");
            return false;
        }
        if (destFile.exists()) {
            LogUtils.debug(TAG, "copyFile, before copy File, delete first.");
            destFile.delete();
        }
        if (srcFile.isFile()) {
            copyFile(srcFile, destFile);
            return true;
        }
        if (srcFile.isDirectory()) {
            copyDirectiory(srcPath, destPath);
            return true;
        }
        return false;
    }

    /**
     * 复制单个文件
     *
     * @param srcFile
     * @param destFile
     */
    public static void copyFile(File srcFile, File destFile) {
        if (!srcFile.isFile()) {
            LogUtils.error(TAG, "src isn't a file");
            return;
        }
        if (destFile.exists()) {
            return;
        }
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(srcFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(destFile));
            byte[] buff = new byte[2048];
            int len;
            while ((len = inBuff.read(buff)) != -1) {
                outBuff.write(buff, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inBuff != null) {
                try {
                    inBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outBuff != null) {
                try {
                    outBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean copyDirectiory(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (!srcFile.isDirectory()) {
            LogUtils.error(TAG, srcPath + "is a wrong path");
            return false;
        }
        if (!destFile.mkdirs()) {
            return false;
        }
        File[] files = srcFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String targetPath = destPath + File.separator + files[i].getName();
                copyFile(files[i], new File(targetPath));
            }
            if (files[i].isDirectory()) {
                copyDirectiory(srcPath + File.separator + files[i].getName(), destPath + File.separator + files[i].getName());
            }
        }
        return true;
    }

    /**
     * SDCARD是否存在
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard文件路径
     */
    public String sdCardPath() {
        if (isSDCardAvailable()) {    //如果SDCard存在并且可以读写
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return null;
        }
    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return 内部剩余存储空间字节数
     */
    public static long getAvailableInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long availableBytes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBytes = stat.getAvailableBytes();
        } else {
            availableBytes = stat.getBlockSize() * stat.getAvailableBlocks();
        }
        return availableBytes;
    }

    /**
     * 根据文件路径和名称获得文件对象，如果文件不存在，获得空目录
     *
     * @param path
     * @param fileName
     * @return 如果文件不存在，创建文件路径，获得空的file
     */
    public static File getFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (file.exists()) {
            return file;
        }
        File directories = new File(path);
        if (!directories.exists()) {
            directories.mkdirs();
        }
        if (TextUtils.isEmpty(fileName)) {
            return directories;
        }
        file = new File(path, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 删除文件
     *
     * @param file
     * @return true成功，false失败
     */
    public static boolean deleteFile(File file) {
        if (file != null) {
            return file.delete();
        }
        return false;
    }


    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long totleBytes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totleBytes = stat.getTotalBytes();
        } else {
            totleBytes = stat.getBlockSize() * stat.getBlockCount();
        }
        return totleBytes;
    }

    /**
     * 获取外部剩余存储空间
     *
     * @return -1表示外存不可用
     */
    public static long getAvailableExternalMemorySize() {
        if (isSDCardAvailable()) {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long availableBytes;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBytes = stat.getAvailableBytes();
            } else {
                availableBytes = stat.getBlockSize() * stat.getAvailableBlocks();
            }
            return availableBytes;
        } else {
            return -1;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return 返回外部存储总空间，-1表示外存不可用
     */
    public static long getTotalExternalMemorySize() {
        if (isSDCardAvailable()) {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long totleBytes;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totleBytes = stat.getTotalBytes();
            } else {
                totleBytes = stat.getBlockSize() * stat.getBlockCount();
            }
            return totleBytes;
        } else {
            return -1;
        }
    }

//    /**
//     * 将要读取文件头信息的文件的byte数组转换成string类型表示
//     *
//     * @param src 要读取文件头信息的文件的byte数组
//     * @return 文件头信息
//     */
//    private static String bytesToHexString(byte[] src) {
//        StringBuilder builder = hasnew StringBuilder();
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        String hv;
//        for (int i = 0; i < src.length; i++) {
//            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
//            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase(Locale.getDefault());
//            if (hv.length() < 2) {
//                builder.append(0);
//            }
//            builder.append(hv);
//        }
//        return builder.toString();
//    }

    /**
     * 从assets目录下拷贝文件
     *
     * @param assetsFilePath     文件的路径名如：SBClock/0001cuteowl/cuteowl_dot.png
     * @param targetFileFullPath 目标文件路径如：/sdcard/SBClock/0001cuteowl/cuteowl_dot.png
     */
    public static void copyFileFromAssets(String assetsFilePath, String targetFileFullPath) {
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = PhApplication.getAppContext().getAssets().open(assetsFilePath);
            copyFileInputStream(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void copyFileInputStream(InputStream inputStream, String targetPath) {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(inputStream);
            outBuff = new BufferedOutputStream(new FileOutputStream(targetPath));
            byte[] buff = new byte[2048];
            int len;
            while ((len = inBuff.read(buff)) != -1) {
                outBuff.write(buff, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            android.util.Log.d("myso", targetPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inBuff != null) {
                try {
                    inBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outBuff != null) {
                try {
                    outBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void permissionPath(String path) {
        try {
            Runtime runtime = Runtime.getRuntime();
            String command = "chmod -R 777 " + path;
            runtime.exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
