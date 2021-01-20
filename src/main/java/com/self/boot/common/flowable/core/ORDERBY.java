package com.self.boot.common.flowable.core;

/**
 * @author: jinyun
 * @date: 2021/1/20
 */
public enum ORDERBY {
    DESC,
    ASC;

    private ORDERBY() {
    }

    public ORDERBY reverse() {
        return this == ASC ? DESC : ASC;
    }
}

