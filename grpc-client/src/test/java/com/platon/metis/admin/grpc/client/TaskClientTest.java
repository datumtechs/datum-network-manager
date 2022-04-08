package com.platon.metis.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.entity.TaskOrg;
import com.platon.metis.admin.grpc.service.TaskRpcMessage;
import com.platon.metis.admin.grpc.service.TaskServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TaskClientTest {

    @Resource
    TaskClient taskClient;


    @Test
    public void getTaskList() {
        LocalDateTime lastUpdated = LocalDateTime.parse("1970-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Pair<List<Task>, Map<String, TaskOrg>> resp = taskClient.getLocalTaskList(lastUpdated);
        log.info("left:{}", JSON.toJSONString(resp.getLeft()));
        log.info("right:{}", JSON.toJSONString(resp.getRight()));
    }

    @Test
    public void getTaskListByT() {
        LocalDateTime lastUpdated = LocalDateTime.parse("1970-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Pair<List<Task>, Map<String, TaskOrg>> resp = taskClient.getLocalTaskList(lastUpdated);
        log.info("left:{}", JSON.toJSONString(resp.getLeft()));
        log.info("right:{}", JSON.toJSONString(resp.getRight()));
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
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        LocalDateTime lastUpdated = LocalDateTime.parse("1970-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        TaskRpcMessage.GetTaskDetailListRequest request = TaskRpcMessage.GetTaskDetailListRequest
                .newBuilder()
                .setLastUpdated(lastUpdated.toInstant(ZoneOffset.UTC).toEpochMilli())
                .build();

        TaskRpcMessage.GetTaskDetailListResponse taskDetailListResponse = TaskServiceGrpc.newBlockingStub(channel).getLocalTaskDetailList(request);
        int status = taskDetailListResponse.getStatus();
        String msg = taskDetailListResponse.getMsg();
        List<TaskRpcMessage.GetTaskDetail> taskDetailResponseList = taskDetailListResponse.getTasksList();
        System.out.println("############### status:" + status);
        System.out.println("############### msg:" + msg);
        System.out.println("############### data:" + taskDetailResponseList.toString());

    }


    /**
     * 服务端实现
     */

    static class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

        @Override
        public void getLocalTaskDetailList(com.platon.metis.admin.grpc.service.TaskRpcMessage.GetTaskDetailListRequest request,
                                           io.grpc.stub.StreamObserver<com.platon.metis.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> responseObserver) {
            System.out.println("########### request:" + request);


            TaskRpcMessage.TaskDetailShow taskDetailShow = TaskRpcMessage.TaskDetailShow.newBuilder()
                    .setTaskId("001")
                    .setTaskName("taskName")
                    .build();
            TaskRpcMessage.GetTaskDetail taskDetailResponse = TaskRpcMessage.GetTaskDetail.newBuilder()
                    .setInformation(taskDetailShow)
                    .build();

            TaskRpcMessage.GetTaskDetailListResponse response = TaskRpcMessage
                    .GetTaskDetailListResponse
                    .newBuilder()
                    .setStatus(0)
                    .setMsg("seccess")
                    .addTasks(taskDetailResponse)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();


        }
    }


}
