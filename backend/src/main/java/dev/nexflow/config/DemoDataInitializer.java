package dev.nexflow.config;

import dev.nexflow.auth.Role;
import dev.nexflow.auth.UserEntity;
import dev.nexflow.auth.UserRepository;
import dev.nexflow.order.OrderDtos;
import dev.nexflow.order.OrderService;
import dev.nexflow.product.Product;
import dev.nexflow.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DemoDataInitializer {

    @Bean
    CommandLineRunner demoData(
            UserRepository users,
            PasswordEncoder encoder,
            ProductRepository products,
            OrderService orders
    ) {
        return args -> {
            if (users.findByEmailIgnoreCase("demo@nexflow.dev").isEmpty()) {
                UserEntity user = new UserEntity();
                user.setName("Pamela Demo");
                user.setEmail("demo@nexflow.dev");
                user.setPassword(encoder.encode("Demo@123"));
                user.setRole(Role.ADMIN);
                users.save(user);
            }

            if (products.count() == 0) {
                products.saveAll(List.of(
                        product("Jogo de Cama Premium Queen", "BED-QN-001", "Cama", 289.90, 42, 8),
                        product("Edredom Dupla Face King", "EDR-KG-002", "Edredons", 399.90, 18, 5),
                        product("Kit Toalhas Algodão 5 peças", "TWL-005", "Banho", 179.90, 65, 12),
                        product("Travesseiro Hotel 50x70", "TRV-5070", "Travesseiros", 89.90, 30, 8),
                        product("Protetor de Colchão Queen", "PRT-QN-004", "Cama", 129.90, 11, 5),
                        product("Manta Decorativa Bouclé", "MNT-BC-006", "Decoração", 159.90, 7, 6)
                ));
            }

            if (orders.list().isEmpty()) {
                List<Product> productList = products.findAllByOrderByNameAsc();

                orders.create(
                        new OrderDtos.CreateRequest(
                                "Mariana Costa",
                                "mariana@example.com",
                                List.of(
                                        new OrderDtos.ItemRequest(productList.get(0).getId(), 1),
                                        new OrderDtos.ItemRequest(productList.get(2).getId(), 2)
                                )
                        ),
                        "system@nexflow.dev"
                );

                orders.create(
                        new OrderDtos.CreateRequest(
                                "Lucas Almeida",
                                "lucas@example.com",
                                List.of(new OrderDtos.ItemRequest(productList.get(1).getId(), 1))
                        ),
                        "system@nexflow.dev"
                );

                orders.create(
                        new OrderDtos.CreateRequest(
                                "Ana Souza",
                                "ana@example.com",
                                List.of(
                                        new OrderDtos.ItemRequest(productList.get(3).getId(), 2),
                                        new OrderDtos.ItemRequest(productList.get(4).getId(), 1)
                                )
                        ),
                        "system@nexflow.dev"
                );
            }
        };
    }

    private Product product(
            String name,
            String sku,
            String category,
            double price,
            int stock,
            int minStock
    ) {
        Product product = new Product();
        product.setName(name);
        product.setSku(sku);
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStockQuantity(stock);
        product.setReservedQuantity(0);
        product.setMinStock(minStock);
        product.setActive(true);
        return product;
    }
}
