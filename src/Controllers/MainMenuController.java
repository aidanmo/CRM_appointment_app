package Controllers;

import Controllers.ToolBarController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

public class MainMenuController implements Initializable {

    public StackPane MenuContent;
    @FXML
    private JFXDrawer menu_drawer;

    @FXML
    private JFXHamburger menu_hamburger;

    @FXML
    private Text tab_title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            initToolBar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initToolBar() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(MainMenuController.class.getResource
                        ("/views/toolbar.fxml"));
        VBox vBox = fxmlLoader.load();
        ToolBarController toolbarController = fxmlLoader
                .getController();
        menu_drawer.setSidePane(vBox);
        menu_drawer.close();

    }

}
