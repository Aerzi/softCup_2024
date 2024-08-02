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
 * @since 2024-07-15 06:27:04
 */
@Getter
@Setter
@TableName("t_question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String format;

    private String example;

    private String difficult;

    private String tips;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    private Boolean deleted;

    private Integer classId;
}
