package com.platon.datum.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.dao.entity.Task;
import com.platon.datum.admin.dao.entity.TaskOrg;
import com.platon.datum.admin.grpc.carrier.api.TaskRpcApi;
import com.platon.datum.admin.grpc.carrier.api.TaskServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.TaskData;
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
        TaskRpcApi.GetTaskDetailListRequest request = TaskRpcApi.GetTaskDetailListRequest
                .newBuilder()
                .setLastUpdated(lastUpdated.toInstant(ZoneOffset.UTC).toEpochMilli())
                .build();

        TaskRpcApi.GetTaskDetailListResponse taskDetailListResponse = TaskServiceGrpc.newBlockingStub(channel).getLocalTaskDetailList(request);
        int status = taskDetailListResponse.getStatus();
        String msg = taskDetailListResponse.getMsg();
        List<TaskData.TaskDetail> taskDetailResponseList = taskDetailListResponse.getTasksList();
        System.out.println("############### status:" + status);
        System.out.println("############### msg:" + msg);
        System.out.println("############### data:" + taskDetailResponseList.toString());

    }


    /**
     * 服务端实现
     */

    static class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

        @Override
        public void getLocalTaskDetailList(TaskRpcApi.GetTaskDetailListRequest request,
                                           io.grpc.stub.StreamObserver<TaskRpcApi.GetTaskDetailListResponse> responseObserver) {
            System.out.println("########### request:" + request);


            TaskData.TaskDetailSummary taskDetailShow = TaskData.TaskDetailSummary.newBuilder()
                    .setTaskId("001")
                    .setTaskName("taskName")
                    .build();
            TaskData.TaskDetail taskDetailResponse = TaskData.TaskDetail.newBuilder()
                    .setInformation(taskDetailShow)
                    .build();

            TaskRpcApi.GetTaskDetailListResponse response =
                    TaskRpcApi.GetTaskDetailListResponse
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
