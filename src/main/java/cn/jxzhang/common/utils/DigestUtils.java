package cn.jxzhang.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2016-11-08 09:55
 * <p>Title:       [Title]</p>
 * <p>Description: [Description]</p>
 * <p>Company:     [Company Name]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author <a href=zhangjx_dev@163.com>J.X.Zhang</a>
 * @version 1.0
 */
public class DigestUtils {

    private DigestUtils() { /* cannot be instantiated */ }

    private static final String MD5_ALGORITHM_NAME = "MD5";

    private static final int BUFFER_SIZE = 4096;

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Return a hexadecimal string representation of the MD5 digest of the given string.
     *
     * @param str the string to calculate the digest over
     * @return a hexadecimal digest string, return null if str is null.
     */
    public static String md5DigestAsHex(String str) {
        return md5DigestAsHex(str.getBytes());
    }

    /**
     * Return a hexadecimal string representation of the MD5 digest of the given bytes.
     *
     * @param bytes the bytes to calculate the digest over
     * @return a hexadecimal digest string
     */
    public static String md5DigestAsHex(byte[] bytes) {
        return digestAsHexString(MD5_ALGORITHM_NAME, bytes);
    }

    /**
     * Return a hexadecimal string representation of the MD5 digest of the given stream.
     *
     * @param inputStream the InputStream to calculate the digest over
     * @return a hexadecimal digest string
     * @since 1.0
     */
    public static String md5DigestAsHex(InputStream inputStream) throws IOException {
        return digestAsHexString(MD5_ALGORITHM_NAME, inputStream);
    }

    /**
     * Create a new {@link MessageDigest} with the given algorithm.
     * Necessary because {@code MessageDigest} is not thread-safe.
     */
    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    private static byte[] digest(String algorithm, byte[] bytes) {
        return getDigest(algorithm).digest(bytes);
    }

    private static byte[] digest(String algorithm, InputStream inputStream) throws IOException {
        MessageDigest messageDigest = getDigest(algorithm);
        if (inputStream instanceof UpdateMessageDigestInputStream) {
            ((UpdateMessageDigestInputStream) inputStream).updateMessageDigest(messageDigest);
            return messageDigest.digest();
        } else {
            final byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return messageDigest.digest();
        }
    }

    private static String digestAsHexString(String algorithm, byte[] bytes) {
        char[] hexDigest = digestAsHexChars(algorithm, bytes);
        return new String(hexDigest);
    }

    private static String digestAsHexString(String algorithm, InputStream inputStream) throws IOException {
        char[] hexDigest = digestAsHexChars(algorithm, inputStream);
        return new String(hexDigest);
    }

    private static char[] digestAsHexChars(String algorithm, byte[] bytes) {
        byte[] digest = digest(algorithm, bytes);
        return encodeHex(digest);
    }

    private static char[] digestAsHexChars(String algorithm, InputStream inputStream) throws IOException {
        byte[] digest = digest(algorithm, inputStream);
        return encodeHex(digest);
    }

    private static char[] encodeHex(byte[] bytes) {
        char chars[] = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }

    abstract class UpdateMessageDigestInputStream extends InputStream {

        /**
         * Update the message digest with the rest of the bytes in this stream.
         * <p>Using this method is more optimized since it avoids creating new
         * byte arrays for each call.
         *
         * @param messageDigest the message digest to update
         * @throws IOException when propagated from {@link #read()}
         */
        public void updateMessageDigest(MessageDigest messageDigest) throws IOException {
            int data;
            while ((data = read()) != -1) {
                messageDigest.update((byte) data);
            }
        }

        /**
         * Update the message digest with the next len bytes in this stream.
         * <p>Using this method is more optimized since it avoids creating new
         * byte arrays for each call.
         *
         * @param messageDigest the message digest to update
         * @param len           how many bytes to read from this stream and use to update the message digest
         * @throws IOException when propagated from {@link #read()}
         */
        public void updateMessageDigest(MessageDigest messageDigest, int len) throws IOException {
            int data;
            int bytesRead = 0;
            while (bytesRead < len && (data = read()) != -1) {
                messageDigest.update((byte) data);
                bytesRead++;
            }
        }
    }
}
