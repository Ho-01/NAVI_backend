package com.doljabi.Outdoor_Escape_Room.common.db;

import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
class SQLiteInit implements InitializingBean {
    private final DataSource ds;
    SQLiteInit(DataSource ds){ this.ds = ds; }
    @Override public void afterPropertiesSet() throws Exception {
        try (var c = ds.getConnection(); var s = c.createStatement()) {
            s.execute("PRAGMA journal_mode=WAL");    // 동시 읽기/쓰기 안정성↑
            s.execute("PRAGMA synchronous=NORMAL");  // 복구/성능 균형(더 안전: FULL)
            s.execute("PRAGMA foreign_keys=ON");     // FK 제약 강제
            s.execute("PRAGMA busy_timeout=5000");   // 잠금 시 대기(ms)
        }
    }
}
