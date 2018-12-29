package dbApp.database;

import java.time.LocalDate;

public class DBCol<E> {
    private E value;
    private String key;
    private E smallerValue = null;

    public DBCol(E value, String key) {
        this.value = value;
        this.key = key;
    }

    /**
     * In case there is a range of values
     */
    public DBCol(E biggerValue, String key, E smallerValue) {
        this.value = biggerValue;
        this.key = key;
        this.smallerValue = smallerValue;
    }

    public E getSmallerValue() {
        return smallerValue;
    }

    public void setSmallerValue(E smallerValue) {
        this.smallerValue = smallerValue;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public E getValue() {
        return this.value;
    }

    public String getKey() {
        return this.key;
    }

    /**
     * Format it in a format of: `key` = `value` or st
     * @return formatted string
     */
    public String getSelectQuery() {
        String returnString;
        Class c = this.value.getClass();
        if(c == String.class){
            returnString = "upper(`" + getKey() + "`)";
            returnString += " LIKE upper('%" + getSQLValue(this.value) + "%')";
        }else if((c == LocalDate.class || c == Integer.class || c == Double.class) && this.smallerValue != null){
            returnString = "`" + getKey() + "`";
            returnString += " between '" + getSQLValue(this.smallerValue) + "' and '" + getSQLValue(this.value) + "'";
        } else{
            returnString = "`" + getKey() + "`";
            returnString += " = '" + getSQLValue(this.value) + "'";
        }
        return returnString;
    }

    public String getInsertVal() {
        return "'" + getSQLValue(this.value) + "'";
    }

    /**
     * Format it in a format of: `key` = 'value'
     * @return formatted string
     */
    public String getSetVal() {
        return "`" + getKey() + "` = '" + getSQLValue(this.value) + "'";
    }

    private String getSQLValue(E value) {
        if(value == null) {
            return "NULL";
        }else{
            return cleanString(String.valueOf(value));
        }
    }

    private String cleanString(String myString){
        return myString.replace("'","''");
    }

}
