package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Categorie;

import java.util.List;

public interface CategorieService {
    List<Categorie> getAll();
    Categorie getCategorie(long id);
}
