package br.fiap.cp5.securitymvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", repo.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping
    public String create(@ModelAttribute Product product) {
        repo.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable Long id, Model model) {
        model.addAttribute("product", repo.findById(id).orElseThrow());
        return "product-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        repo.save(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/products";
    }
}
