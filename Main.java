import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static OrderManager manager = new OrderManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== SHOP MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Product");
            System.out.println("2. Update Customer Info");
            System.out.println("3. Create New Order");
            System.out.println("4. List All Orders");
            System.out.println("5. Search Orders by Customer");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    updateCustomerInfo();
                    break;
                case 3:
                    createNewOrder();
                    break;
                case 4:
                    showAllOrders();
                    break;
                case 5:
                    searchOrders();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addNewProduct() {
        try {
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();
            if(name.isEmpty()) throw new Exception("Name cannot be empty");

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            manager.addProduct(new Product(name, price));
        } catch (Exception e) {
            System.out.println("Input Error: " + e.getMessage());
        }
    }

    private static void updateCustomerInfo() {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter New Email: ");
            String email = scanner.nextLine();

            manager.updateCustomer(id, new Customer(name, email));
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        }
    }

    private static void createNewOrder() {
        try {
            System.out.print("Enter Customer ID: ");
            int custId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Product ID to buy: ");
            int prodId = Integer.parseInt(scanner.nextLine());

            Product product = manager.getProductById(prodId);
            if (product == null) {
                System.out.println("Product not found!");
                return;
            }

            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) throw new Exception("Quantity must be > 0");

            double total = product.getPrice() * quantity;
            Date now = new Date(System.currentTimeMillis());

            Order order = new Order(custId, now, total);
            manager.createOrder(order);
            
            System.out.printf("Total Amount calculated: %,.0f\n", total);

        } catch (Exception e) {
            System.out.println("Order Error: " + e.getMessage());
        }
    }

    private static void showAllOrders() {
        List<Order> orders = manager.listAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order o : orders) {
                System.out.println(o);
            }
        }
    }

    private static void searchOrders() {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            List<Order> orders = manager.getOrdersByCustomer(id);
            
            if (orders.isEmpty()) {
                System.out.println("No orders found for this customer.");
            } else {
                for (Order o : orders) {
                    System.out.println(o);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }
}
