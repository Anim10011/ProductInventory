import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private Set<Product> products = new HashSet<>();

//testing!!!!!

    void addProduct(String productId, double price, int quantity) {
        if(inStock(productId)) {
            Product product = getProduct(productId);
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }
        } else {
            products.add(new Product(productId, price, quantity));
        }
//        int index = getProductIndex(productId);
//        if (index >= 0) {
//            Product product = products.get(index);
//            product.addStock(quantity);
//            if (product.getPrice() != price) {
//                product.setPrice(price);
//            }
//
//        } else {
//            products.add(new Product(productId, price, quantity));
//        }
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

//        int index = getProductIndex(productId);
//
//        if (index != -1) {
//            if (products.get(index).getQuantity() > quantity) {
//                products.get(index).removeStock(quantity);
//            } else if (products.get(index).getQuantity() == 0) {
//                products.remove(index);
//            } else {
//                throw new InsufficientInventory(
//                        products.get(index).getQuantity(), quantity
//                );
//            }
//        } else {
//            throw new InsufficientInventory(0, quantity);
//        }
    }

    Product getProduct(String productId) {
        for (Product p:
             products) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
//        int index = getProductIndex(productId);
//        if (index >= 0) {
//            return products.get(index);
//        } else {
//            return null;
//        }
    }

    String getAllProductNames() {
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getProductId());
        }

        return String.join(", ", productIds);
    }

    double totalInventoryValue() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    boolean inStock(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;

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
