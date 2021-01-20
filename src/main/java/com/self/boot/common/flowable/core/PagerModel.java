package com.self.boot.common.flowable.core;

import com.github.pagehelper.Page;
import liquibase.pro.packaged.T;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分页模型可自行按需修改
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
@NoArgsConstructor
public class PagerModel<P> {
    private static final long serialVersionUID = 4804053559968742915L;
    private long total;
    private List<T> data = new ArrayList();
    private List<T> rows = new ArrayList();

    public PagerModel(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page)list;
            this.data = page;
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.data = list;
            this.total = (long)list.size();
        }

    }

    public PagerModel(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
        this.data = rows;
    }
}
