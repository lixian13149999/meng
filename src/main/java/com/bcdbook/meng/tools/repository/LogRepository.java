package com.bcdbook.meng.tools.repository;

import com.bcdbook.meng.tools.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author summer
 * @Date 2017/8/15 下午1:58
 * 日志记录的资源层
 */
public interface LogRepository extends JpaRepository<Log,String> {
}
