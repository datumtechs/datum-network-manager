package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.json.JSONUtil;
import com.platon.rosettanet.admin.grpc.entity.TaskDataResp;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.TaskRpcMessage;
import com.platon.rosettanet.admin.grpc.service.TaskServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = TestApplication.class)
public class TaskClientTest {

    @Resource
    TaskClient taskClient;

    @Test
    public void getTaskList(){
        TaskDataResp taskListData = taskClient.getTaskListData();
        log.info(JSONUtil.toJsonStr(taskListData));
    }


//    /**
//     * 启动一个服务
//     * @throws Exception
//     */
//    @Before
//    public void startService() throws Exception {
//        /* The port on which the server should run */
//        int port = 50051;
//        //这个部分启动server
//        NettyServerBuilder.forPort(port)
//                .addService(new TaskServiceImpl().bindService())
//                .build()
//                .start();
//        System.out.println("Task server started, listening on " + port);
//    }

    /**
     * 测试客户端
     */
    @Test
    public void test() {
        Channel channel =  ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();


        CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams.newBuilder().build();
        TaskRpcMessage.GetTaskDetailListResponse taskDetailListResponse = TaskServiceGrpc.newBlockingStub(channel).getTaskDetailList(request);
        int status = taskDetailListResponse.getStatus();
        String msg = taskDetailListResponse.getMsg();
        List<TaskRpcMessage.GetTaskDetailResponse> taskDetailResponseList = taskDetailListResponse.getTaskListList();
        System.out.println("############### status:" + status);
        System.out.println("############### msg:" + msg);
        System.out.println("############### data:" + taskDetailResponseList.toString());

    }


    /**
     * 服务端实现
     */

    static class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

        @Override
        public void getTaskDetailList(CommonMessage.EmptyGetParams request, StreamObserver<TaskRpcMessage.GetTaskDetailListResponse> responseObserver) {
            System.out.println("########### request:" + request);


            TaskRpcMessage.TaskDetailShow taskDetailShow = TaskRpcMessage.TaskDetailShow.newBuilder()
                                                               .setTaskId("001")
                                                               .setTaskName("taskName")
                                                               .build();
            TaskRpcMessage.GetTaskDetailResponse  taskDetailResponse = TaskRpcMessage.GetTaskDetailResponse.newBuilder()
                                                                           .setInformation(taskDetailShow)
                                                                           .build();

            TaskRpcMessage.GetTaskDetailListResponse response = TaskRpcMessage
                                                                  .GetTaskDetailListResponse
                                                                  .newBuilder()
                                                                  .setStatus(0)
                                                                  .setMsg("seccess")
                                                                  .addTaskList(taskDetailResponse)
                                                                  .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();


        }
    }





}
