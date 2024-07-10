package com.example.backend.model.request;

import com.example.backend.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;

/**
 * @version 1.0.0
 * @description: The type Base vm.
 * @author feixia0g
 * @date 2024/7/10 22:35
 */
public class BaseRequest {
    /**
     * The constant modelMapper.
     */
    protected static ModelMapper modelMapper = ModelMapperSingle.Instance();
}
