package com.damianqm.proyectoIntermodular;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Tests de integración de contexto para la aplicación principal.
 * <p>
 * Verifica que el contexto de Spring se carga correctamente
 * y que todos los beans están disponibles.
 * </p>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ProyectoIntermodularDamianApplicationTests {

    /**
     * Comprueba que el contexto de Spring se inicializa sin errores.
     */
    @Test
    void contextLoads() {
    }
}
