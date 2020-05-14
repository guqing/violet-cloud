package xyz.guqing.violet.auth.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Slf4j
@Service
public class MyJdbcClientDetailsService extends JdbcClientDetailsService {
    public MyJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }
}
