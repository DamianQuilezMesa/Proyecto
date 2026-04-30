package com.damianqm.proyectoIntermodular.util;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utilidad para mostrar ventanas de ayuda contextual.
 * <p>
 * Cada pantalla de la aplicación puede invocar {@link #show(String, String)}
 * para abrir una ventana modal con información sobre cómo usar esa pantalla.
 * La ventana de ayuda se puede abrir pulsando el botón {@code ?} de la interfaz
 * o mediante el atajo de teclado {@code F1}.
 * </p>
 *
 * <p>Ejemplo de uso desde un controlador:</p>
 * <pre>
 *   HelpDialog.show("Ayuda - Login", "Para acceder introduce tu email y contraseña...");
 * </pre>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 */
public final class HelpDialog {

    /** Anchura fija de la ventana de ayuda en píxeles. */
    private static final double DIALOG_WIDTH = 480.0;

    /** Altura fija de la ventana de ayuda en píxeles. */
    private static final double DIALOG_HEIGHT = 380.0;

    /** Constructor privado: esta clase es una utilidad estática, no se instancia. */
    private HelpDialog() {
        throw new UnsupportedOperationException("Clase de utilidad, no instanciable");
    }

    /**
     * Muestra una ventana modal con el título y contenido de ayuda indicados.
     * <p>
     * La ventana es modal respecto a todas las ventanas de la aplicación
     * ({@link Modality#APPLICATION_MODAL}) y no es redimensionable.
     * </p>
     *
     * @param title   título de la ventana (p. ej. {@code "Ayuda - Inicio de Sesión"})
     * @param content texto con la información de ayuda; admite saltos de línea {@code \n}
     */
    public static void show(String title, String content) {
        Stage helpStage = new Stage();
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.setTitle(title);
        helpStage.setResizable(false);

        Label lblContent = new Label(content);
        lblContent.setWrapText(true);
        lblContent.setStyle("-fx-font-size: 13px; -fx-text-fill: #333333;");
        lblContent.setPadding(new Insets(5, 10, 5, 10));

        ScrollPane scrollPane = new ScrollPane(lblContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setPrefHeight(280);

        Button btnClose = new Button("Cerrar");
        btnClose.setPrefWidth(90);
        btnClose.setPrefHeight(34);
        btnClose.getStyleClass().add("btnGreen");
        btnClose.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        btnClose.setOnAction(e -> helpStage.close());

        VBox root = new VBox(15, scrollPane, btnClose);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f6fb;");

        Scene scene = new Scene(root, DIALOG_WIDTH, DIALOG_HEIGHT);
        try {
            String css = Objects.requireNonNull(
                    HelpDialog.class.getResource("/styles/Styles.css")).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (NullPointerException ignored) {
            // Si no se encuentra la hoja de estilos se muestra sin CSS personalizado
        }

        helpStage.setScene(scene);
        helpStage.show();
    }
}
