package com.github.xzb617.cappuccino.server.aoplog;

import org.springframework.util.StringUtils;

import java.util.Date;

public interface LoggerService {

    public void saveAopLog(String reqURI, String opMethod, String opArgs, String opDesc, String opUser, Date opTime);

}
