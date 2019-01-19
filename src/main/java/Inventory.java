import java.util.*;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private Map<String, Product> products = new HashMap<>();

    void addProduct(String productId, double price, int quantity) {
        if(inStock(productId)) {
            Product product = getProduct(productId);
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }
        } else {
            products.put(productId,new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        // you write this class
        Product product = getProduct(productId);
        if (inStock(productId)) {
            if (product.getQuantity() > quantity) {
                product.removeStock(quantity);
            } else if (product.getQuantity() == 0){
                products.remove(product);
            } else {
                throw new InsufficientInventory(
                        product.getQuantity(), quantity
                );
            }
        } else {
            throw new InsufficientInventory(0, quantity);
        }
    }

    Product getProduct(String productId) {
        return products.get(productId);
    }

    String getAllProductNames() {
        return String.join(", ", products.keySet());
    }

    double totalInventoryValue() {
        double total = 0;
        for (Product product : products.values()) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    boolean inStock(String productId) {
        return products.containsKey(productId);

    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.totalInventoryValue());
        System.out.println(inventory.inStock("apple"));
        System.out.println(inventory.getAllProductNames());

    }
}
