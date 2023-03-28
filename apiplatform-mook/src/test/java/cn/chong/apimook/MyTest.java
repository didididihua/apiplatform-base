package cn.chong.apimook;

import cn.chong.apimook.client.UserClient;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--02 14:36
 * @description
 */
@SpringBootTest
public class MyTest {

    @Test
    void TestClient(){
        UserClient userClient = new UserClient();
        System.out.println(userClient.getNameByGet("zhangsan"));
    }
}
