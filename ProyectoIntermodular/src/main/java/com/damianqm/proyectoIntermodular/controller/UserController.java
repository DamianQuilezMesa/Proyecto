package com.damianqm.proyectoIntermodular.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.damianqm.proyectoIntermodular.config.StageManager;
import com.damianqm.proyectoIntermodular.modelo.User;
import com.damianqm.proyectoIntermodular.services.UserService;
import com.damianqm.proyectoIntermodular.util.HelpDialog;
import com.damianqm.proyectoIntermodular.view.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

/**
 * Controlador del panel principal de gestión de usuarios.
 * <p>
 * Permite realizar operaciones CRUD sobre la entidad {@link User}:
 * </p>
 * <ul>
 *   <li><b>Crear</b>: rellena el formulario izquierdo y pulsa «Guardar».</li>
 *   <li><b>Editar</b>: pulsa el icono de edición en la columna «Edit» de la tabla;
 *       el formulario se rellena con los datos del usuario seleccionado.</li>
 *   <li><b>Eliminar</b>: selecciona filas y usa el menú contextual (clic derecho)
 *       o el menú Edit &gt; Delete.</li>
 *   <li><b>Limpiar</b>: pulsa «Reset» para vaciar el formulario.</li>
 * </ul>
 *
 * <p>Acceso a la ayuda:</p>
 * <ul>
 *   <li>Botón {@code ?} en la barra superior.</li>
 *   <li>Tecla {@code F1} mientras esta pantalla esté activa.</li>
 *   <li>Menú Help &gt; Help.</li>
 * </ul>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see UserService
 * @see HelpDialog
 */
@Controller
public class UserController implements Initializable {

    private static final String HELP_TITLE = "Ayuda - Panel de Usuarios";
    private static final String HELP_CONTENT =
            "PANEL DE GESTIÓN DE USUARIOS\n\n"
            + "AÑADIR UN USUARIO:\n"
            + "  1. Rellene los campos del formulario izquierdo.\n"
            + "  2. Seleccione el rol en el desplegable.\n"
            + "  3. Pulse 'Save' para guardar.\n\n"
            + "EDITAR UN USUARIO:\n"
            + "  1. Pulse el icono de edición (lápiz) en la columna 'Edit'.\n"
            + "  2. Modifique los campos necesarios (email y contraseña\n"
            + "     no se actualizan al editar).\n"
            + "  3. Pulse 'Save' para confirmar los cambios.\n\n"
            + "ELIMINAR USUARIO(S):\n"
            + "  1. Seleccione una o varias filas (Ctrl+Click para múltiples).\n"
            + "  2. Haga clic derecho y seleccione 'Delete', o use\n"
            + "     el menú Edit > Delete.\n\n"
            + "LIMPIAR FORMULARIO:\n"
            + "  Pulse 'Reset' para vaciar todos los campos.\n\n"
            + "CERRAR SESIÓN:\n"
            + "  Pulse 'Logout' o use el menú File > Exit.\n\n"
            + "Pulse F1 en cualquier momento para abrir esta ventana de ayuda.";

    // ── Formulario izquierdo ──────────────────────────────────────────────────

    @FXML private Label userId;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dob;
    @FXML private RadioButton rbMale;
    @FXML private ToggleGroup gender;
    @FXML private RadioButton rbFemale;
    @FXML private ComboBox<String> cbRole;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private Button reset;
    @FXML private Button saveUser;

    // ── Tabla central ─────────────────────────────────────────────────────────

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Long> colUserId;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, LocalDate> colDOB;
    @FXML private TableColumn<User, String> colGender;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, Boolean> colEdit;

    // ── Barra superior ────────────────────────────────────────────────────────

    @FXML private Button btnLogout;
    @FXML private Button btnHelp;

    // ── Dependencias Spring ───────────────────────────────────────────────────

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;

    // ── Estado interno ────────────────────────────────────────────────────────

    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private final ObservableList<String> roles   = FXCollections.observableArrayList("Admin", "User");

