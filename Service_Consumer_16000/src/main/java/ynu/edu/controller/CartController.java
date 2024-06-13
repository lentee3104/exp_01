package ynu.edu.controller;

import ynu.edu.entity.User;
import jakarta.annotation.Resource;
import ynu.edu.entity.CommonResult;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/getCartById/{userId}")
    public CommonResult<User> getCartById(@PathVariable("userId") Integer userId) {
        List<ServiceInstance> instanceList = discoveryClient.getInstances("provider-server");
        for (ServiceInstance instance : instanceList) {
            System.out.println(instance.getHost() + "\t" + instance.getPort());
        }
        ServiceInstance instance = instanceList.get(0);
        CommonResult<User> result = restTemplate.getForObject(
                "http://" + instance.getHost() + ":" + instance.getPort() + "/user/getUserById/" + userId.toString(), CommonResult.class);
        return result;
    }
}
