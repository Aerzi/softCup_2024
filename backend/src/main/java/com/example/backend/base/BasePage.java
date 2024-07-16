package com.example.backend.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0.0
 * @description: The type Base page.
 * @author feixia0g
 * @date 2024/7/10 22:00
 */
@Setter
@Getter
public class BasePage {

    /**
     * -- GETTER --
     *  Gets page index.
     *
     *
     * -- SETTER --
     *  Sets page index.
     *
     @return the page index
      * @param pageIndex the page index
     */
    private Integer pageIndex;

    /**
     * -- GETTER --
     *  Gets page size.
     *
     *
     * -- SETTER --
     *  Sets page size.
     *
     @return the page size
      * @param pageSize the page size
     */
    private Integer pageSize;

}

