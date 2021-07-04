package pl.estrix.zwrotpaczek.dto;

public class CollectorDto extends BaseEntityDto<Long> {

    private String number;
    private String sessionId;

    public CollectorDto() {
    }

    public CollectorDto(String number, String sessionId) {
        this.number = number;
        this.sessionId = sessionId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
