package com.example.backend.base;

import com.example.backend.context.WebContext;
import com.example.backend.model.entity.User;
import com.example.backend.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @description The type Base api controller.
 * @author feixia0g
 * @date 2024/7/7 10:50
 */
@Component
public class BaseApiController {
    /**
     * The constant modelMapper.
     */
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    /**
     * The Web context.
     */
    @Autowired
    protected WebContext webContext;

    /**
     * Gets current user.
     *
     * @return the current user
     */
    protected User getCurrentUser() {
        return webContext.getCurrentUser();
    }

}
