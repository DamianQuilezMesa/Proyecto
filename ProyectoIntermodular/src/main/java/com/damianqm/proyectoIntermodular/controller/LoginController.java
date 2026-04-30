package com.damianqm.proyectoIntermodular.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.damianqm.proyectoIntermodular.config.StageManager;
import com.damianqm.proyectoIntermodular.services.UserService;
import com.damianqm.proyectoIntermodular.util.HelpDialog;
import com.damianqm.proyectoIntermodular.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Controlador de la pantalla de inicio de sesión.
 * <p>
 * Gestiona la autenticación del usuario: valida que los campos no estén vacíos
 * y delega la comprobación de credenciales en {@link UserService#authenticate(String, String)}.
 * Si las credenciales son correctas, navega al panel de usuarios; si no,
 * muestra un mensaje de error en pantalla.
 * </p>
 *
 * <p>Acceso a la ayuda:</p>
 * <ul>
 *   <li>Botón {@code ?} situado en la esquina superior derecha del formulario.</li>
 *   <li>Tecla {@code F1} en cualquier momento mientras esta pantalla esté activa.</li>
 * </ul>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see UserService
 * @see HelpDialog
 */
@Controller
public class LoginController implements Initializable {

    private static final String HELP_TITLE = "Ayuda - Inicio de Sesión";
    private static final String HELP_CONTENT =
            "PANTALLA DE INICIO DE SESIÓN\n\n"
            + "Para acceder a la aplicación:\n"
            + "  1. Introduzca su correo electrónico en el campo 'Usuario'.\n"
            + "  2. Introduzca su contraseña en el campo 'Contraseña'.\n"
            + "  3. Pulse el botón 'Entrar' o la tecla Enter.\n\n"
            + "Si las credenciales son incorrectas, se mostrará un aviso\n"
            + "bajo el formulario.\n\n"
            + "Credenciales de ejemplo por defecto:\n"
            + "  Usuario:    admin\n"
            + "  Contraseña: admin\n\n"
            + "Pulse F1 en cualquier momento para abrir esta ventana de ayuda.";

    @FXML private Button btnLogin;
    @FXML private Button btnHelp;
    @FXML private PasswordField password;
    @FXML private TextField username;
    @FXML private Label lblLogin;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    /**
     * Intenta autenticar al usuario con las credenciales introducidas.
     * <p>
     * Si el login es correcto navega al panel de usuarios ({@link FxmlView#USER}).
     * En caso contrario muestra un mensaje de error en {@code lblLogin}.
     * </p>
     *
     * @param event el evento de acción (botón o tecla Enter en el campo contraseña)
     */
    @FXML
    private void login(ActionEvent event) {
        if (userService.authenticate(getUsername(), getPassword())) {
            stageManager.switchScene(FxmlView.USER);
        } else {
            lblLogin.setText("Credenciales incorrectas. Inténtelo de nuevo.");
        }
    }

    /**
     * Muestra la ventana de ayuda contextual de esta pantalla.
     * Se invoca desde el botón {@code ?} y desde el atajo {@code F1}.
     */
    @FXML
    private void showHelp() {
        HelpDialog.show(HELP_TITLE, HELP_CONTENT);
    }

    /**
     * Devuelve el texto introducido en el campo de usuario (email).
     *
     * @return el email introducido
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Devuelve el texto introducido en el campo de contraseña.
     *
     * @return la contraseña introducida
     */
    public String getPassword() {
        return password.getText();
    }

    /**
     * Inicializa el controlador registrando el atajo de teclado {@code F1}
     * para abrir la ayuda. El registro se hace cuando el nodo entra en la escena,
     * ya que en el momento de {@code initialize()} la escena aún no existe.
     *
     * @param location  URL usada para resolver rutas relativas del objeto raíz
     * @param resources ResourceBundle de internacionalización
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnHelp.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.F1) {
                        showHelp();
                    }
                });
            }
        });
    }
}
