import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderBookTest {

    @Test
    public void testAddOrder() {
        Order order = new Order(1L, 100.0, 'B', 10L);
        OrderBook orderBook = new OrderBook();

        orderBook.addOrder(order);

        assertEquals(order, orderBook.getBook().get('B').get(100.0).get(0));
    }

    @Test
    public void testAddMultipleOrders() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertEquals(order1, orderBook.getBook().get('B').get(100.0).get(0));
        assertEquals(order2, orderBook.getBook().get('B').get(99.0).get(0));
    }

    @Test
    public void testRemoveOrder() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 100.0, 'B', 20L);
        Order order3 = new Order(3L, 99.0, 'B', 30L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);

        orderBook.removeOrder(1);

        assertEquals(1, orderBook.getBook().get('B').get(100.0).size());
        assertEquals(order2, orderBook.getBook().get('B').get(100.0).get(0));
    }

    @Test
    public void testRemoveNonExistentOrder() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        orderBook.removeOrder(3);

        assertEquals(order1, orderBook.getBook().get('B').get(100.0).get(0));
        assertEquals(order2, orderBook.getBook().get('B').get(99.0).get(0));
    }

    @Test
    public void testModifyOrderSize() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        orderBook.modifyOrderSize(1, 5);

        assertEquals(5, orderBook.getBook().get('B').get(100.0).get(0).getSize());
    }

    @Test
    public void testModifyNonExistentOrderSize() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        orderBook.modifyOrderSize(3, 5);

        assertEquals(10, orderBook.getBook().get('B').get(100.0).get(0).getSize());
    }

    @Test
    public void testGetPriceForLevel() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertEquals(99.0, orderBook.getPriceForLevel('B', 2));
    }

    @Test
    public void testGetPriceForInvalidLevel() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertThrows(IllegalArgumentException.class, () -> orderBook.getPriceForLevel('B', 0));
    }

    @Test
    public void testGetPriceForNonExistentLevel() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertNull(orderBook.getPriceForLevel('B', 3));
    }

    @Test
    public void testGetPriceForSideWithNoOrders() {
        OrderBook orderBook = new OrderBook();
        assertNull(orderBook.getPriceForLevel('B', 1));
    }

    @Test
    public void testGetTotalSizeForLevel() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        Order order3 = new Order(3L, 98.0, 'B', 30L);
        Order order4 = new Order(4L, 99.0, 'B', 5L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);
        orderBook.addOrder(order4);

        Long result = orderBook.getTotalSizeForLevel('B', 2);

        assertEquals(25L, result);
    }

    @Test
    public void testGetTotalSizeForInvalidLevel() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertThrows(IllegalArgumentException.class, () -> orderBook.getTotalSizeForLevel('B', 0));
    }

    @Test
    public void testGetSizeForNonExistentLevel() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 99.0, 'B', 20L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertNull(orderBook.getTotalSizeForLevel('B', 3));
    }

    @Test
    public void testGetSizeForSideWithNoOrders() {
        OrderBook orderBook = new OrderBook();
        assertNull(orderBook.getTotalSizeForLevel('B', 1));
    }

    @Test
    public void testGetOrdersBySide() {
        Order order1 = new Order(1L, 100.0, 'B', 10L);
        Order order2 = new Order(2L, 98.0, 'O', 20L);
        Order order3 = new Order(3L, 99.0, 'B', 30L);
        Order order4 = new Order(4L, 101.0, 'B', 40L);
        Order order5 = new Order(5L, 100.0, 'B', 50L);
        Order order6 = new Order(6L, 98.0, 'O', 60L);
        Order order7 = new Order(7L, 97.0, 'O', 70L);
        OrderBook orderBook = new OrderBook();
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);
        orderBook.addOrder(order4);
        orderBook.addOrder(order5);
        orderBook.addOrder(order6);
        orderBook.addOrder(order7);

        List<Order> resultB = orderBook.getOrdersBySide('B');
        List<Order> resultO = orderBook.getOrdersBySide('O');

        assertEquals(4, resultB.size());
        assertEquals(4, resultB.get(0).getId());
        assertEquals(1, resultB.get(1).getId());
        assertEquals(5, resultB.get(2).getId());
        assertEquals(3, resultB.get(3).getId());

        assertEquals(3, resultO.size());
        assertEquals(7, resultO.get(0).getId());
        assertEquals(2, resultO.get(1).getId());
        assertEquals(6, resultO.get(2).getId());
    }

}