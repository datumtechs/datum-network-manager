package com.platon.datum.admin.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Map;

/**
 * @description 钱包签名验证签名工具类
 */

@Slf4j
public class WalletSignUtil {
    /**
     * 签名
     *
     * @param message   签名消息
     * @param ecKeyPair 密钥对
     * @return 签名值
     */
    @SuppressWarnings("unused")
    public static String sign(String message, ECKeyPair ecKeyPair) {
        Sign.SignatureData signatureData = Sign.signMessage(message.getBytes(StandardCharsets.UTF_8), ecKeyPair);
        byte[] sigData = new byte[65];
        sigData[0] = signatureData.getV()[0];
        System.arraycopy(signatureData.getR(), 0, sigData, 1, 32);
        System.arraycopy(signatureData.getS(), 0, sigData, 33, 32);
        return new String(Base64.encodeBase64(sigData), StandardCharsets.UTF_8);
    }

    /**
     * 验签
     *
     * @param message 签名明文
     * @param signMsg 签名信息
     * @param address 签名人地址
     * @return 验证成功失败标识
     */
    @SuppressWarnings("unused")
    public static boolean verifySign(String message, String signMsg, String address) {
        byte[] bs = Base64.decodeBase64(signMsg);
        Sign.SignatureData signatureData = new Sign.SignatureData(bs[0], Arrays.copyOfRange(bs, 1, 33), Arrays.copyOfRange(bs, 33, 65));

        BigInteger bigInteger;
        try {
            bigInteger = Sign.signedMessageToKey(message.getBytes(StandardCharsets.UTF_8), signatureData);
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }
        String tmpAddress = Keys.getAddress(bigInteger);
        return Numeric.cleanHexPrefix(address).equals(Numeric.cleanHexPrefix(tmpAddress));
    }

    /**
     * 验证Sign Typed Data v4 的签名
     *
     * @param jsonMessage json格式签名明文
     * @param signMsg     签名数据
     * @param address     hrp钱包地址
     * @return 是否成功标识
     */
    public static boolean verifyTypedDataV4(String jsonMessage, String signMsg, String address) throws IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessage);
        Map<Integer, String> addresses = CryptoUtil.ecRecover(signMsg, dataEncoder.hashStructuredData());
        return addresses.toString().contains(AddressChangeUtil.convert0xAddress(address).toLowerCase());
    }

    /**
     * 对数据进行eth_signTypedData_v4签名
     *
     * @param jsonMessage 签名数据结构体
     * @param ecKeyPair   密钥对
     * @return 签名字符串
     */
    public static String signTypedDataV4(String jsonMessage, ECKeyPair ecKeyPair) throws IOException {
        StrUtil.replace(jsonMessage, "\\\"", "\"");
        StructuredDataEncoder encoder = new StructuredDataEncoder(jsonMessage);
        byte[] hash = encoder.hashStructuredData();
        Sign.SignatureData signatureData = Sign.signMessage(hash, ecKeyPair, false);
        byte[] bytesValue = ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        return "0x" + DatatypeConverter.printHexBinary(bytesValue).toLowerCase();
    }

    public static void main(String[] args) {
        createLoginSign("8f94ec8c250844fea57f319769b498d0",Credentials.create("0x03a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668"));
    }

    /**
     * 创建登录签名
     * @param nonce nonce，登录前需要先获取nonce {@link UserController#getLoginNonce(String)}
     * @param credentials 钱包凭证
     */
    public static String createLoginSign(String nonce,Credentials credentials){
        log.info("address>>>" + credentials.getAddress());
        //登录签名
        String json = "{\"domain\":{\"name\":\"Metis\"},\"message\":{\"key\":\"{}\",\"desc\":\"Welcome to Metis!\"},\"primaryType\":\"Login\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"}],\"Login\":[{\"name\":\"key\",\"type\":\"string\"},{\"name\":\"desc\",\"type\":\"string\"}]}}";
        try {
            json = StrUtil.format(json, nonce);
            log.info("signMessage>>>" + json);
            String sign = signTypedDataV4(json, credentials.getEcKeyPair());
            log.info("sign>>>" + sign);
            return sign;
        } catch (Exception e) {
            log.error("创建登录签名失败！",e);
        }
        return null;
    }
}
