package com.chen.demo.picture;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * @author chenzhuang
 * @create_time 2022/3/15 8:43:41
 * @description 图片压缩
 * 其它压缩资源库：1）ImageJ，用 Java 编写的，可以编辑、分析、处理、保存和打印图像。
 *              2）Apache Commons Imaging，一个读取和写入各种图像格式的库，包括快速解析图像信息（如大小，颜色，空间，ICC配置文件等）和元数据。
 *              3）ImageMagick，可以读取和写入超过100种格式的图像，包括DPX、EXR、GIF、JPEG、JPEG-2000、PDF、PNG、Postscript、SVG和TIFF。还可以调整大小、翻转、镜像、旋转、扭曲、剪切和变换图像，调整图像颜色，应用各种特殊效果，包括绘制文本、线条、多边形、椭圆和贝塞尔曲线。
 *              4）OpenCV，由BSD许可证发布，可以免费学习和商业使用，提供了包括 C/C++、Python 和 Java 等主流编程语言在内的接口。OpenCV 专为计算效率而设计，强调实时应用，可以充分发挥多核处理器的优势
 *
 */
public class PictureCompression {

    public static void compression(String inputPath, String outPutPath) throws IOException {
        File input = new File(inputPath);
        BufferedImage image = ImageIO.read(input);

        // 返回一个Iterator，其中包含了通过命名格式对图像进行编码的 ImageWriter
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        File compressedImageFile = new File(outPutPath);
        OutputStream os = new FileOutputStream(compressedImageFile);
        // 创建一个图像的输出流对象
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        // 设置为输出流
        writer.setOutput(ios);


        ImageWriteParam param = writer.getDefaultWriteParam();

        // 压缩模式一共有四种，MODE_EXPLICIT 是其中一种，表示 ImageWriter 可以根据后续的 set 的附加信息进行平铺和压缩
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 参数是一个 0-1 之间的数，0.0 表示尽最大程度压缩，1.0 表示保证图像质量
        param.setCompressionQuality(0.01f);

        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }

    /**
     * openCV依赖压缩
     * 步骤：1）引入依赖 <dependency><groupId>org.openpnp</groupId><artifactId>opencv</artifactId><version>4.5.1-2</version></dependency>
     *      2）先初始化，使用 OpenCV 读取图片
     *      3）使用 OpenCV 压缩图片
     */
    public static void openCVCompression(String imagePath, String outputPath) {
        // 初始化
        OpenCV.loadShared();
        // 使用 OpenCV 读取图片
        Mat src = Imgcodecs.imread(imagePath);
        // 使用 OpenCV 压缩图片
        MatOfInt dstImage = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 1);
        Imgcodecs.imwrite("resized_image.jpg", src, dstImage);
    }
}
