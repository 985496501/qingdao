package com.self.boot.conf;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PrimaryBeanAutoConfiguration {
    //private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 序列化组件
     * springMVC需要使用 Redis需要使用
     *
     * @return objectMapper
     */
    //@Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN));
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//       objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 必须设置，否则无法将JSON转化为对象，会转化成Map类型  这里序列有问题
//       objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        return objectMapper;
//    }

}
