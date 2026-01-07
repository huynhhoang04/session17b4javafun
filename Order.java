import java.sql.Date;

public class Order {
    private int id;
    private int customerId;
    private Date orderDate;
    private double totalAmount;
    private String customerName; 

    public Order() {}

    public Order(int customerId, Date orderDate, double totalAmount) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public Order(int id, String customerName, Date orderDate, double totalAmount) {
        this.id = id;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    @Override
    public String toString() {
        return String.format("Order ID: %d | Customer: %s | Date: %s | Total: %,.0f", 
            id, (customerName != null ? customerName : customerId), orderDate, totalAmount);
    }
}
