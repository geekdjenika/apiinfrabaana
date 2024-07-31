package ml.geekdjenika.apiinfrabaana.services.response;

import ml.geekdjenika.apiinfrabaana.dto.response.ResponseResponse;
import ml.geekdjenika.apiinfrabaana.models.Response;

import java.util.List;

public interface ResponseService {
    ResponseResponse save(Response response);
    ResponseResponse update(Response response);
    List<ResponseResponse> findByQuestionId(long questionId);
    List<ResponseResponse> findAll();
    ResponseResponse mapToResponse(Response response);
    List<ResponseResponse> mapToResponse(List<Response> responses);
}
