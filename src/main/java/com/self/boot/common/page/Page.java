package com.self.boot.common.page;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> list;

    private Integer page;

    private Integer pageSize;
}
