package com.platon.metis.admin.common.util;

public class DataChangeUtils {

    static final String ADDRESS_0X_PRE = "0x";
    static final int ODD = 2;

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    @SuppressWarnings("unused")
    public static byte[] hexToByteArray(String inHex) {
        if (inHex.toLowerCase().startsWith(ADDRESS_0X_PRE)) {
            inHex = inHex.substring(2);
        }
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % ODD == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += ODD) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    @SuppressWarnings("all")
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < ODD) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
