package com.damianqm.proyectoIntermodular.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import org.slf4j.Logger;

import com.damianqm.proyectoIntermodular.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Gestor del escenario principal de JavaFX.
 * <p>
 * Centraliza el cambio de escenas sobre el {@link Stage} primario.
 * Todas las pantallas de la aplicación se muestran a través de este gestor,
 * garantizando que siempre tengan el mismo tamaño (900 × 600 px) y que
 * se centren en pantalla al mostrarse.
 * </p>
 *
 * <p>Uso típico desde un controlador:</p>
 * <pre>
 *   stageManager.switchScene(FxmlView.USER);
 * </pre>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see FxmlView
 * @see SpringFXMLLoader
 */
public class StageManager {

    /** Ancho fijo de todas las ventanas de la aplicación en píxeles. */
    private static final double WINDOW_WIDTH = 900.0;

    /** Alto fijo de todas las ventanas de la aplicación en píxeles. */
    private static final double WINDOW_HEIGHT = 600.0;

    private static final Logger LOG = getLogger(StageManager.class);

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    /**
     * Construye el gestor asociándolo al escenario principal y al cargador FXML.
     *
     * @param springFXMLLoader el cargador FXML integrado con Spring
     * @param stage            el escenario principal de la aplicación
     */
    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    /**
     * Cambia la escena activa del escenario principal a la vista indicada.
     * <p>
     * Carga el FXML correspondiente, prepara la escena con el tamaño estándar
     * y centra la ventana en pantalla.
     * </p>
     *
     * @param view la vista destino definida en {@link FxmlView}
     */
    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }

    /**
     * Configura y muestra el nodo raíz en el escenario principal.
     * <p>
     * Reutiliza la escena existente si ya hay una para evitar recrearla en cada
     * cambio de vista. Fija el tamaño de la ventana y la centra en pantalla.
     * </p>
     *
     * @param rootnode el nodo raíz de la nueva vista
     * @param title    el título a mostrar en la barra del sistema operativo
     */
    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/Styles.css")).toExternalForm());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit("Unable to show scene for title: " + title, exception);
        }
    }

    /**
     * Prepara la escena reutilizando la existente o creando una nueva.
     *
     * @param rootnode el nodo raíz que se establecerá como contenido de la escena
     * @return la escena lista para mostrarse
     */
    private Scene prepareScene(Parent rootnode) {
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Carga la jerarquía de nodos de un fichero FXML y devuelve el nodo raíz.
     *
     * @param fxmlFilePath ruta del recurso FXML (relativa al classpath)
     * @return el nodo raíz cargado; nunca {@code null}
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "El nodo raíz del FXML no puede ser null");
        } catch (Exception exception) {
            logAndExit("Error al cargar la vista FXML: " + fxmlFilePath, exception);
        }
        return rootNode;
    }

    /**
     * Registra el error en el log y termina la aplicación JavaFX de forma controlada.
     *
     * @param errorMsg  mensaje descriptivo del error
     * @param exception la excepción que lo causó
     */
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception);
        Platform.exit();
    }
}
