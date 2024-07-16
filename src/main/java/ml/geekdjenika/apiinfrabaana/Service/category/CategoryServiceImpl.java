package ml.geekdjenika.apiinfrabaana.Service.category;

import ml.geekdjenika.apiinfrabaana.Model.Category;
import ml.geekdjenika.apiinfrabaana.Repository.CategoryRepository;
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
