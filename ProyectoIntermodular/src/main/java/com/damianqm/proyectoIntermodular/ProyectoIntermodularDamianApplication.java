package com.damianqm.proyectoIntermodular;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.damianqm.proyectoIntermodular.config.StageManager;
import com.damianqm.proyectoIntermodular.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class ProyectoIntermodularDamianApplication extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, primaryStage);
		displayInitialScene();

	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ProyectoIntermodularDamianApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
