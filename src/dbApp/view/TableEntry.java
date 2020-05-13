package dbApp.view;

import javafx.beans.property.IntegerProperty;

public class TableEntry {
    protected IntegerProperty id;

    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
}