    // ═════════════════════════════════════════════════════════════════════════
    // Acciones de menú / barra
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Cierra la aplicación de forma ordenada.
     *
     * @param event evento de acción del menú File &gt; Exit
     */
    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Cierra la sesión actual y vuelve a la pantalla de login.
     *
     * @param event evento de acción del botón Logout
     */
    @FXML
    private void logout(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /**
     * Muestra la ventana de ayuda contextual de esta pantalla.
     * Se invoca desde el botón {@code ?}, desde {@code F1} y desde
     * el menú Help &gt; Help.
     */
    @FXML
    private void showHelp() {
        HelpDialog.show(HELP_TITLE, HELP_CONTENT);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Acciones del formulario
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Limpia todos los campos del formulario izquierdo.
     *
     * @param event evento de acción del botón Reset
     */
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    /**
     * Guarda o actualiza un usuario según el estado del formulario.
     * <p>
     * Si {@code userId} está vacío se crea un nuevo usuario; si tiene valor
     * se actualiza el usuario existente. En ambos casos se validan los campos
     * obligatorios antes de persistir.
     * </p>
     *
     * @param event evento de acción del botón Save
     */
    @FXML
    private void saveUser(ActionEvent event) {
        boolean baseValid = validate("First Name", getFirstName(), "[a-zA-Z]+")
                && validate("Last Name", getLastName(), "[a-zA-Z]+")
                && emptyValidation("DOB", dob.getEditor().getText().isEmpty())
                && emptyValidation("Role", getRole() == null);

        if (!baseValid) {
            return;
        }

        if (userId.getText() == null || userId.getText().isEmpty()) {
            // ── Crear nuevo usuario ──────────────────────────────────────────
            if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
                    && emptyValidation("Password", getPassword().isEmpty())) {

                User user = new User();
                user.setFirstName(getFirstName());
                user.setLastName(getLastName());
                user.setDob(getDob());
                user.setGender(getGender());
                user.setRole(getRole());
                user.setEmail(getEmail());
                user.setPassword(getPassword());

                User newUser = userService.save(user);
                saveAlert(newUser);
            }
        } else {
            // ── Actualizar usuario existente ─────────────────────────────────
            User user = userService.find(Long.parseLong(userId.getText()));
            user.setFirstName(getFirstName());
            user.setLastName(getLastName());
            user.setDob(getDob());
            user.setGender(getGender());
            user.setRole(getRole());
            User updatedUser = userService.update(user);
            updateAlert(updatedUser);
        }

        clearFields();
        loadUserDetails();
    }

    /**
     * Elimina los usuarios seleccionados en la tabla tras confirmar con el usuario.
     * <p>
     * Se puede invocar desde el menú contextual (clic derecho) y desde
     * el menú Edit &gt; Delete.
     * </p>
     *
     * @param event evento de acción del item de menú Delete
     */
    @FXML
    private void deleteUsers(ActionEvent event) {
        List<User> users = userTable.getSelectionModel().getSelectedItems();

        if (users.isEmpty()) {
            showWarning("Selección vacía", "Seleccione al menos un usuario para eliminar.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que desea eliminar los usuarios seleccionados?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            userService.deleteInBatch(users);
            loadUserDetails();
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Inicialización
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Inicializa el controlador: configura el combo de roles, el modo de selección
     * múltiple de la tabla, las propiedades de las columnas y registra F1 para la ayuda.
     *
     * @param location  URL usada para resolver rutas relativas del objeto raíz
     * @param resources ResourceBundle de internacionalización
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbRole.setItems(roles);
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setColumnProperties();
        loadUserDetails();

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

    // ═════════════════════════════════════════════════════════════════════════
    // Métodos privados de soporte
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Configura los {@code cellValueFactory} de cada columna de la tabla y
     * asigna la factoría de celdas editables a la columna «Edit».
     */
    private void setColumnProperties() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEdit.setCellFactory(createEditCellFactory());
    }

    /**
     * Crea la factoría de celdas para la columna «Edit».
     * <p>
     * Cada celda contiene un botón con un icono de lápiz que, al pulsarse,
     * carga los datos del usuario de esa fila en el formulario izquierdo.
     * </p>
     *
     * @return la {@link Callback} que construye las celdas de edición
     */
    private Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> createEditCellFactory() {
        return param -> new TableCell<>() {

            private final Image imgEdit = new Image(
                    getClass().getResourceAsStream("/images/edit.png"));
            private final Button btnEdit = new Button();

            {
                ImageView iv = new ImageView(imgEdit);
                iv.setPreserveRatio(true);
                iv.setSmooth(true);
                iv.setCache(true);
                btnEdit.setGraphic(iv);
                btnEdit.setStyle("-fx-background-color: transparent;");
                btnEdit.setAccessibleText("Editar usuario");
            }

            @Override
            protected void updateItem(Boolean check, boolean empty) {
                super.updateItem(check, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnEdit.setOnAction(e -> populateFormWithUser(
                            getTableView().getItems().get(getIndex())));
                    setGraphic(btnEdit);
                    setAlignment(Pos.CENTER);
                    setText(null);
                }
            }
        };
    }

    /**
     * Rellena el formulario izquierdo con los datos del usuario indicado
     * para permitir su edición.
     *
     * @param user el usuario cuyos datos se cargan en el formulario
     */
    private void populateFormWithUser(User user) {
        userId.setText(Long.toString(user.getId()));
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        dob.setValue(user.getDob());
        rbMale.setSelected("Male".equals(user.getGender()));
        rbFemale.setSelected(!"Male".equals(user.getGender()));
        cbRole.getSelectionModel().select(user.getRole());
    }

    /**
     * Vacía todos los campos del formulario y restablece los valores por defecto.
     */
    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        dob.getEditor().clear();
        dob.setValue(null);
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
        cbRole.getSelectionModel().clearSelection();
        email.clear();
        password.clear();
    }

    /**
     * Recarga la lista de usuarios desde la base de datos y actualiza la tabla.
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());
        userTable.setItems(userList);
    }

    // ── Alertas ───────────────────────────────────────────────────────────────

    /**
     * Muestra una confirmación de que el usuario ha sido creado correctamente.
     *
     * @param user el usuario recién creado
     */
    private void saveAlert(User user) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Usuario creado");
        alert.setHeaderText(null);
        alert.setContentText("El usuario " + user.getFirstName() + " " + user.getLastName()
                + " ha sido creado con ID " + user.getId() + ".");
        alert.showAndWait();
    }

    /**
     * Muestra una confirmación de que el usuario ha sido actualizado correctamente.
     *
     * @param user el usuario recién actualizado
     */
    private void updateAlert(User user) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Usuario actualizado");
        alert.setHeaderText(null);
        alert.setContentText("El usuario " + user.getFirstName() + " " + user.getLastName()
                + " ha sido actualizado correctamente.");
        alert.showAndWait();
    }

