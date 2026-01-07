import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    public boolean isProductExists(String name) {
        String sql = "SELECT COUNT(*) FROM products WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addProduct(Product product) {
        if (isProductExists(product.getName())) {
            System.out.println("Error: Product name already exists.");
            return;
        }
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.executeUpdate();
            System.out.println("Success: Product added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(int customerId, Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setInt(3, customerId);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Success: Customer updated.");
            else System.out.println("Error: Customer ID not found.");
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (customer_id, order_date, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setDate(2, order.getOrderDate());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.executeUpdate();
            System.out.println("Success: Order created.");
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }

    public List<Order> listAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, c.name as customer_name, o.order_date, o.total_amount " +
                     "FROM orders o JOIN customers c ON o.customer_id = c.id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Order(
                    rs.getInt("id"),
                    rs.getString("customer_name"),
                    rs.getDate("order_date"),
                    rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, c.name as customer_name, o.order_date, o.total_amount " +
                     "FROM orders o JOIN customers c ON o.customer_id = c.id WHERE c.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Order(
                    rs.getInt("id"),
                    rs.getString("customer_name"),
                    rs.getDate("order_date"),
                    rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
