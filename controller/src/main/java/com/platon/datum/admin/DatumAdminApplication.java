package com.platon.datum.admin;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.ProposalLogService;
import com.platon.datum.admin.service.VoteContract;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.platon.datum.admin.dao")
@Slf4j
@RestController
@RequestMapping("/")
public class DatumAdminApplication {

    @Resource
    private OrgService orgService;

    @Resource
    private ProposalLogService proposalLogService;

    @Resource
    private VoteContract voteContract;

    @Value("${spring.cloud.consul.host}")
    private String consulHost;

    @Value("${spring.cloud.consul.port}")
    private Integer consulPort;

    @Value("${grpc.server.port}")
    private Integer grpcServerPort;

    public static void main(String[] args) {
        SpringApplication.run(DatumAdminApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "Hello, Datum-Admin";
    }

    /**
     * 应用启动后做一些操作
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        log.info("应用已启动，执行初始化操作.............");

        //1.注册grpc到consul
        registerGrpcService();

        //2.将org信息存到内存中
        Org org = orgService.select();
        OrgCache.setLocalOrgInfo(org);

        //3.vote合约初始化
        voteContract.init();

        //4.订阅提案相关日志
        proposalLogService.subscribe();

        log.info("执行初始化操作执行完成............");
    }

    @SneakyThrows
    private void registerGrpcService() {
        String localIp = getLocalIp();

        NewService newService = new NewService();
        newService.setId("adminGrpcService_" + localIp + "_" + grpcServerPort);
        newService.setName("adminGrpcService");
        newService.setTags(Arrays.asList("adminGrpc"));
        newService.setAddress(localIp);
        newService.setPort(grpcServerPort);
        newService.setEnableTagOverride(false);

        NewService.Check check = new NewService.Check();
        check.setGrpc(localIp + ":" + grpcServerPort);
        check.setInterval("10s");
        newService.setCheck(check);

        try {
            ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
            Response<Void> voidResponse = consulClient.agentServiceRegister(newService);
        } catch (Throwable ex) {
            log.error("Register grpc service failed!");
            throw ex;
        }
    }

    private String getLocalIp() {
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            return ip4.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Get local ip failed!");
            throw new BizException(Errors.SysException, e);
        }
    }
}
