package com.damianqm.proyectoIntermodular;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.damianqm.proyectoIntermodular.config.StageManager;
import com.damianqm.proyectoIntermodular.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Punto de entrada principal de la aplicación.
 * <p>
 * Combina el ciclo de vida de JavaFX ({@link Application}) con el contenedor de
 * Spring Boot. En el método {@link #init()} se arranca el contexto de Spring antes
 * de que JavaFX muestre ninguna ventana, garantizando que todos los beans estén
 * disponibles cuando se construya la UI.
 * </p>
 *
 * <p>Flujo de arranque:</p>
 * <ol>
 *   <li>{@code main()} invoca {@link Application#launch(String[])}.</li>
 *   <li>JavaFX llama a {@link #init()}, que arranca Spring.</li>
 *   <li>JavaFX llama a {@link #start(Stage)}, que obtiene el {@link StageManager}
 *       del contexto y muestra la primera pantalla.</li>
 * </ol>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 */
@SpringBootApplication
public class ProyectoIntermodularDamianApplication extends Application {

    /** Contexto de Spring, inicializado en {@link #init()} y usado en {@link #start(Stage)}. */
    protected ConfigurableApplicationContext springContext;

    /** Gestor de escenas de JavaFX, obtenido del contexto de Spring. */
    protected StageManager stageManager;

    /**
     * Inicializa el contexto de Spring antes de que JavaFX construya la UI.
     * Este método se ejecuta en el hilo de inicio de la aplicación JavaFX,
     * no en el hilo de la aplicación de JavaFX (JavaFX Application Thread).
     *
     * @throws Exception si Spring no puede arrancar
     */
    @Override
    public void init() throws Exception {
        springContext = springBootApplicationContext();
    }

    /**
     * Punto de entrada de la JVM. Delega en {@link Application#launch(String[])}
     * para iniciar el ciclo de vida de JavaFX.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    /**
     * Llamado por JavaFX una vez que el entorno gráfico está listo.
     * Obtiene el {@link StageManager} del contexto de Spring y muestra
     * la pantalla inicial.
     *
     * @param primaryStage el escenario principal proporcionado por JavaFX
     * @throws Exception si no se puede mostrar la escena inicial
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stageManager = springContext.getBean(StageManager.class, primaryStage);
        displayInitialScene();
    }

    /**
     * Muestra la primera pantalla de la aplicación.
     * Puede sobreescribirse en subclases para cambiar la vista inicial,
     * por ejemplo en tests funcionales.
     */
    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /**
     * Crea y arranca el contexto de Spring Boot pasando los argumentos
     * de línea de comandos recibidos por JavaFX.
     *
     * @return el contexto de Spring ya inicializado
     */
    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder =
                new SpringApplicationBuilder(ProyectoIntermodularDamianApplication.class);
        String[] args = getParameters().getRaw().toArray(String[]::new);
        return builder.run(args);
    }
}
