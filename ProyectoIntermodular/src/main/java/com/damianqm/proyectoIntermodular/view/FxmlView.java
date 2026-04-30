package com.damianqm.proyectoIntermodular.view;

import java.util.ResourceBundle;

/**
 * Enumeración que centraliza las definiciones de las vistas FXML de la aplicación.
 * <p>
 * Cada constante mapea una vista con su fichero FXML y el título que se
 * mostrará en la barra de título de la ventana. Los títulos se leen del
 * {@code Bundle.properties} para facilitar la internacionalización.
 * </p>
 *
 * <p>Para añadir una nueva pantalla basta con:</p>
 * <ol>
 *   <li>Crear el fichero FXML en {@code /resources/fxml/}.</li>
 *   <li>Añadir la clave de título en {@code Bundle.properties}.</li>
 *   <li>Añadir una nueva constante en este enum.</li>
 * </ol>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see com.damianqm.proyectoIntermodular.config.StageManager
 */
public enum FxmlView {

    /**
     * Pantalla de inicio de sesión.
     */
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },

    /**
     * Panel principal de gestión de usuarios.
     */
    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    };

    /**
     * Devuelve el título de la vista para mostrarlo en la barra de título.
     *
     * @return el título localizado
     */
    public abstract String getTitle();

    /**
     * Devuelve la ruta del fichero FXML asociado a esta vista.
     *
     * @return ruta relativa al classpath (p. ej. {@code /fxml/Login.fxml})
     */
    public abstract String getFxmlFile();

    /**
     * Obtiene una cadena del {@code Bundle.properties} por su clave.
     *
     * @param key la clave del bundle
     * @return el valor localizado
     */
    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
