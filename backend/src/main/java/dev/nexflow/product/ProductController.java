package dev.nexflow.product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/products")
public class ProductController {
    private final ProductService service; public ProductController(ProductService service){this.service=service;}
    @GetMapping public List<ProductDtos.View> list(){return service.list();}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ProductDtos.View create(@Valid @RequestBody ProductDtos.Request r, Authentication a){return service.create(r,a.getName());}
    @PutMapping("/{id}") public ProductDtos.View update(@PathVariable UUID id,@Valid @RequestBody ProductDtos.Request r,Authentication a){return service.update(id,r,a.getName());}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable UUID id,Authentication a){service.deactivate(id,a.getName());}
}
