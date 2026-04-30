package com.damianqm.proyectoIntermodular.config;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javafx.stage.Stage;

/**
 * Configuración central de Spring para la aplicación JavaFX.
 * <p>
 * Define los beans de infraestructura que conectan Spring con JavaFX:
 * el {@link ResourceBundle} para la internacionalización y el
 * {@link StageManager} que gestiona los cambios de escena.
 * </p>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see SpringFXMLLoader
 * @see StageManager
 */
@Configuration
public class AppJavaConfig {

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    /**
     * Proporciona el {@link ResourceBundle} cargado desde {@code Bundle.properties}.
     * <p>
     * Este bundle se inyecta en {@link SpringFXMLLoader} para que los FXML puedan
     * utilizar expresiones de internacionalización con el prefijo {@code %}.
     * </p>
     *
     * @return el ResourceBundle con las cadenas de texto de la aplicación
     */
    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }

    /**
     * Crea el {@link StageManager} con el escenario principal de JavaFX.
     * <p>
     * Se declara como {@code @Lazy} porque el {@link Stage} solo existe después
     * de que JavaFX haya inicializado su entorno gráfico, lo cual ocurre tras
     * el arranque del contexto de Spring.
     * </p>
     *
     * @param stage el escenario principal de JavaFX
     * @return el StageManager configurado
     * @throws IOException si el cargador FXML no puede inicializarse
     */
    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springFXMLLoader, stage);
    }
}
