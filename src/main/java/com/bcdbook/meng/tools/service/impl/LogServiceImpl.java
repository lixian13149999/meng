package com.bcdbook.meng.tools.service.impl;

import com.bcdbook.meng.tools.model.Log;
import com.bcdbook.meng.tools.repository.LogRepository;
import com.bcdbook.meng.tools.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author summer
 * @Date 2017/8/15 下午1:59
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;

    /**
     * @author summer
     * @date 2017/8/15 下午2:04
     * @param log
     * @return com.bcdbook.meng.tools.model.Log
     * @description 保存日志的方法
     */
    @Override
    public Log save(Log log) {
        return log==null ? null : logRepository.save(log);
    }
}
