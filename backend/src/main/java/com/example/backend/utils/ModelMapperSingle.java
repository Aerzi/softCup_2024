package com.example.backend.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * @version 1.0.0
 * @description: The type Model mapper single.
 * @author feixia0g
 * @date 2024/7/7 20:00
 */
public class ModelMapperSingle {
    /**
     * The constant modelMapper.
     */
    protected final static ModelMapper modelMapper = new ModelMapper();
    private final static ModelMapperSingle modelMapperSingle = new ModelMapperSingle();

    static {
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Instance model mapper.
     *
     * @return the model mapper
     */
    public static ModelMapper Instance() {
        return modelMapperSingle.modelMapper;
    }
}
