package pl.edu.wszib.warehouse.model.view;

public class ChangeQuantityModel {
    private int id;
    private int quantity;

    public ChangeQuantityModel(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public ChangeQuantityModel() {    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
