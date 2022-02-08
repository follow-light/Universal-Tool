package com.chen.demo.graphiccode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author chenzhuang
 * @create_time 2021/12/22 15:45:43
 * @description 生成二维码
 */
public class QRCodeGenerater {
    public static void generateCodeImage(String messageInfo, Integer width, Integer hight) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix encode = writer.encode(messageInfo, BarcodeFormat.QR_CODE, width, hight);
        MatrixToImageWriter.writeToFile(encode, "PNG", new File("C:\\Users\\chenzhuang\\Desktop\\ceshi.png"));
    }

    public static void main(String[] args) throws IOException, WriterException {
        generateCodeImage("zheshiceshi", 1000, 800);
    }
}
