package application;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import data.ObservableScenario;
import data.Task;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import results.Service;
import view.RootLayoutController;
import view.planing.PlaningFrame;
import view.planing.PlaningFrame2;
import view.planing.graphicalTask.HermesFactory;


public class Main extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}


	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FileInputStream fichier = new FileInputStream("tasks.ser");
			ObjectInputStream ois = new ObjectInputStream(fichier);
			ArrayList<Task> taskList = (ArrayList<Task>)ois.readObject();


			FileInputStream fichier2 = new FileInputStream("services.ser");
			ObjectInputStream ois2 = new ObjectInputStream(fichier2);
			ArrayList<Service> sol = (ArrayList<Service>)ois2.readObject();

//			System.out.println(sol);


			ObservableScenario scenario = new ObservableScenario(taskList, sol);
//			PlaningFrame graph = new PlaningFrame(scenario, TimeConstant.ONE_DAY);
			PlaningFrame2 chart = new PlaningFrame2(new HermesFactory(), scenario.getSolution());

			System.out.println("done");

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/rootLayout.fxml"));

			Region split = loader.load();

			RootLayoutController controller = loader.getController();

			controller.setGraphic(chart.getChart());
			controller.getMainFrame().getTasksController().setList(scenario.getTasks());

			Scene scene = new Scene(split, 800, 400);

			primaryStage.setScene(scene);
			primaryStage.setMinWidth(900);
			primaryStage.setMinHeight(600);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
