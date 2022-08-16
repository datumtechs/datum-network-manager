package com.platon.datum.admin.common.util;

import com.platon.bech32.Bech32;
import com.platon.parameters.NetworkParameters;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Author liushuyu
 * @Date 2021/7/9 0:01
 * @Version
 * @Desc id 工具类
 */
public class DidUtil {

    public static final String DID_PREFIX = "did:pid:";

    public static String latAddressToDid(String address) {
        Objects.requireNonNull(address);
        String bech32Address = Bech32.convertToUnifiedAddress(address);
        return DID_PREFIX.concat(bech32Address);
    }

    public static String didToBech32Address(String did) {
        Objects.requireNonNull(did);
        return did.replace(DID_PREFIX, "");
    }

    public static String didToHexAddress(String did) {
        Objects.requireNonNull(did);
        String bech32Address = did.replace(DID_PREFIX, "");
        return Bech32.addressDecodeHex(bech32Address);
    }

    public static Boolean isValidDid(String did) {
        if (StringUtils.isBlank(did)) {
            return false;
        } else {
            return !did.startsWith(DID_PREFIX) ? false : isValidAddressStr(trimDidPrefix(did));
        }
    }

    public static String trimDidPrefix(String did) {
        return StringUtils.removeStart(did, DID_PREFIX);
    }

    public static boolean isValidAddressStr(String addr) {
        if (!StringUtils.isBlank(addr) && Pattern.compile("[a-zA-Z0-9]{42}").matcher(addr).matches()) {
            if (!addr.startsWith(NetworkParameters.getHrp())) {
                return false;
            } else {
                try {
                    new String(addr);
                    return true;
                } catch (Exception var2) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        //生成did
        System.out.println(latAddressToDid("lat1z602u2328hp8c9jh68mdauxchqehpd0882fgey"));
        System.out.println(latAddressToDid("0x169EaE2A2a3DC27C1657d1f6dEf0d8b83370b5e7"));
        System.out.println(latAddressToDid("0x111"));
        //解析成bech32地址
        System.out.println(didToBech32Address("did:pid:atp1z602u2328hp8c9jh68mdauxchqehpd087ulsxt"));
        System.out.println(didToBech32Address("did:pid:atp1qygsrgww2x"));
        //解析成0x地址
        System.out.println(didToHexAddress("did:pid:atp1z602u2328hp8c9jh68mdauxchqehpd087ulsxt"));
        System.out.println(didToHexAddress("did:pid:atp1qygsrgww2x"));
    }
}
