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
 * @since 2024-07-10 03:18:46
 */
@Getter
@Setter
@TableName("t_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String extension;

    private String type;

    private String filePath;

    private Date createTime;

    private Date modifyTime;

    private Integer size;

    private Integer status;

    private String description;

    private Boolean deleted;

    private Integer classId;

    private Integer userId;
}
