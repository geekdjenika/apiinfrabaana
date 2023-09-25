package ml.geekdjenika.apiinfrabaana.Service.tip;

import ml.geekdjenika.apiinfrabaana.Model.Tip;

import java.util.List;
import java.util.Optional;

public interface TipService {

    Tip addTip(Tip tip);
    Tip getTip(long id);
    List<Tip> getAll();
    Optional<Tip> update(Tip tip, long id);
    void delete(long id);

}
