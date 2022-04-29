package com.example.pep.iot.test.queue;

import com.example.pep.iot.response.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author LiuGang
 * @since 2022-04-23 17:16
 */
@RequestMapping("/queue")
@RestController
public class QueueController {

    private final BlockQueue blockQueue;

    private QueueController(BlockQueue blockQueue) {
        this.blockQueue = blockQueue;
    }

    @PostMapping("/send")
    public ResultVO<Boolean> send(@RequestBody Message message) {
        String data = message.getData();
        blockQueue.send(data, message.getTime(), TimeUnit.SECONDS);
        return ResultVO.ok(true);
    }

    @PostMapping("/remove")
    public ResultVO<Boolean> remove(@RequestBody Message message) {
        String data = message.getData();
        boolean remove = blockQueue.remove(data);
        return ResultVO.ok(remove);
    }

    @PostMapping("/contain")
    public ResultVO<Boolean> contain(@RequestBody Message message) {
        String data = message.getData();
        boolean contains = blockQueue.contains(data);
        return ResultVO.ok(contains);
    }
}
