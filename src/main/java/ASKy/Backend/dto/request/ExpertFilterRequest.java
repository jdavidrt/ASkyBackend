package ASKy.Backend.dto.request;

import lombok.Data;

@Data
public class ExpertFilterRequest {
    private String name;
    private String topic;
    private String subtopic;
    private String orderBy;
}