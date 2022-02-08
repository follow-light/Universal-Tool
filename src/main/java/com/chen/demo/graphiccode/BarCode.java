package com.chen.demo.graphiccode;

import com.alibaba.druid.util.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenzhuang
 * @create_time 2022/2/8 9:21:14
 * @description 条形码生成
 */
public class BarCode {
    public static void generateFile(String msg, String path) throws IOException {
        File file = new File(path);
        generate(msg, new FileOutputStream(file));
    }

    public static void generate(String msg, OutputStream ous) throws IOException {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        // 初始化条形码对象
        Code128Bean bean = new Code128Bean();
        // 精细度
        final int dpi = 180;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(4.5f / dpi);
        bean.setBarHeight(10);
        /* 配置对象*/
        bean.setModuleWidth(moduleWidth);
        /*是否设置两侧留白*/
        bean.doQuietZone(false);
        bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
        // 输出格式
        String format = "image/png";

        // 输出到流
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);

        // 生成条形码
        bean.generateBarcode(canvas, msg);

        // 结束绘制
        canvas.finish();

    }

    public static void main(String[] args) throws IOException {
        generateFile("121412", "C:\\Users\\chenzhuang\\Desktop\\ceshi.png");
    }
}
