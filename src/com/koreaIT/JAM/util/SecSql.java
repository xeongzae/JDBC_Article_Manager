package com.koreaIT.JAM.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SecSql {
    private StringBuilder sqlBuilder;
    private List<Object> params;

    public SecSql() {
        sqlBuilder = new StringBuilder();
        params = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "sql=" + getSql() + ", params=" + params;
    }

    public boolean isInsert() {
        return getSql().startsWith("INSERT");
    }

    // SQL 조각 추가
    public SecSql append(String sqlBit, Object... args) {
        if (sqlBit != null && !sqlBit.isEmpty()) {
            sqlBuilder.append(sqlBit).append(" ");
        }

        for (Object arg : args) {
            params.add(arg);
        }

        return this;
    }

    // PreparedStatement 생성
    public PreparedStatement getPreparedStatement(Connection dbConn) throws SQLException {
        PreparedStatement stmt;

        if (isInsert()) {
            stmt = dbConn.prepareStatement(getSql(), Statement.RETURN_GENERATED_KEYS);
        } else {
            stmt = dbConn.prepareStatement(getSql());
        }

        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            int parameterIndex = i + 1;

            if (param instanceof Integer) {
                stmt.setInt(parameterIndex, (Integer) param);
            } else if (param instanceof String) {
                stmt.setString(parameterIndex, (String) param);
            } else {
                // 추가적인 데이터 타입에 대해 처리 가능
                stmt.setObject(parameterIndex, param);
            }
        }

        return stmt;
    }

    // SQL 문자열 반환
    public String getSql() {
        return sqlBuilder.toString().trim();
    }

    // 파라미터 리스트 반환
    public List<Object> getParams() {
        return params;
    }

    // SQL 쿼리 생성용 static 메서드
    public static SecSql from(String sql) {
        return new SecSql().append(sql);
    }
}
