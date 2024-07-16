package ml.geekdjenika.apiinfrabaana.services.category;

import ml.geekdjenika.apiinfrabaana.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getCategory(long id);
}
