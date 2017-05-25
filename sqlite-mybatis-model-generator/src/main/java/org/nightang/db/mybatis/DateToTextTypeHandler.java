package org.nightang.db.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class DateToTextTypeHandler extends BaseTypeHandler<Date> {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Date parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, sdf.format(parameter));
	}

	@Override
	public Date getNullableResult(ResultSet arg0, String arg1) throws SQLException {
		return null;
	}

	@Override
	public Date getNullableResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public Date getNullableResult(CallableStatement arg0, int arg1)	throws SQLException {
		return null;
	}

}
