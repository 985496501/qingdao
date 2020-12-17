package com.self.boot.loader;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.io.support.SpringFactoriesLoader;
import reactor.core.publisher.Flux;

import java.util.List;

public class SpringFactoriesLoaderTest {

    @Test
    public void loadFactoryNamesTest() {
        String location = SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION;
        System.out.println(location);
        List<String> strings = SpringFactoriesLoader.loadFactoryNames(ApplicationContextInitializer.class, null);
        Flux.fromIterable(strings).subscribe(System.out::println);
    }
}
