package com.chen.demo.filetype;

import java.io.*;

/**
 * @author chenzhuang
 * @create_time 2022/2/23 16:09:28
 * @description
 */
public class FileTypeJudge {
    /**
     * Constructor
     */
    private FileTypeJudge() {}

    /**
     * 将文件头转换成16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     */
    private static String getFileContent(InputStream inputStream) throws IOException {

        byte[] b = new byte[28];

        try {
            inputStream.read(b, 0, 28);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     */
    public static FileType getType(InputStream inputStream) throws IOException {

        String fileHead = getFileContent(inputStream);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        for (FileType type : FileType.values()) {
            if (fileHead.startsWith(type.code)) {
                return type;
            }
        }

        return null;
    }

    /**
     * InputStream转ByteArrayOutputStream，用于流的复制
     */
    public static ByteArrayOutputStream cloneInputStream(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        return baos;
    }

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("C:\\Users\\chenzhuang\\Desktop\\sys_attachment\\sys_attachment.dosys_id=f9e2cf281b87b010997ffc49cd4bcb0b"));
        FileType type = getType(is);
        System.out.println(type);
    }
}
