package com.self.boot.util;


import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;

/**
 * 学习spring的class utils
 * 针对的是 org.springframework.util
 * 即spring框架最基础的字节码工具类
 */
public class ClassUtilsTest {

    @Test
    public void getCurrentThreadClassLoaderTest() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader); // Launcher$AppClassLoader
    }

    @Test
    public void classLoaderTest() {
        // 可以直接调用这个类加载器 先获取当前线程的类加载器 然后获取classUtils的classloader 然后获取 sysClassLoader
        // 好处就是不用传入参数
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        System.out.println(classLoader);  // Launcher$AppClassLoader
    }

    @Test
    public void forName0Test() throws ClassNotFoundException {
        // 可以加载普通类但是加载不到静态内部类 com.self.boot.util.ClassUtilsTest$A
        // 内部类要使用 全限定性类型.外部类名$内部类名
        Class<?> name = Class.forName("int[]");
        System.out.println("getName: " + name.getName());
        System.out.println("getSimpleName: " + name.getSimpleName());
        System.out.println("getTypeName: " + name.getTypeName());
    }


    @Test
    public void resolvePrimitiveTypeTest() {
        String name = float[].class.getName();  //[I == int  [Ljava.lang.String;=string [F = float
        System.out.println(name);

        // primitive: int char short float double boolean byte long 8个基本数据类型
        Class<?> className = ClassUtils.resolvePrimitiveClassName("[I");
        System.out.println(className);
    }

    @Test
    public void forNameTest() throws Exception {
        // 相比class 支持了基本数据类型
        Class<?> clazz = ClassUtils.forName("com.self.boot.util.ClassUtilsTest$A", null);
        A o = (A)clazz.getDeclaredConstructor().newInstance();
        System.out.println(clazz);
        System.out.println(o);
    }

    @Test
    public void resolveClassNameTest() {
        Class<?> clazz = ClassUtils.resolveClassName("com.self.boot.util.ClassUtilsTest$A", null);
        System.out.println(clazz);
    }


    @Data
    static class A {
        private int i;
    }

}