    /**
     * Muestra una advertencia de validación indicando qué campo es incorrecto.
     *
     * @param field nombre del campo con el error
     * @param empty {@code true} si el campo está vacío; {@code false} si el formato es inválido
     */
    private void validationAlert(String field, boolean empty) {
        String message = field.equals("Role")
                ? "Seleccione un " + field
                : (empty ? "Introduzca " + field : "Introduzca un " + field + " válido");

        showWarning("Error de validación", message);
    }

    /**
     * Muestra un diálogo de advertencia genérico.
     *
     * @param title   título del diálogo
     * @param content texto del mensaje
     */
    private void showWarning(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ── Validaciones ──────────────────────────────────────────────────────────

    /**
     * Valida que el valor de un campo no esté vacío y cumpla el patrón indicado.
     *
     * @param field   nombre del campo (se usa en el mensaje de error)
     * @param value   valor introducido por el usuario
     * @param pattern expresión regular que debe cumplir el valor
     * @return {@code true} si el valor es válido; {@code false} y muestra alerta en caso contrario
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            }
            validationAlert(field, false);
            return false;
        }
        validationAlert(field, true);
        return false;
    }

    /**
     * Valida que un campo no esté vacío.
     *
     * @param field nombre del campo (se usa en el mensaje de error)
     * @param empty {@code true} si el campo está vacío
     * @return {@code true} si el campo tiene valor; {@code false} y muestra alerta si está vacío
     */
    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        }
        validationAlert(field, true);
        return false;
    }

    // ── Getters de campos del formulario ──────────────────────────────────────

    /**
     * @return el texto del campo «First Name»
     */
    public String getFirstName() { return firstName.getText(); }

    /**
     * @return el texto del campo «Last Name»
     */
    public String getLastName() { return lastName.getText(); }

    /**
     * @return la fecha seleccionada en el {@link DatePicker}
     */
    public LocalDate getDob() { return dob.getValue(); }

    /**
     * @return {@code "Male"} si el radio masculino está seleccionado, {@code "Female"} en caso contrario
     */
    public String getGender() { return rbMale.isSelected() ? "Male" : "Female"; }

    /**
     * @return el rol seleccionado en el combo, o {@code null} si no se ha elegido ninguno
     */
    public String getRole() { return cbRole.getSelectionModel().getSelectedItem(); }

    /**
     * @return el texto del campo «Email»
     */
    public String getEmail() { return email.getText(); }

    /**
     * @return el texto del campo «Password»
     */
    public String getPassword() { return password.getText(); }
}
