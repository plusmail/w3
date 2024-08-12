package kroryi.w3.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import lombok.extern.log4j.Log4j2;

@WebListener
@Log4j2
public class LoginListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent event) {

        String name = event.getName();
        Object value = event.getValue();
        if(name.equals("loginInfo")){
            log.info("사용자 로그인 되어 있나......");
            log.info(value);
        }

    }



}
