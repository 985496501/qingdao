package com.self.boot.common.flowable.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 审批的意见类型
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Getter
@RequiredArgsConstructor
public enum CommentEnum {
    SP("审批"),
    BH("驳回"),
    CH("撤回"),
    ZC("暂存"),
    QS("签收"),
    WP("委派"),
    ZH("知会"),
    ZY("转阅"),
    YY("已阅"),
    ZB("转办"),
    QJQ("前加签"),
    HJQ("后加签"),
    XTZX("系统执行"),
    TJ("提交"),
    CXTJ("重新提交"),
    SPJS("审批结束"),
    LCZZ("流程终止"),
    SQ("授权"),
    CFTG("重复跳过"),
    XT("协同"),
    PS("评审");
    private final String name;

    /**
     * 通过type获取Msg
     */
    public static String getEnumMsgByType(String type) {
        for (CommentEnum e : CommentEnum.values()) {
            if (e.toString().equals(type)) {
                return e.name;
            }
        }

        return "";
    }
}
