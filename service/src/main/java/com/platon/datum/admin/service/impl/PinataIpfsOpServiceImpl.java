package com.platon.datum.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.service.IpfsOpService;
import com.platon.datum.admin.service.client.PinataClient;
import com.platon.datum.admin.service.client.PinataGatewayClient;
import com.platon.datum.admin.service.client.dto.PinataPinJSONToIPFSReq;
import com.platon.datum.admin.service.client.dto.PinataPinResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/22 18:33
 * @Version
 * @Desc
 */


@Slf4j
@Service
public class PinataIpfsOpServiceImpl implements IpfsOpService {

    public static final String prefix_ipfs = "ipfs://";

    @Value("Bearer ${pinata-token}")
    private String token;

    @Resource
    private PinataClient pinataClient;
    @Resource
    private PinataGatewayClient pinataGatewayClient;

    /**
     * 保存图片
     *
     * @return
     */
    @Override
    public String saveFile(MultipartFile file) {
        PinataPinResult pinataPinResult = null;
        try {
            pinataPinResult = pinFileToIPFS(file);
        } catch (Exception exception) {
            throw new BizException(Errors.UploadFileFailed, exception);
        }
        return getIpfsLink(pinataPinResult);
    }

    /**
     * 保存json
     *
     * @return
     */
    @Override
    public String saveJson(Object content) {
        PinataPinResult pinataPinResult = null;
        try {
            pinataPinResult = pinJSONToIPFS(content);
        } catch (Exception exception) {
            throw new BizException(Errors.UploadFileFailed, exception);
        }
        return getIpfsLink(pinataPinResult);
    }

    /**
     * @param url
     * @return
     */
    @Override
    public <T> T queryJson(String url, Class<T> tClass) {
        if (StrUtil.isBlank(url)) {
            throw new ValidateException("Url can't be blank");
        }
        if (!url.startsWith(prefix_ipfs)) {
            throw new ValidateException("Url need to start with : " + prefix_ipfs);
        }
        String cid = StrUtil.removePrefix(url, prefix_ipfs);
        Object json = null;
        try {
            json = pinataGatewayClient.getJsonFromIpfs(cid);
        } catch (Throwable ex) {
            throw new BizException(Errors.DownloadFailed,ex);
        }
        String jsonStr = JSONUtil.toJsonStr(json);
        return parseJson(jsonStr,tClass);
    }

    private <T> T parseJson(String jsonStr,Class<T> tClass) {
        if (JSONUtil.isJson(jsonStr)) {
            T t = JSONUtil.toBean(jsonStr, tClass);
            return t;
        } else {
            log.debug("jsonStr is not json : {}", jsonStr);
            return null;
        }
    }

    private String getIpfsLink(PinataPinResult pinataPinResult) {
        String ipfsHash = pinataPinResult.getIpfsHash();
        return prefix_ipfs + ipfsHash;
    }

    private PinataPinResult pinFileToIPFS(MultipartFile file) {
        PinataPinResult pinataPinResult = pinataClient.pinFileToIPFS(token, file);
        return pinataPinResult;
    }


    private PinataPinResult pinJSONToIPFS(Object content) {
        PinataPinJSONToIPFSReq req = new PinataPinJSONToIPFSReq();
        req.setPinataContent(content);
        PinataPinResult pinataPinResult = pinataClient.pinJSONToIPFS(token, req);
        return pinataPinResult;
    }

}
