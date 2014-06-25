package namic.cad.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DbCommon DB common management
 * 
 * @author Mark
 * @version 0.1 2014.6.22
 */
public final class DBCommon
{

	/**
	 * DB Connection Object
	 */
	private Connection dbConnection = null;

	/**
	 * DB PrepareStatement Object
	 */
	private PreparedStatement preStatement = null;

	/**
	 * DB ResultSet Object
	 */
	private ResultSet rsSet = null;

	/**
	 * String CONSTANT
	 */
	// private static final String SQLITEDBFILELOCATION = "jdbc:sqlite:/Users/zhangfanmark/Documents/Namic/NamicDB.db";
	// private static final String SQLITEDRIVERCLASS = "org.sqlite.JDBC";

	private static final String DBURL = "jdbc:mysql://54.91.4.229:3306/namic";
	private static final String DRIVERCLASS = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "dev";
	private static final String PASSWORD = "bmitsydney";

	/**
	 * Self static variable
	 */
	private static DBCommon dbCommon = null;

	/**
	 * Get an instance of DBCommon
	 * 
	 * @return DBCommon object
	 */
	public static DBCommon getInstance()
	{
		// get one instance
		if (dbCommon == null)
		{
			dbCommon = new DBCommon();
		}
		// return the instance
		return dbCommon;
	}

	/**
	 * Get Connection Object
	 * 
	 * @return Connection
	 * @throws Exception
	 *             / ClassNotFoundException
	 */
	public Connection getConnection() throws Exception
	{
		try
		{
			Class.forName(DRIVERCLASS);
			this.dbConnection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
			//System.out.println("aaa");

		}
		catch (ClassNotFoundException ex)
		{
			String errMsg = "Uknown SQLite driven class : " + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new ClassNotFoundException(errMsg, ex);
		}
		catch (Exception ex)
		{
			String errMsg = "DB connection error???" + ex.getMessage();
			//System.err.println(errMsg);
		    ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
		// Return the Connection object
		return this.dbConnection;
	}

	/**
	 * Get PreparedStatement Object
	 * 
	 * @param sql
	 *            statement
	 * @return PreparedStatement
	 * @throws Exception
	 */
	public PreparedStatement getPreparedStatement(String sql) throws Exception
	{
		try
		{
			this.preStatement = this.getConnection().prepareStatement(sql);
		}
		catch (Exception ex)
		{
			String errMsg = "DB PreparedStatement error???" + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
		// Return the PreparedStatement object
		return this.preStatement;
	}

	/**
	 * Execute the SQL statement (Insert|Update|Delete)
	 * 
	 * @param sql
	 * @return numbers of records affected
	 * @throws Exception
	 */
	public int executeUpdate(String sql) throws Exception
	{
		try
		{
			this.closeResultSet();
			this.closePreparedStatement();
			return this.getPreparedStatement(sql).executeUpdate();
		}
		catch (SQLException ex)
		{
			this.closeDBResource();
			String errMsg = "Insert / Update / Delete error???" + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
	}

	/**
	 * Execute the SQL statement (Select)
	 * 
	 * @param sql
	 *            statement
	 * @return Result set
	 * @throws Exception
	 */
	public ResultSet executeQuery(String sql) throws Exception
	{
		try
		{
			this.closeResultSet();
			this.closePreparedStatement();
			this.rsSet = this.getPreparedStatement(sql).executeQuery();
		}
		catch (SQLException ex)
		{
			this.closeDBResource();
			String errMsg = "Select error" + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}

		// Return the ResutlSet object
		return this.rsSet;
	}

	/**
	 * Execute the SQL statement (Select) for the number of records in the
	 * Result Set
	 * 
	 * @param sql
	 *            statement
	 * @return numbers of records contained
	 * @throws Exception
	 */
	public int getResultSetCount(String sql) throws Exception
	{
		// counter of the records
		int count = 0;

		try
		{
			this.closeResultSet();
			this.closePreparedStatement();
			this.rsSet = this.getPreparedStatement(sql).executeQuery();
			
			// iterate the result set and update the counter
			while (this.rsSet.next())
			{
				count++;
			}

		}
		catch (SQLException ex)
		{
			this.closeDBResource();
			String errMsg = "Select error : " + ex.getMessage();
			// System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}

		return count;
	}

	/**
	 * Close the DB connection resources (including the
	 * ResultSet,PreparedStatement, and Connection)
	 * 
	 * @throws Exception
	 */
	public void closeDBResource() throws Exception
	{
		try
		{
			closeResultSet();
			closePreparedStatement();
			closeConnection();
		}
		catch (SQLException ex)
		{
			String errMsg = "Close Resource error???" + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
	}

	/**
	 * Close ResultSet
	 * 
	 * @throws Exception
	 */
	private void closeResultSet() throws Exception
	{
		try
		{
			if (this.rsSet != null)
			{
				this.rsSet.close();
				this.rsSet = null;
			}
		}
		catch (SQLException ex)
		{
			String errMsg = "Close ResultSet error: " + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
	}

	/**
	 * Close PreparedStatement
	 * 
	 * @throws Exception
	 */
	private void closePreparedStatement() throws Exception
	{
		try
		{
			if (this.preStatement != null)
			{
				this.preStatement.close();
				this.preStatement = null;
			}
		}
		catch (SQLException ex)
		{
			String errMsg = "Close PreparedStatement error: " + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
	}

	/**
	 * Close Connection
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	private void closeConnection() throws SQLException, Exception
	{
		try
		{
			if (this.dbConnection != null && (!this.dbConnection.isClosed()))
			{
				this.dbConnection.close();
			}
		}
		catch (SQLException ex)
		{
			String errMsg = "Close Connection error: " + ex.getMessage();
			//System.err.println(errMsg);
			ex.printStackTrace();
			throw new Exception(errMsg, ex);
		}
	}

	/**
	 * Start the transaction
	 * 
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException
	{
		if (this.dbConnection != null && (!this.dbConnection.isClosed())
				&& this.dbConnection.getAutoCommit())
		{
			// Disable the auto commit
			this.dbConnection.setAutoCommit(false);
		}
	}

	/**
	 * Commit the transaction
	 * 
	 * @throws SQLException
	 */
	public void commitTransaction() throws SQLException
	{
		if (this.dbConnection != null && (!this.dbConnection.isClosed())
				&& !this.dbConnection.getAutoCommit())
		{
			// commit manually
			this.dbConnection.commit();
			// enable the auto commit
			this.dbConnection.setAutoCommit(true);
		}
	}

	/**
	 * Roll back the transaction
	 * 
	 * @throws SQLException
	 */
	public void rollbackTransaction() throws SQLException
	{
		if (this.dbConnection != null && (!this.dbConnection.isClosed())
				&& !this.dbConnection.getAutoCommit())
		{
			// roll back the transaction
			this.dbConnection.rollback();
			// enable the auto commit
			this.dbConnection.setAutoCommit(true);
		}
	}
}
