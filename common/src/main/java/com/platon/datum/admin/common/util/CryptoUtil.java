package com.platon.datum.admin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CryptoUtil {
    static final int V_27 = 27;
    static final int V_4 = 4;
    private static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    private CryptoUtil() {
    }

    @SuppressWarnings("unused")
    public static Map<Integer, String> ecRecoverPersonalSignature(String signature, String message) {

        log.debug("ecRecoverPersonalSignature(signature=" + signature + ", message=" + message + ")");

        // Message
        String pref = PERSONAL_MESSAGE_PREFIX + message.length();

        byte[] msgHash = Hash.sha3((pref + message).getBytes());
        log.trace("msgHash=" + Numeric.toHexString(msgHash));

        return ecRecover(signature, msgHash);
    }

    public static Map<Integer, String> ecRecover(String signature, byte[] msgHash) {

        log.debug("ecRecover (signature=" + signature + ", msgHash=" + DatatypeConverter.printHexBinary(msgHash) + ")");

        // Signature
        byte[] array = Numeric.hexStringToByteArray(signature);
        byte v = array[64];
        if (v < V_27) {
            v += V_27;
        }

        SignatureData sd = new SignatureData(v, Arrays.copyOfRange(array, 0, 32), Arrays.copyOfRange(array, 32, 64));

        Map<Integer, String> addresses = new HashMap<>(10);

        // Iterate for each possible key to recover
        for (int i = 0; i < V_4; i++) {
            BigInteger publicKey = Sign.recoverFromSignature((byte) i, new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())), msgHash);
            log.trace("publicKey (" + i + ") = " + publicKey);

            if (publicKey != null) {
                addresses.put(i, "0x" + Keys.getAddress(publicKey));
            }
        }

        log.debug("checkSig(signature=" + signature + ", msgHash=" + DatatypeConverter.printHexBinary(msgHash) + ") => addresses recovered " + addresses);

        return addresses;
    }
}
