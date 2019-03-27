package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/03/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Message implements Serializable {
    private Integer id;

    /**
     * 消息发送者id：0-系统
     */
    private Integer fromUserId;

    /**
     * 消息发送者类型：0-系统 1-客户 2-司机 3-员工
     */
    private Integer fromUserType;

    /**
     * 消息接受者id：0-系统
     */
    private Integer toUserId;

    /**
     * 消息接受者类型：0-系统 1-客户 2-司机 3-员工
     */
    private Integer toUserType;

    /**
     * 消息头
     */
    private String header;

    /**
     * 消息体
     */
    private String body;

    /**
     * 分类 1-通知,2-提醒
     */
    private Integer category;

    /**
     * 消息类型：1-系统通知(公告) 
     */
    private Integer msgType;

    /**
     * 消息来源:1-APP,2-小程序,3-站内
     */
    private Integer msgSource;

    /**
     * 关联养殖计划Id 等
     */
    private Integer referId;

    /**
     * 关联id类型
     */
    private Integer referType;

    /**
     * 消息状态：0-未读 1-已读
     */
    private Integer msgStatus;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Byte enabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

    private static final long serialVersionUID = 1L;
}