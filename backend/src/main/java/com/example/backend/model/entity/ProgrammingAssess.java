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
 * @since 2024-07-18 11:25:45
 */
@Getter
@Setter
@TableName("t_programming_assess")
public class ProgrammingAssess implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private String feedback;

    private String modifiedCode;

    private String errorAnalysis;

    private String optimizationSuggestions;

    private Integer questionId;

    private Integer userId;
}
