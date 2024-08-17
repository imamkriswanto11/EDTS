package com.edts.test.service;

import com.edts.test.base.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {
    @Autowired
    protected HttpService httpService;
}
