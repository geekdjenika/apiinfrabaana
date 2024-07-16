package ml.geekdjenika.apiinfrabaana.dto.payloads.response;

import lombok.ToString;

@ToString
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
