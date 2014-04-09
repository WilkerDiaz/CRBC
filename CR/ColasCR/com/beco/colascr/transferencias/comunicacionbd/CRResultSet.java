/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.comunicacionbd
 * Programa   : ICRResultSet.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 22/02/2005 10:52:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción :
 * =============================================================================
 */
package com.beco.colascr.transferencias.comunicacionbd;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: CRResultSet
 * </pre>
 * <p>
 * <a href="CRResultSet.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: acastillo $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/02/10 14:49:35 $
 * @since 04-feb-2005
 * @
 */
public class CRResultSet implements ResultSet {

	/**
	 * @since 04-feb-2005
	 * 
	 */
	
	ResultSet implementation;
	
	public CRResultSet(ResultSet impl) {
		super();
		implementation = impl;
	}

	public boolean absolute(int row) throws SQLException {
		return implementation.absolute(row);
	}
	public void afterLast() throws SQLException {
		implementation.afterLast();
	}
	public void beforeFirst() throws SQLException {
		implementation.beforeFirst();
	}
	public void cancelRowUpdates() throws SQLException {
		implementation.cancelRowUpdates();
	}
	public void clearWarnings() throws SQLException {
		implementation.clearWarnings();
	}
	public void close() throws SQLException {
		implementation.close();
		//implementation.getStatement().close();
	}
	public void deleteRow() throws SQLException {
		implementation.deleteRow();
	}
	public boolean equals(Object obj) {
		return implementation.equals(obj);
	}
	public int findColumn(String columnName) throws SQLException {
		return implementation.findColumn(columnName);
	}
	public boolean first() throws SQLException {
		return implementation.first();
	}
	public Array getArray(int i) throws SQLException {
		return implementation.getArray(i);
	}
	public Array getArray(String colName) throws SQLException {
		return implementation.getArray(colName);
	}
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return implementation.getAsciiStream(columnIndex);
	}
	public InputStream getAsciiStream(String columnName) throws SQLException {
		return implementation.getAsciiStream(columnName);
	}
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return implementation.getBigDecimal(columnIndex);
	}
	/**
	 * @deprecated
	 */
	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		return implementation.getBigDecimal(columnIndex, scale);
	}
	public BigDecimal getBigDecimal(String columnName) throws SQLException {
		return implementation.getBigDecimal(columnName);
	}
	/**
	 * @deprecated
	 */
	public BigDecimal getBigDecimal(String columnName, int scale)
			throws SQLException {
		return implementation.getBigDecimal(columnName, scale);
	}
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return implementation.getBinaryStream(columnIndex);
	}
	public InputStream getBinaryStream(String columnName) throws SQLException {
		return implementation.getBinaryStream(columnName);
	}
	public Blob getBlob(int i) throws SQLException {
		return implementation.getBlob(i);
	}
	public Blob getBlob(String colName) throws SQLException {
		return implementation.getBlob(colName);
	}
	public boolean getBoolean(int columnIndex) throws SQLException {
		return implementation.getBoolean(columnIndex);
	}
	public boolean getBoolean(String columnName) throws SQLException {
		return implementation.getBoolean(columnName);
	}
	public byte getByte(int columnIndex) throws SQLException {
		return implementation.getByte(columnIndex);
	}
	public byte getByte(String columnName) throws SQLException {
		return implementation.getByte(columnName);
	}
	public byte[] getBytes(int columnIndex) throws SQLException {
		return implementation.getBytes(columnIndex);
	}
	public byte[] getBytes(String columnName) throws SQLException {
		return implementation.getBytes(columnName);
	}
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return implementation.getCharacterStream(columnIndex);
	}
	public Reader getCharacterStream(String columnName) throws SQLException {
		return implementation.getCharacterStream(columnName);
	}
	public Clob getClob(int i) throws SQLException {
		return implementation.getClob(i);
	}
	public Clob getClob(String colName) throws SQLException {
		return implementation.getClob(colName);
	}
	public int getConcurrency() throws SQLException {
		return implementation.getConcurrency();
	}
	public String getCursorName() throws SQLException {
		return implementation.getCursorName();
	}
	public Date getDate(int columnIndex) throws SQLException {
		return implementation.getDate(columnIndex);
	}
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return implementation.getDate(columnIndex, cal);
	}
	public Date getDate(String columnName) throws SQLException {
		return implementation.getDate(columnName);
	}
	public Date getDate(String columnName, Calendar cal) throws SQLException {
		return implementation.getDate(columnName, cal);
	}
	public double getDouble(int columnIndex) throws SQLException {
		return implementation.getDouble(columnIndex);
	}
	public double getDouble(String columnName) throws SQLException {
		return implementation.getDouble(columnName);
	}
	public int getFetchDirection() throws SQLException {
		return implementation.getFetchDirection();
	}
	public int getFetchSize() throws SQLException {
		return implementation.getFetchSize();
	}
	public float getFloat(int columnIndex) throws SQLException {
		return implementation.getFloat(columnIndex);
	}
	public float getFloat(String columnName) throws SQLException {
		return implementation.getFloat(columnName);
	}
	public int getInt(int columnIndex) throws SQLException {
		return implementation.getInt(columnIndex);
	}
	public int getInt(String columnName) throws SQLException {
		return implementation.getInt(columnName);
	}
	public long getLong(int columnIndex) throws SQLException {
		return implementation.getLong(columnIndex);
	}
	public long getLong(String columnName) throws SQLException {
		return implementation.getLong(columnName);
	}
	public ResultSetMetaData getMetaData() throws SQLException {
		return implementation.getMetaData();
	}
	public Object getObject(int columnIndex) throws SQLException {
		return implementation.getObject(columnIndex);
	}
	public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
		return implementation.getObject(i, map);
	}
	public Object getObject(String columnName) throws SQLException {
		return implementation.getObject(columnName);
	}
	public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
		return implementation.getObject(colName, map);
	}
	public Ref getRef(int i) throws SQLException {
		return implementation.getRef(i);
	}
	public Ref getRef(String colName) throws SQLException {
		return implementation.getRef(colName);
	}
	public int getRow() throws SQLException {
		return implementation.getRow();
	}
	public short getShort(int columnIndex) throws SQLException {
		return implementation.getShort(columnIndex);
	}
	public short getShort(String columnName) throws SQLException {
		return implementation.getShort(columnName);
	}
	public Statement getStatement() throws SQLException {
		return implementation.getStatement();
	}
	public String getString(int columnIndex) throws SQLException {
		return implementation.getString(columnIndex);
	}
	public String getString(String columnName) throws SQLException {
		return implementation.getString(columnName);
	}
	public Time getTime(int columnIndex) throws SQLException {
		return implementation.getTime(columnIndex);
	}
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return implementation.getTime(columnIndex, cal);
	}
	public Time getTime(String columnName) throws SQLException {
		return implementation.getTime(columnName);
	}
	public Time getTime(String columnName, Calendar cal) throws SQLException {
		return implementation.getTime(columnName, cal);
	}
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return implementation.getTimestamp(columnIndex);
	}
	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return implementation.getTimestamp(columnIndex, cal);
	}
	public Timestamp getTimestamp(String columnName) throws SQLException {
		return implementation.getTimestamp(columnName);
	}
	public Timestamp getTimestamp(String columnName, Calendar cal)
			throws SQLException {
		return implementation.getTimestamp(columnName, cal);
	}
	public int getType() throws SQLException {
		return implementation.getType();
	}
	/**
	 * @deprecated
	 */
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return implementation.getUnicodeStream(columnIndex);
	}
	/**
	 * @deprecated
	 */
	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return implementation.getUnicodeStream(columnName);
	}
	public URL getURL(int columnIndex) throws SQLException {
		return implementation.getURL(columnIndex);
	}
	public URL getURL(String columnName) throws SQLException {
		return implementation.getURL(columnName);
	}
	public SQLWarning getWarnings() throws SQLException {
		return implementation.getWarnings();
	}
	public int hashCode() {
		return implementation.hashCode();
	}
	public void insertRow() throws SQLException {
		implementation.insertRow();
	}
	public boolean isAfterLast() throws SQLException {
		return implementation.isAfterLast();
	}
	public boolean isBeforeFirst() throws SQLException {
		return implementation.isBeforeFirst();
	}
	public boolean isFirst() throws SQLException {
		return implementation.isFirst();
	}
	public boolean isLast() throws SQLException {
		return implementation.isLast();
	}
	public boolean last() throws SQLException {
		return implementation.last();
	}
	public void moveToCurrentRow() throws SQLException {
		implementation.moveToCurrentRow();
	}
	public void moveToInsertRow() throws SQLException {
		implementation.moveToInsertRow();
	}
	public boolean next() throws SQLException {
		return implementation.next();
	}
	public boolean previous() throws SQLException {
		return implementation.previous();
	}
	public void refreshRow() throws SQLException {
		implementation.refreshRow();
	}
	public boolean relative(int rows) throws SQLException {
		return implementation.relative(rows);
	}
	public boolean rowDeleted() throws SQLException {
		return implementation.rowDeleted();
	}
	public boolean rowInserted() throws SQLException {
		return implementation.rowInserted();
	}
	public boolean rowUpdated() throws SQLException {
		return implementation.rowUpdated();
	}
	public void setFetchDirection(int direction) throws SQLException {
		implementation.setFetchDirection(direction);
	}
	public void setFetchSize(int rows) throws SQLException {
		implementation.setFetchSize(rows);
	}
	public String toString() {
		return implementation.toString();
	}
	public void updateArray(int columnIndex, Array x) throws SQLException {
		implementation.updateArray(columnIndex, x);
	}
	public void updateArray(String columnName, Array x) throws SQLException {
		implementation.updateArray(columnName, x);
	}
	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		implementation.updateAsciiStream(columnIndex, x, length);
	}
	public void updateAsciiStream(String columnName, InputStream x, int length)
			throws SQLException {
		implementation.updateAsciiStream(columnName, x, length);
	}
	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		implementation.updateBigDecimal(columnIndex, x);
	}
	public void updateBigDecimal(String columnName, BigDecimal x)
			throws SQLException {
		implementation.updateBigDecimal(columnName, x);
	}
	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		implementation.updateBinaryStream(columnIndex, x, length);
	}
	public void updateBinaryStream(String columnName, InputStream x, int length)
			throws SQLException {
		implementation.updateBinaryStream(columnName, x, length);
	}
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		implementation.updateBlob(columnIndex, x);
	}
	public void updateBlob(String columnName, Blob x) throws SQLException {
		implementation.updateBlob(columnName, x);
	}
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		implementation.updateBoolean(columnIndex, x);
	}
	public void updateBoolean(String columnName, boolean x) throws SQLException {
		implementation.updateBoolean(columnName, x);
	}
	public void updateByte(int columnIndex, byte x) throws SQLException {
		implementation.updateByte(columnIndex, x);
	}
	public void updateByte(String columnName, byte x) throws SQLException {
		implementation.updateByte(columnName, x);
	}
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		implementation.updateBytes(columnIndex, x);
	}
	public void updateBytes(String columnName, byte[] x) throws SQLException {
		implementation.updateBytes(columnName, x);
	}
	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		implementation.updateCharacterStream(columnIndex, x, length);
	}
	public void updateCharacterStream(String columnName, Reader reader,
			int length) throws SQLException {
		implementation.updateCharacterStream(columnName, reader, length);
	}
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		implementation.updateClob(columnIndex, x);
	}
	public void updateClob(String columnName, Clob x) throws SQLException {
		implementation.updateClob(columnName, x);
	}
	public void updateDate(int columnIndex, Date x) throws SQLException {
		implementation.updateDate(columnIndex, x);
	}
	public void updateDate(String columnName, Date x) throws SQLException {
		implementation.updateDate(columnName, x);
	}
	public void updateDouble(int columnIndex, double x) throws SQLException {
		implementation.updateDouble(columnIndex, x);
	}
	public void updateDouble(String columnName, double x) throws SQLException {
		implementation.updateDouble(columnName, x);
	}
	public void updateFloat(int columnIndex, float x) throws SQLException {
		implementation.updateFloat(columnIndex, x);
	}
	public void updateFloat(String columnName, float x) throws SQLException {
		implementation.updateFloat(columnName, x);
	}
	public void updateInt(int columnIndex, int x) throws SQLException {
		implementation.updateInt(columnIndex, x);
	}
	public void updateInt(String columnName, int x) throws SQLException {
		implementation.updateInt(columnName, x);
	}
	public void updateLong(int columnIndex, long x) throws SQLException {
		implementation.updateLong(columnIndex, x);
	}
	public void updateLong(String columnName, long x) throws SQLException {
		implementation.updateLong(columnName, x);
	}
	public void updateNull(int columnIndex) throws SQLException {
		implementation.updateNull(columnIndex);
	}
	public void updateNull(String columnName) throws SQLException {
		implementation.updateNull(columnName);
	}
	public void updateObject(int columnIndex, Object x) throws SQLException {
		implementation.updateObject(columnIndex, x);
	}
	public void updateObject(int columnIndex, Object x, int scale)
			throws SQLException {
		implementation.updateObject(columnIndex, x, scale);
	}
	public void updateObject(String columnName, Object x) throws SQLException {
		implementation.updateObject(columnName, x);
	}
	public void updateObject(String columnName, Object x, int scale)
			throws SQLException {
		implementation.updateObject(columnName, x, scale);
	}
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		implementation.updateRef(columnIndex, x);
	}
	public void updateRef(String columnName, Ref x) throws SQLException {
		implementation.updateRef(columnName, x);
	}
	public void updateRow() throws SQLException {
		implementation.updateRow();
	}
	public void updateShort(int columnIndex, short x) throws SQLException {
		implementation.updateShort(columnIndex, x);
	}
	public void updateShort(String columnName, short x) throws SQLException {
		implementation.updateShort(columnName, x);
	}
	public void updateString(int columnIndex, String x) throws SQLException {
		implementation.updateString(columnIndex, x);
	}
	public void updateString(String columnName, String x) throws SQLException {
		implementation.updateString(columnName, x);
	}
	public void updateTime(int columnIndex, Time x) throws SQLException {
		implementation.updateTime(columnIndex, x);
	}
	public void updateTime(String columnName, Time x) throws SQLException {
		implementation.updateTime(columnName, x);
	}
	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		implementation.updateTimestamp(columnIndex, x);
	}
	public void updateTimestamp(String columnName, Timestamp x)
			throws SQLException {
		implementation.updateTimestamp(columnName, x);
	}
	public boolean wasNull() throws SQLException {
		return implementation.wasNull();
	}

	//Métodos agregados al hacer la migración a Java 1.6. Modificación realizada por jperez.
	public int getHoldability() throws SQLException {
		return implementation.getHoldability();
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return implementation.getNCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return implementation.getNCharacterStream(columnLabel);
	}
	

	public String getNString(int columnIndex) throws SQLException {
		return implementation.getNString(columnIndex);
	}

	public String getNString(String columnLabel) throws SQLException {
		return implementation.getNString(columnLabel);
	}

	public boolean isClosed() throws SQLException {
		return implementation.isClosed();
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		implementation.updateAsciiStream(columnIndex, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		implementation.updateAsciiStream(columnLabel, x);	
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		implementation.updateAsciiStream(columnIndex, x);	
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		implementation.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		implementation.updateBinaryStream(columnIndex, x);	
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		implementation.updateBinaryStream(columnLabel, x);	
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		implementation.updateBinaryStream(columnIndex, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		implementation.updateBinaryStream(columnLabel, x, length);
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		implementation.updateBlob(columnIndex, inputStream);	
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		implementation.updateBlob(columnLabel, inputStream);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		implementation.updateBlob(columnIndex, inputStream, length);	
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		implementation.updateBlob(columnLabel, inputStream, length);	
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		implementation.updateCharacterStream(columnIndex, x);	
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		implementation.updateCharacterStream(columnLabel, reader);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		implementation.updateCharacterStream(columnIndex, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		implementation.updateCharacterStream(columnLabel, reader, length);	
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		implementation.updateClob(columnIndex, reader);	
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		implementation.updateClob(columnLabel, reader);
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		implementation.updateClob(columnIndex, reader, length);	
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		implementation.updateClob(columnLabel, reader, length);
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		implementation.updateNCharacterStream(columnIndex, x);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		implementation.updateNCharacterStream(columnLabel, reader);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		implementation.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		implementation.updateNCharacterStream(columnLabel, reader, length);
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		implementation.updateNClob(columnIndex, reader);
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		implementation.updateNClob(columnLabel, reader);	
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		implementation.updateNClob(columnIndex, reader, length);
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		implementation.updateNClob(columnLabel, reader, length);	
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		implementation.updateNString(columnIndex, nString);
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		implementation.updateNString(columnLabel, nString);
	}

	



	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		return implementation.getRowId(columnIndex);
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		return implementation.getRowId(columnLabel);
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		implementation.updateRowId(columnIndex, x);
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		implementation.updateRowId(columnLabel, x);
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		implementation.updateNClob(columnIndex, nClob);
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		implementation.updateNClob(columnLabel, nClob);
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		return implementation.getNClob(columnIndex);
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return implementation.getNClob(columnLabel);
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return implementation.getSQLXML(columnIndex);
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return implementation.getSQLXML(columnLabel);
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		implementation.updateSQLXML(columnIndex, xmlObject);
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		implementation.updateSQLXML(columnLabel, xmlObject);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return implementation.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return implementation.isWrapperFor(iface);
	}

}
