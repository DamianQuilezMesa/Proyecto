package com.damianqm.proyectoIntermodular.config;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Cargador de ficheros FXML integrado con el contenedor de Spring.
 * <p>
 * Al establecer el contexto de Spring como {@code ControllerFactory} del
 * {@link FXMLLoader}, todos los controladores FXML se obtienen como beans
 * de Spring, lo que permite inyectar dependencias con {@code @Autowired}
 * directamente en los controladores JavaFX.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 *   Parent root = springFXMLLoader.load("/fxml/Login.fxml");
 * </pre>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see StageManager
 */
@Component
public class SpringFXMLLoader {

    private final ResourceBundle resourceBundle;
    private final ApplicationContext context;

    /**
     * Construye el cargador con el contexto de Spring y el bundle de recursos.
     *
     * @param context        el contexto de Spring que actúa como factoría de controladores
     * @param resourceBundle el bundle de internacionalización para los ficheros FXML
     */
    @Autowired
    public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
        this.context = context;
        this.resourceBundle = resourceBundle;
    }

    /**
     * Carga el árbol de nodos de un fichero FXML y devuelve el nodo raíz.
     * <p>
     * El controlador declarado en el FXML se instancia a través del contexto
     * de Spring, por lo que recibirá todas las inyecciones de dependencias
     * antes de que se llame a su método {@code initialize()}.
     * </p>
     *
     * @param fxmlPath ruta del recurso FXML relativa al classpath (p. ej. {@code /fxml/Login.fxml})
     * @return el nodo raíz del árbol de la vista cargada
     * @throws IOException si el fichero FXML no se encuentra o contiene errores
     */
    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}
