package view;

import java.io.IOException;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;


public class RootLayoutController
{
	@FXML
	private BorderPane root;

	@FXML
	private JFXHamburger hamburger;

	/**
	 * SplitPane of the main frame
	 */
	private SplitPane mainFrame;

	/**
	 * mainFrameController
	 */
	private MainFarmeController mainFrameController;



	/**
	 * Useful for to open the slide at the initial position after closing it
	 */
	private double initialSize;



	@FXML
	public void initialize()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainFrame.fxml"));
		initialSize = 0.3;
		try
		{
			mainFrame = loader.load();
			mainFrameController = loader.getController();
			root.setCenter(mainFrame);

			mainFrame.prefWidthProperty().bind(root.widthProperty());
			mainFrame.prefHeightProperty().bind(root.heightProperty().subtract(root.getTop().prefHeight(0)));

			initTransition();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Return the transition of the divider.
	 */
	private Transition initDividerTransition()
	{
		Transition dataTransition = new Transition()
		{
			{setCycleDuration(Duration.millis(500));}

			@Override
			protected void interpolate(double frac) {
				mainFrame.setDividerPosition(0, initialSize * frac);
			}
		};

		//define the actions to be done at the end
		dataTransition.setOnFinished(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				if(dataTransition.getRate() > 0) //expanding
				{
					mainFrameController.getData().setMinWidth(25);
				}
				else //Collapsing
				{
					mainFrameController.getData().maxWidthProperty().unbind();
					mainFrameController.getData().setMaxWidth(0);
					mainFrameController.getData().setVisible(false);
				}
			}
		});

		return dataTransition;
	}

	/**
	 *
	 */
	private void initTransition()
	{
		Transition dataTransition = initDividerTransition();
		dataTransition.setRate(-1);

		HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
		transition.setRate(-0.5);

		hamburger.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				dataTransition.setRate(dataTransition.getRate() * (-1));
				transition.setRate(transition.getRate() * (-1));

				if(dataTransition.getRate() > 0) //expanding
				{
					mainFrameController.getData().maxWidthProperty().bind(mainFrame.widthProperty().multiply(0.3));
					mainFrameController.getData().setVisible(true);
				}
				else // collapsing
				{
					initialSize = mainFrame.getDividerPositions()[0];
					mainFrameController.getData().setMinWidth(0);
				}

				transition.play();
				dataTransition.play();
			}
		});
	}


	public void setGraphic(Node container)
	{
		mainFrameController.getGraphic().setContent(container);

//		mainFrameController.getGraphic().ge
	}



	public MainFarmeController getMainFrame(){
		return this.mainFrameController;
	}

}
