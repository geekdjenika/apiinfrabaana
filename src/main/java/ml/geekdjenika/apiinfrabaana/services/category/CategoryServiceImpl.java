package ml.geekdjenika.apiinfrabaana.services.category;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Category;
import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.models.Vocal;
import ml.geekdjenika.apiinfrabaana.repositories.CategoryRepository;
import ml.geekdjenika.apiinfrabaana.services.infringement.InfringementService;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final InfringementService infringementService;
    private final VocalService vocalService;

    @Override
    public CategoryResponse save(Category category) {
        Category existingCategory = repository.findByName(category.getName());
        if (existingCategory != null)
            throw new NotFoundException("Cette catégorie existe déjà !");
        return mapToResponse(repository.save(category));
    }

    @Override
    public CategoryResponse update(Category category) {
        Category categoryToUpdate = repository.findById(category.getId()).orElse(null);
        if (categoryToUpdate == null) throw new NotFoundException("Catégorie introuvable !");
        Category existingCategory = repository.findByName(category.getName());
        if (existingCategory != null)
            throw new NotFoundException("Cette catégorie existe déjà !");
        categoryToUpdate.setName(category.getName());
        return mapToResponse(repository.save(categoryToUpdate));
    }

    @Override
    public List<CategoryResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public CategoryResponse findById(long id) {
        Category category = repository.findById(id).orElse(null);
        if (category == null) throw new NotFoundException("Cette catégorie n'existe pas !");
        return mapToResponse(category);
    }

    @Override
    public void delete(long id) {
        Category category = repository.findById(id).orElse(null);
        if (category == null) throw new NotFoundException("Cette catégorie n'existe pas !");
        repository.delete(category);
    }

    @Override
    public List<CategoryResponse> mapToResponse(List<Category> categories) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categories.forEach(category -> categoryResponses.add(mapToResponse(category)));
        return categoryResponses;
    }

    @Override
    public CategoryResponse mapToResponse(Category category) {
        List<Fine> fines = category.getFines();
        List<FineResponse> fineResponses = new ArrayList<>();
        fines.forEach(fine -> {
            List<Infringement> infringements = fine.getInfringements();
            List<Vocal> vocals = fine.getVocals();
            fineResponses.add(FineResponse.builder()
                    .id(fine.getId())
                    .amount(AmountResponse.builder()
                            .id(fine.getAmount().getId())
                            .value(fine.getAmount().getValue())
                            .currency(fine.getAmount().getCurrency())
                            .build())
                    .category(CategoryResponse.builder()
                            .id(fine.getCategory().getId())
                            .name(fine.getCategory().getName())
                            .build())
                    .infringements(infringementService.mapToResponse(infringements))
                    .vocals(vocalService.mapToResponse(vocals))
                    .build());
        });
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .fines(fineResponses)
                .infringements(infringementService.mapToResponse(category.getInfringements()))
                .build();
    }
}
