package com.platon.metis.admin.grpc.client;

import com.platon.metis.admin.grpc.service.AuthRpcMessage;
import com.platon.metis.admin.grpc.service.AuthServiceGrpc;
import com.platon.metis.admin.grpc.types.Base;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.Test;


/**
 * @Author liushuyu
 * @Date 2021/7/6 19:50
 * @Version
 * @Desc
 */
public class DemoClientTest {


    /**
     * 启动一个服务
     * @throws Exception
     */
//    @Before
//    public void startService() throws Exception {
//        /* The port on which the server should run */
//        int port = 50051;
//        //这个部分启动server
//        NettyServerBuilder.forPort(port)
//                .addService(new AuthServiceImpl().bindService())
//                .build()
//                .start();
//        System.out.println("Server started, listening on " + port);
//    }

    /**
     * 测试客户端
     */
    @Test
    public void test() {
        Channel channel = ManagedChannelBuilder.forAddress("192.168.21.164", 4444).usePlaintext().build();

        AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest.newBuilder().build();
        Base.SimpleResponse responseCode = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        System.out.println("###############" + responseCode.getMsg());
        System.out.println("111111111");
    }


    /**
     * 服务端实现
     */
    static class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

        @Override
        public void applyIdentityJoin(AuthRpcMessage.ApplyIdentityJoinRequest request, StreamObserver<Base.SimpleResponse> responseObserver) {
            System.out.println(request);
            Base.SimpleResponse code = Base.SimpleResponse.newBuilder().setMsg("goooooooooooo").setStatus(0).build();
            //responseObserver.onNext(code);
            responseObserver.onCompleted();
        }
    }
}
