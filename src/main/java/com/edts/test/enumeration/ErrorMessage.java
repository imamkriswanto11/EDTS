package com.edts.test.enumeration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "enum.error.message")
public class ErrorMessage {

    @Autowired
    private Locale defaultLocale;

    private Map<String, Map<String, String>> messages;

    public String getMessage(Locale locale, String key) {
        if(locale==null) {
            locale = defaultLocale;
        }
        if(getMessages()==null || StringUtils.isEmpty(key)) {
            return "";
        }
        if(!getMessages().containsKey(key) || !getMessages().get(key).containsKey(locale.toString())) {
            return key;
        }
        return this.messages.get(key).get(locale.toString());
    }

}

