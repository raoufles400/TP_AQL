package Exo3;

public class ProductService {
    private final ProductApiClient apiClient;

    public ProductService(ProductApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Product getProduct(String productId) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("L'ID produit ne peut pas être vide");
        }
        return apiClient.getProduct(productId);
    }
}