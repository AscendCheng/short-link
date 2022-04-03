package com.cyx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * EventMessage.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventMessage implements Serializable {
    /**
     * 消息id.
     */
    private String messageId;

    /**
     * 消息类型.
     */
    private String eventMessageType;

    /**
     * 业务id.
     */
    private String bizId;

    /**
     * 账号.
     */
    private Long account;

    /**
     * 消息体.
     */
    private String content;

    /**
     * 备注.
     */
    private String remark;
}
