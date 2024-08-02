package com.example.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author feixia0g
 * @since 2024-08-02 02:49:01
 */
@Getter
@Setter
@TableName("t_user_event_log")
public class UserEventLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String userName;

    private String content;

    private Boolean deleted;

    private Date createTime;

    public UserEventLog(Integer userId, String userName, Date createTime) {
        this.userId = userId;
        this.userName = userName;
        this.createTime = createTime;
        this.deleted = false;
    }
}
