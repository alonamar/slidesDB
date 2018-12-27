package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



/*
*   The database query with a DBCol
*   Null values are ok in updates but not in select
* */

public class DBUnit {

    public static void main(String[] args){
        DBUnit myDB = new DBUnit();
        try {
            myDB.connect();
            //myDB.init();
            List<DBCol> myList = new ArrayList<>();
            //myList.add(new DBCol<Integer>(79, "ID"));
            /*myList.add(new DBCol<LocalDate>(LocalDate.parse("2016-10-11"), "dateaccessioned"));
            myList.add(new DBCol<Boolean>(false, "OLD_FORM"));
            myList.add(new DBCol<String>("ed", "site"));
            List<DBCol> pkList = new ArrayList<>();
            //pkList.add(new DBCol<Boolean>(false, "OLD_FORM"));
            //pkList.add(new DBCol<Integer>(79, "id"));
            myDB.update(pkList, myList, "Patient");*/
            /*while(mySet.next()){
                System.out.println(mySet.getString("dateaccessioned"));
            }*/
            /*myDB.delete(myMap,"Patient");
            myDB.insert(myMap,"Patient");
            Map<String,String> myMap2 = new HashMap<>();
            myMap2.put("ID","79");
            myMap2.put("OSU","7");
            myMap2.put("SEX","aa");
            myDB.update(myMap, myMap2, "Patient");*/
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private Connection _con = null;
    private final String DBNAME = "susterDB";

    /**
     * Connect to the database that located at "[HomeDir]/Database". <br>
     * E.g.: "jdbc:h2:C:/Users/Alon/IdeaProjects/SlidesDB/Database/susterDB". <br>
     * Using default user = "root" and password = "123456"
     * @throws ClassNotFoundException in case h2 class does not exist
     * @throws IllegalAccessException initiate new h2 instance
     * @throws InstantiationException initiate new h2 instance
     * @throws SQLException for connecting the DB
     */
    public void connect() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("org.h2.Driver").newInstance();
        final String dir = System.getProperty("user.dir");
        String dbConnection = "jdbc:h2:" + dir + "/Database/" + DBNAME;
        System.out.println(dbConnection);
        this._con = DriverManager.getConnection(dbConnection,"root","123456");
    }

    /**
     * Creating a new DB with the provided files under the Database folder
     * @throws IOException accessing the database files
     * @throws SQLException updating the DB table
     */
    public void init() throws IOException, SQLException {
        List<String> mySQLFiles = new ArrayList<String>();
        final String dir = System.getProperty("user.dir");
        Files.list(Paths.get(dir + "/Database"))
                .filter(p -> !p.toFile().isDirectory() && p.toFile().getAbsolutePath().endsWith("sql"))
                .forEach(p -> mySQLFiles.add(p.toString()));
        for (String file:mySQLFiles) {
            runSQLFile(file);
        }
    }

    /**
     * Receive SQL file and execute each statement in that file.
     * @param file The sql file to execute
     * @throws SQLException for each statement execution
     * @throws IOException reading the file itself
     */
    public void runSQLFile(String file) throws SQLException, IOException {
        Statement mySTMT = _con.createStatement();
        String content = new String(Files.readAllBytes(Paths.get(file)));
        mySTMT.executeUpdate(content);
    }

    // can't look for null values
    public ResultSet select(List<DBCol> whereQ, String table) throws SQLException {
        Statement mySTMT = _con.createStatement();
        String queryString = "SELECT * FROM " + table + " WHERE ";
        int initLength = queryString.length();
        for (DBCol entry : whereQ) {
            queryString += entry.getSelectQuery() + " and  ";
        }
        queryString = queryString.substring(0, queryString.length() - 6); //remove the 'and' or the 'where' in case of no vars
        ResultSet mySet = mySTMT.executeQuery(queryString);
        return mySet;
    }

    // empty list in not possible since old_form column must be given
    public void insert(List<DBCol> insertQ, String table) throws SQLException {
        Statement mySTMT = _con.createStatement();
        String queryString = "INSERT INTO `" + table + "` (";
        String vars = "";
        String values = "";
        for (DBCol entry : insertQ) {
            vars += " `" + entry.getKey() + "`, ";
            values += entry.getInsertVal() + ", ";
        }
        vars = vars.substring(0, vars.length() - 2);
        values = values.substring(0, values.length() - 2);
        queryString += vars + ") VALUES (" + values + ");";
        mySTMT.executeUpdate(queryString);
    }

    public void update(List<DBCol> pk, List<DBCol> updateQ, String table) throws SQLException {
        Statement mySTMT = _con.createStatement();
        String queryString = "UPDATE " + table + " SET ";
        for (DBCol entry : updateQ) {
            queryString += entry.getSetVal() + ", ";
        }
        queryString = queryString.substring(0, queryString.length() - 2);
        queryString += " WHERE ";
        for (DBCol entry : pk) { // check by id and old_form
            queryString += entry.getSelectQuery() + " and  ";
        }
        queryString = queryString.substring(0, queryString.length() - 6);
        System.out.println(queryString);
        mySTMT.executeUpdate(queryString);
    }

    public void delete(List<DBCol> pk, String table) throws SQLException {
        Statement mySTMT = _con.createStatement();
        String queryString = "DELETE FROM " + table + " WHERE ";
        for (DBCol entry : pk) {
            queryString += entry.getSelectQuery() + " and  ";
        }
        queryString = queryString.substring(0, queryString.length() - 6);
        mySTMT.executeUpdate(queryString);
    }
}
