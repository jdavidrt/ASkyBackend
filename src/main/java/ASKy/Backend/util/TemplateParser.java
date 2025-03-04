package ASKy.Backend.util;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateParser {

    private final SpringTemplateEngine templateEngine;

    /**
     * Procesa una plantilla Thymeleaf con las variables proporcionadas
     * @param template Ruta de la plantilla
     * @param variables Mapa de variables para reemplazar en la plantilla
     * @return HTML procesado listo para enviar
     */
    public String processTemplate(String template, Map<String, Object> variables) {
        Context context = new Context();

        if (variables != null) {
            variables.forEach(context::setVariable);
        }

        return templateEngine.process(template, context);
    }
}