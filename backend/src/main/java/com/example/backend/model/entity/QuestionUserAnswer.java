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
 * @since 2024-07-15 09:20:38
 */
@Getter
@Setter
@TableName("t_question_user_answer")
public class QuestionUserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String answer;

    private Integer score;

    private Boolean deleted;

    private Integer userId;

    private Integer questionId;

    private Integer classId;
}
