package ua.chistikov.OnlineShopServer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedException extends RuntimeException {
    public static final Logger LOG = LoggerFactory.getLogger(NotAllowedException.class);
    public NotAllowedException(String message){
        super(message);
        LOG.info(message);
    }
}
