import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class OrderBook {

    private final Map<Character, SortedMap<Double, LinkedList<Order>>> book;

    public OrderBook() {
        book = new HashMap<>();
        book.put('B', new TreeMap<>(Collections.reverseOrder()));
        book.put('O', new TreeMap<>());
    }

    public void addOrder(Order order) {
        Map<Double, LinkedList<Order>> sideBook = book.get(order.getSide());
        if (!sideBook.containsKey(order.getPrice())) {
            sideBook.put(order.getPrice(), new LinkedList<>());
        }
        sideBook.get(order.getPrice()).add(order);
    }

    public void removeOrder(long orderId) {
        for (char side : book.keySet()) {
            Map<Double, LinkedList<Order>> sideBook = book.get(side);
            for (double price : sideBook.keySet()) {
                List<Order> orders = sideBook.get(price);
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getId() == orderId) {
                        orders.remove(i);
                        return;
                    }
                }
            }
        }
    }

    public void modifyOrderSize(long orderId, long newSize) {
        for (char side : book.keySet()) {
            Map<Double, LinkedList<Order>> sideBook = book.get(side);
            for (double price : sideBook.keySet()) {
                List<Order> orders = sideBook.get(price);
                for (Order order : orders) {
                    if (order.getId() == orderId) {
                        order.setSize(newSize);
                        return;
                    }
                }
            }
        }
    }

    public Double getPriceForLevel(char side, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level should be greater than 0.");
        }
        Map<Double, LinkedList<Order>> sideBook = book.get(side);
        int currentLevel = 1;
        for (double price : sideBook.keySet()) {
            if (currentLevel == level) {
                return price;
            }
            currentLevel++;
        }
        return null;
    }

    public Long getTotalSizeForLevel(char side, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level should be greater than 0.");
        }
        Map<Double, LinkedList<Order>> sideBook = book.get(side);
        int currentLevel = 1;
        for (double price : sideBook.keySet()) {
            if (currentLevel == level) {
                List<Order> ordersAtLevel = sideBook.get(price);
                return ordersAtLevel.stream().mapToLong(Order::getSize).sum();
            }
            currentLevel++;
        }
        return null;
    }

    public List<Order> getOrdersBySide(char side) {
        return book.get(side).values().stream()
                .flatMap(List::stream)
                .toList();
    }

    Map<Character, SortedMap<Double, LinkedList<Order>>> getBook() {
        return book;
    }
}

