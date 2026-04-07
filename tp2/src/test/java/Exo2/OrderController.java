package Exo2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testCreateOrder_callsServiceWithCorrectOrder() {
        Order order = new Order(1, "Laptop", 999.99);

        orderController.createOrder(order);

        // Vérifie que OrderService.createOrder est appelé avec le bon argument
        verify(orderService, times(1)).createOrder(order);
    }

    @Test
    void testCreateOrder_callsDaoWithCorrectOrder() {
        Order order = new Order(2, "Phone", 499.99);

        // On crée un vrai OrderService avec le DAO mocké pour tester la chaîne complète
        OrderService realService = new OrderService(orderDao);
        OrderController controller = new OrderController(realService);

        controller.createOrder(order);

        // Vérifie que OrderDao.saveOrder est bien appelé avec la commande
        verify(orderDao, times(1)).saveOrder(order);
    }

    @Test
    void testCreateOrder_neverCalledWithWrongOrder() {
        Order order = new Order(3, "Tablet", 299.99);
        Order wrongOrder = new Order(99, "Wrong", 0.0);

        orderController.createOrder(order);

        // Vérifie que la mauvaise commande n'a jamais été passée
        verify(orderService, never()).createOrder(wrongOrder);
    }
}