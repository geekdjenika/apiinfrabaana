package ml.geekdjenika.apiinfrabaana.Service.category;

import ml.geekdjenika.apiinfrabaana.Model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getCategory(long id);
}
