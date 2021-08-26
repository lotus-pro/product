package com.platform.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.collect.Maps;
import com.platform.admin.service.ProductUserService;
import com.platform.admin.thread.TestThread;
import com.platform.admin.util.ThreadPoolUtil;
import com.platform.common.cache.Cache;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.util.AuthenticationUtils;
import com.platform.core.util.JWTUtils;
import com.platform.product.entity.admin.ProductUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Api(tags = {"登录处理类"})
@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController extends BaseController {

    @Autowired
    ProductUserService productUserService;
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    Cache cache;
    @Autowired
    ThreadPoolUtil threadPoolUtil;

//        ArrayList<Map<String, Object>> list = Lists.newArrayList();
//        for (int i = 0; i < 20; i++) {
//            HashMap<String, Object> map = Maps.newHashMap();
//            map.put("id", i + 1);
//            map.put("name", "test" + (i + 1));
//            list.add(map);
//        }
//        HashMap<String, Object> map1 = Maps.newHashMap();
//        map1.put("id", "1");
//        map1.put("name", "test1");
//        list.add(map1);
//        threadPoolUtil.aa();
//        System.out.println("------------");
//        for (Map<String, Object> strMap : list) {
//            TestThread testThread = new TestThread(strMap.get("id").toString(), (String) strMap.get("name"));
//            threadPoolUtil.execute(testThread);
//        }
//        threadPoolUtil.aa();

    public static void main(String[] args) throws Exception {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new TestThread("1", "2");
            pool.execute(myrun);
        }
        // 关闭线程池
        pool.shutdown();
    }

    @PostMapping("/system/login/success")
    public ResponseResult loginSucces(@RequestBody Map<String, Object> params) {
        String username = (String)params.get("username");
        Map<String, Object> map = Maps.newHashMap();
        ProductUser productUser = productUserService.getById(username);
        map.put("userInfo", productUser);
        map.put("access_token_expire_timestamp", 1800);
        map.put("identity", username);
        String access_token = JWTUtils.sign(map, 1800);
        map.put("access_token", access_token);
        AuthenticationUtils.setUserMap(username, map);
        return result(map);
    }

    @PostMapping("/system/loginOut")
    public ResponseResult loginOut(@RequestBody Map<String, Object> params) {
        String username = (String)params.get("username");
        AuthenticationUtils.removeUserMap(username);
        return result();
    }

}
