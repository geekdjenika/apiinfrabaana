package ml.geekdjenika.apiinfrabaana.services.category;

import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.models.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(Category category);
    CategoryResponse update(Category category);
    List<CategoryResponse> findAll();
    CategoryResponse findById(long id);
    void delete(long id);
    List<CategoryResponse> mapToResponse(List<Category> categories);
    CategoryResponse mapToResponse(Category category);
}
