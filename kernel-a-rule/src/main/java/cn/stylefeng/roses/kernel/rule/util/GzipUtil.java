package cn.stylefeng.roses.kernel.rule.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 一般用于http请求和响应压缩
 *
 * @author fengshuonan
 * @since 2026/1/27 16:36
 */
public class GzipUtil {

    /**
     * Gzip压缩字节数组
     *
     * @param data 原始字节数组
     * @return 压缩后的字节数组
     * @throws IOException 压缩异常
     * @author fengshuonan
     * @since 2026/1/27 16:24
     */
    public static byte[] gzipCompress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return data;
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data);
            gzip.finish();
            return bos.toByteArray();
        }
    }

    /**
     * Gzip解压缩字节数组
     *
     * @param compressedData 压缩后的字节数组
     * @return 解压后的字符串（UTF-8编码）
     * @author fengshuonan
     * @since 2026/1/27 16:24
     */
    public static String gzipDecompress(byte[] compressedData) throws IOException {
        if (compressedData == null || compressedData.length == 0) {
            return "";
        }
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressedData));
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toString(StandardCharsets.UTF_8);
        }
    }

    /**
     * 解压Gzip压缩的请求体
     *
     * @author fengshuonan
     * @since 2026/1/27 16:43
     */
    public static String decompressGzipRequest(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toString(StandardCharsets.UTF_8);
        }
    }

    /**
     * 读取普通（非压缩）的请求体
     *
     * @author fengshuonan
     * @since 2026/1/27 16:43
     */
    public static String readPlainRequest(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toString(StandardCharsets.UTF_8);
        }
    }

}
