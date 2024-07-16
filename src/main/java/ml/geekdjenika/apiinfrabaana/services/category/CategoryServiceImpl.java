package ml.geekdjenika.apiinfrabaana.services.category;

import ml.geekdjenika.apiinfrabaana.models.Category;
import ml.geekdjenika.apiinfrabaana.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id).get();
    }
}
