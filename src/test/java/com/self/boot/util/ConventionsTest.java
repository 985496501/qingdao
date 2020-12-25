package com.self.boot.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.Conventions;

/**
 * social conventions 社会习俗
 * international conventions 国际公约
 *
 * 约定的工具类
 */
@Slf4j
public class ConventionsTest {

    @Test
    public void getQualifiedTest() {
        // get qualified class name . attributeName  ==  就是获取 这个字节码类的全限定类名.strName
        String attributeName = Conventions.getQualifiedAttributeName(ConventionsTest.class, "convention");
        log.debug(attributeName);
    }




}
