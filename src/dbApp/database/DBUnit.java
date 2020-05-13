package dbApp.database;

import org.h2.tools.Script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



/**
*   The dbApp.database query using DBCol<br>
*   Null values are ok in updates but not in select
* */

public class DBUnit {

    /**
     * testing the methods
     * @param args no need
     */
    public static void main(String[] args){
        DBUnit myDB = new DBUnit("patient",true);
        try {
            myDB.connect();
            myDB.init();
            List<DBCol> myList = new ArrayList<>();
            //myList.add(new DBCol<Integer>(79, "ID"));
            myList.add(new DBCol<LocalDate>(LocalDate.parse("2016-10-11"), "dateaccessioned"));
            myList.add(new DBCol<Boolean>(false, "OLD_FORM"));
            myList.add(new DBCol<String>("ed", "site"));
            List<DBCol> pkList = new ArrayList<>();
            pkList.add(new DBCol<Boolean>(false, "OLD_FORM"));
            pkList.add(new DBCol<Integer>(79, "id"));
            myDB.update(pkList, myList);

            myDB.delete(pkList);
            myDB.insert(myList);
            ResultSet mySet = myDB.select(myList);
            while(mySet.next()){
                System.out.println(mySet.getString("dateaccessioned"));
            }
            System.out.println(mySet);

            myList = new ArrayList<>();
            myList.add(new DBCol<String>("sa", "site"));
            myList.add(new DBCol<Double>(55.0, "age", 50.0));
            myList.add(new DBCol<Integer>(10, "blocks", 0));
            mySet = myDB.select(myList);
            while(mySet.next()){
                System.out.println(mySet.getString("site"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean verbose = false;
    private Connection con = null;
    private final static String DBNAME = "susterDB";
    private String tableName;

    public DBUnit(){
        super();
    }

    public DBUnit(String tableName){
        super();
        this.tableName = tableName;
    }

    public DBUnit(String tableName, boolean verbose){
        this.tableName = tableName;
        this.verbose = verbose;
    }

    /**
     * Connect to the dbApp.database that located at "[HomeDir]/dbApp.database". <br>
     * In case the dbApp.database does not exist - it will be created. <br>
     * E.g.: "jdbc:h2:C:/Users/Alon/IdeaProjects/SlidesDB/dbApp.database/susterDB". <br>
     * Using default user = "root" and password = "123456"
     * @throws SQLException for connecting the DB
     */
    public void connect() throws SQLException {
        try {
            Class.forName("org.h2.Driver").newInstance(); // create h2 driver
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final String dir = System.getProperty("user.dir");
        String dbConnection = "jdbc:h2:" + dir + "/database/" + DBNAME + ";CIPHER=AES";
        System.out.println(dbConnection);
        this.con = DriverManager.getConnection(dbConnection,"root","123 123456");
    }

    /**
     * Creating a new DB with the provided files under the dbApp.database folder <br>
     * Naming convention: [name]_table, [name]_table_inserts
     * @throws IOException accessing the dbApp.database files
     * @throws SQLException In case database already exist
     */
    public void init() throws IOException, SQLException {
        List<String> mySQLFiles = new ArrayList<String>();
        final String dir = System.getProperty("user.dir");
        Files.list(Paths.get(dir + "/database"))
                .filter(p -> !p.toFile().isDirectory() && p.toFile().getAbsolutePath().endsWith("sql"))
                .forEach(p -> mySQLFiles.add(p.toString()));
        for (String file:mySQLFiles) {
            runSQLFile(file);
        }
    }

    /**
     * Receive SQL file and execute each statement in that file.
     * @param file The sql file to execute
     * @throws IOException reading the file itself
     * @throws SQLException In case database already exist
     */
    public void runSQLFile(String file) throws IOException, SQLException {
        Statement mySTMT = null;
        mySTMT = this.con.createStatement();
        String content = new String(Files.readAllBytes(Paths.get(file)));
        mySTMT.executeUpdate(content);
    }

    // can't look for null values
    /**
     *
     * @param whereQ List of DBCol. The WHERE condition, if empty, pick all of them
     * @return A table of data representing a dbApp.database result set
     * @throws SQLException in case the select failed for some reason
     */
    public ResultSet select(List<DBCol> whereQ) throws SQLException {
        Statement mySTMT = this.con.createStatement();
        String queryString = "SELECT * FROM " + tableName;
        queryString += concatDBList(whereQ, "where");
        if(this.verbose)
            System.out.println(queryString);
        ResultSet mySet = mySTMT.executeQuery(queryString);
        return mySet;
    }

    // empty list in not possible since old_form column must be given

    /**
     *
     * @param insertQ List of DBCol, pairs of key and value
     * @throws SQLException in case the insert failed for some reason
     */
    public void insert(List<DBCol> insertQ) throws SQLException {
        Statement mySTMT = this.con.createStatement();
        String queryString = "INSERT INTO " + tableName;
        queryString += concatDBList(insertQ, "insert");
        if(this.verbose)
            System.out.println(queryString);
        mySTMT.executeUpdate(queryString);
    }

    /**
     * Update entry
     * @param pk List of DBCol. The WHERE condition, if empty, pick all of them
     * @param updateQ List of DBCol. The SET arguments - can't be empty (throw sqlexception)
     * @throws SQLException in case the update failed for some reason
     */
    public void update(List<DBCol> pk, List<DBCol> updateQ) throws SQLException {
        String queryString = "UPDATE " + tableName;
        queryString += concatDBList(updateQ, "set");
        queryString += concatDBList(pk, "where");
        if(this.verbose)
            System.out.println(queryString);
        Statement mySTMT = this.con.createStatement();
        mySTMT.executeUpdate(queryString);
    }

    /**
     * Delete entry
     * @param pk List of DBCol. The WHERE condition, if empty, pick all of them
     * @throws SQLException in case the delete failed for some reason
     */
    public void delete(List<DBCol> pk) throws SQLException {
        Statement mySTMT = this.con.createStatement();
        String queryString = "DELETE FROM " + tableName;
        queryString += concatDBList(pk, "where");
        if(this.verbose)
            System.out.println(queryString);
        mySTMT.executeUpdate(queryString);
    }

    /**
     * Concat the set of column name and value by the condition in an SQL statement
     * @param dbList the list of DBCol to concat
     * @param cond the type of condition: ["set", "where", "insert"]
     * @return The concatenate string
     */
    private String concatDBList(List<DBCol> dbList, String cond){
        String returnString = "";
        if(dbList.size() == 0){
            return returnString;
        }
        switch (cond){
            case "set":
                returnString += " SET ";
                for (DBCol entry : dbList) {
                    returnString += entry.getSetVal() + ", ";
                }
                returnString = returnString.substring(0, returnString.length() - 2);
                break;
            case "where":
                returnString += " WHERE ";
                for (DBCol entry : dbList) {
                    returnString += entry.getSelectQuery() + " and ";
                }
                returnString = returnString.substring(0, returnString.length() - 5);
                break;
            case "insert":
                returnString +=  " (";
                String vars = "";
                String values = "";
                for (DBCol entry : dbList) {
                    vars += "`" + entry.getKey() + "`, ";
                    values += entry.getInsertVal() + ", ";
                }
                vars = vars.substring(0, vars.length() - 2);
                values = values.substring(0, values.length() - 2);
                returnString += vars + ") VALUES (" + values + ");";
                break;
        }
        return returnString;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getTablename(){
        return tableName;
    }

    public String setTablename(String tableName){
        return this.tableName = tableName;
    }

    public void backUpDB() throws SQLException{
        Script.process(this.con, "database/tables.sql", "", "");
    }
}
