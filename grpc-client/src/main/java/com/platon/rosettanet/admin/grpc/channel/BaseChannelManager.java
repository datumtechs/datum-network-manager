package com.platon.rosettanet.admin.grpc.channel;

import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.dao.enums.CarrierConnStatusEnum;
import io.grpc.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author liushuyu
 * @Date 2021/7/7 10:27
 * @Version
 * @Desc
 */

public abstract class BaseChannelManager {

    //Channel容器
    private Map<String,Channel> channelContainer = new ConcurrentHashMap<>();;


    /**
     * 获取rpc连接,走缓存,需要维护缓存
     * @param ip
     * @param port
     * @return
     */
    public Channel getChannel(String ip, int port){
        String channelKey = getKey(ip,port);
        //1.先从缓存获取
        Channel channel = channelContainer.get(channelKey);
        if(channel == null){
            //2.缓存不存在则新建连接，并放到缓存中,这时候需要考虑到多个线程并发更新的问题
            channel = buildChannel(ip,port);
            channelContainer.put(channelKey,channel);
        }
        return channel;
    }

    /**
     * 构建一个连接,不走缓存
     * @return
     */
    public abstract Channel buildChannel(String ip,int port);

    /**
     *
     * @param ip
     * @param port
     * @throws ApplicationException
     * @return
     */
    public static String getKey(String ip, int port) throws ApplicationException{
        if(StrUtil.isBlank(ip)){
            throw new ApplicationException("ip为空");
        }
        return ip.concat(":").concat(String.valueOf(port));
    }

    /**
     * 获取调度服务连接
     * carrier_conn_Status = 'enabled' and carrier_status = 'enabled'
     * @throws ApplicationException
     * @return
     */
    public Channel getScheduleServer() throws ApplicationException{
        //获取调度服务的信息
        LocalOrg localOrgInfo = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        if(!CarrierConnStatusEnum.ENABLED.getStatus().equals(localOrgInfo.getCarrierConnStatus())){
            throw new ApplicationException("无可用的调度服务",ApplicationException.ApplicationErrorEnum.CARRIER_INFO_NOT_CONFIGURED);
        }
        //1.获取rpc连接
        Channel channel = buildChannel(localOrgInfo.getCarrierIp(), localOrgInfo.getCarrierPort());
        return channel;
    }

    /**
     * 关闭连接
     * @param channel
     */
    public abstract void closeChannel(Channel channel);
}
