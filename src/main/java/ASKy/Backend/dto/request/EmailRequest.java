package ASKy.Backend.dto.request;

import ASKy.Backend.enums.EmailType;
import lombok.Data;

import java.util.Map;


@Data
public class EmailRequest {
    private String to;
    private EmailType emailType;
    private Map<String, Object> templateVariables;
    private String cc;
    private String bcc;
}