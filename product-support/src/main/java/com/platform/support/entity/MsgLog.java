package com.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 消息投递日志(MsgLog)表实体类
 *
 * @author zengzheng
 * @since 2021-10-13 14:18:31
 */
@Data
@TableName("msg_log")
@NoArgsConstructor
@AllArgsConstructor
public class MsgLog implements Serializable {


    private static final long serialVersionUID = -27664668737745887L;
        
    /**
    * 消息唯一标识
    */                      
    @TableId
    private String msgId;
                    
    /**
    * 消息体, json格式化
    */                        
    private String msg;
                    
    /**
    * 交换机
    */                        
    private String exchange;
                    
    /**
    * 路由键
    */                        
    private String routingKey;

    /**
     * 生产者
     */
    private String producer;

    /**
     * 消费者
     */
    private String consumer;
                    
    /**
    * 状态: 0投递中 1投递成功 2投递失败 3已消费
    */                        
    private Integer status;
                    
    /**
    * 重试次数
    */                        
    private Integer tryCount;
                    
    /**
    * 创建时间
    */                        
    private Date createTime;
                    
    /**
    * 更新时间
    */                        
    private Date updateTime;

    public MsgLog(String msgId, Integer status, Integer tryCount) {
        this.msgId = msgId;
        this.status = status;
        if (Objects.nonNull(tryCount)) {
            this.tryCount = tryCount;
        }
    }

    public MsgLog(String msgId, String msg, String exchange, String routingKey, String producer, String consumer, Date createTime) {
        this.msgId = msgId;
        this.msg = msg;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.producer = producer;
        this.consumer = consumer;
        this.createTime = createTime;
    }
}