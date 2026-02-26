package ipss.web2.examen.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador MVC para vistas web informativas.
 */
@Controller
public class DatabaseStructureViewController {

    /**
     * Ruta dedicada para visualizar la estructura detallada de la base de datos.
     *
     * @return recurso HTML estático con la vista detallada
     */
    @GetMapping("/db/estructura-detallada")
    public String verEstructuraDetallada() {
        return "forward:/db-estructura-detallada.html";
    }
}

