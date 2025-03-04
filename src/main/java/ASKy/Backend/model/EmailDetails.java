package ASKy.Backend.model;


import ASKy.Backend.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
    private String to;
    private String subject;
    private EmailType emailType;
    private Map<String, Object> templateVariables;
    private String from;
    private String cc;
    private String bcc;
}