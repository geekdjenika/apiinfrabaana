package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Langue;

import java.util.List;

public interface LangueService {
    Langue addLangue(Langue langue);
    Langue findLangueByLabel(String label);
    List<Langue> findAllLangue();
    Langue findOneLangue(long id);
}
