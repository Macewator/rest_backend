package pl.javastart.equipy.service;

import org.springframework.stereotype.Service;
import pl.javastart.equipy.model.Category;
import pl.javastart.equipy.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> findAllNames(){
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
