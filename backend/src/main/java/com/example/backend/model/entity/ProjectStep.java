package com.example.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-17 03:22:14
 */
@Getter
@Setter
@TableName("t_project_step")
public class ProjectStep implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String description;

    private String assess;

    private Boolean finished;

    private Boolean deleted;

    private Integer projId;
}
