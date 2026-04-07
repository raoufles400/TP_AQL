package Exo3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductApiClient productApiClient;

    @InjectMocks
    private ProductService productService;

    // Récupération réussie
    @Test
    void testGetProduct_success() {
        Product mockProduct = new Product("P001", "Laptop", 1200.0);
        when(productApiClient.getProduct("P001")).thenReturn(mockProduct);

        Product result = productService.getProduct("P001");

        assertNotNull(result);
        assertEquals("P001", result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(1200.0, result.getPrice());

        // Vérifie que l'API est appelée avec le bon argument
        verify(productApiClient, times(1)).getProduct("P001");
    }

    //  Format de données incompatible (retourne null)
    @Test
    void testGetProduct_incompatibleDataFormat() {
        // L'API retourne null → format incompatible
        when(productApiClient.getProduct("P002")).thenReturn(null);

        Product result = productService.getProduct("P002");

        assertNull(result);
        verify(productApiClient, times(1)).getProduct("P002");
    }

    //  Échec de l'appel API (exception)
    @Test
    void testGetProduct_apiCallFails() {
        when(productApiClient.getProduct("P003"))
                .thenThrow(new ApiException("Serveur API indisponible"));

        ApiException exception = assertThrows(ApiException.class, () -> {
            productService.getProduct("P003");
        });

        assertEquals("Serveur API indisponible", exception.getMessage());
        verify(productApiClient, times(1)).getProduct("P003");
    }

    //  ID produit invalide (validation côté service)
    @Test
    void testGetProduct_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProduct("");
        });

        // L'API ne doit jamais être appelée si l'ID est invalide
        verify(productApiClient, never()).getProduct(any());
    }
}