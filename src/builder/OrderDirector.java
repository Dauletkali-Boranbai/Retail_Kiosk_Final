package builder;

import decorator.Product;

public class OrderDirector {

    public Order singleItemOrder(String customer, Product product, int quantity) {
        return new OrderBuilder().customer(customer).addItem(product, quantity).build();
    }

    public Order pairOrder(String customer, Product first, Product second) {
        return new OrderBuilder().customer(customer).addItem(first, 1).addItem(second, 1).build();
    }
}
