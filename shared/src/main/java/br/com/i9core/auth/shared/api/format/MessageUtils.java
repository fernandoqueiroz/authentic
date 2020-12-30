package br.com.i9core.auth.shared.api.format;

import br.com.i9core.auth.shared.api.context.AppContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    private static MessageSource messageSource;

    public static String getMessage(String key, Object... parameters) {
        try {
            if (messageSource == null) {
                messageSource = AppContext.getApplicationContext().getBean(MessageSource.class);
            }
            return messageSource.getMessage(key, parameters, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return key;
        }
    }
}
