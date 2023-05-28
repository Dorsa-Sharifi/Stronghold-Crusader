package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Human.Troop.Army;
import model.Manage;
import model.Map;
import view.Model.NewButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TileManager extends Application {
    //TODO : Show Map ---> Armin's Method
    //TODO : Remove the color of selected buttons in event 5
    //TODO : Check that selected unit would be empty or not in GameController if it was full
    // show an error that user should make a decision for them
    //TODO : Make conditions for event Handlers
    //TODO : Select Unit must change
    //TODO : We need a button named : Select which should be on the gameMap to make the selection and deselection easier
    //TODO : Method which calculates the Production things on a tile
    public ArrayList<String> cellArmyNameType = new ArrayList<>();
    public Text showCellData = new Text();
    public int avgDamage;
    public int avgSpeed;

    public TilePane view = new TilePane();

    public ArrayList<NewButton>[][] allButtons;
    public ArrayList<NewButton> selectedButtons;
    public Pane pane = new Pane();
    public int avgHp;
    public int avgProduction;
    public int leastProduction;
    public int mostProduction;
    public int numberOfMySoldiers;
    Point firstPoint = new Point();
    Point secondPoint = new Point();
    private boolean drawIsOn;
    public boolean mouseMoveCanClearHover = true;

    @Override
    public void start(Stage stage) throws Exception {
//        tilePane.setLayoutX(-100);
//        tilePane.setLayoutY(-100);
//        tilePane.setPrefColumns(100);
//        tilePane.setMaxWidth(10000);
        createButtonsArraylist();
        ArrayList<Node> list = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < 100; i++) {
                NewButton newButton = new NewButton(j, i);
                applyingMouseEventForButton(newButton);
//                mouseMovement();
                newButton.setPrefSize(51, 54);
                newButton.setFocusTraversable(false);
                list.add(newButton);
            }
        }

//         width  = 1530
//         height = 800

//        Background background = new Background(new BackgroundImage(new Image
//                ("C:\\Users\\F1\\Desktop\\AP\\PROJECT\\project-group-19\\src\\main\\resources\\image\\cegla2.jpg"),
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        for (int u = 0; u < 16; u++) {
            for (int g = 0; g < 30; g++) {
//                ((NewButton)list.get((u + 3) * 100 + (g + 10))).setBackground(background);
                NewButton button = (NewButton) list.get((u + 3) * 100 + (g + 10));
                button.setLayoutX(g * 51.2);
                button.setLayoutY(u * 54);
                pane.getChildren().add(list.get((u + 3) * 100 + (g + 10)));
                allButtons[u][g].add(button);
            }
        }

//        view.setBackground(new Background( new BackgroundImage( new Image(Game.class.getResource("/image/cegla2.jpg").toExternalForm()) ,
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        double width = resolution.getWidth();
        double height = resolution.getHeight();
        view.requestFocus();
        view.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
//                System.out.println(keyName);
                if (keyName.equals("Add")) {

                } else if (keyName.equals("Subtract")) {

                }
            }
        });
        pane.getChildren().add(view);
        Scene scene = new Scene(pane, width - 50, height - 50);

        stage.setTitle("Tile Pane");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }


    private void drawRec(int x1, int y1, int x2, int y2, ArrayList<NewButton>[][] allButtons) {
        selectedButtons.clear();
        int maxX, minX, maxY, minY;
        if (x2 - x1 >= 0) {
            maxX = (int) (x2 / 51.2);
            minX = (int) (x1 / 51.2);
        } else {
            maxX = (int) (x1 / 51.2);
            minX = (int) (x2 / 51.2);
        }
        if (y2 - y1 >= 0) {
            maxY = y2 / 54;
            minY = y1 / 54;
        } else {
            maxY = y1 / 54;
            minY = y2 / 54;
        }
        for (int j = minY; j <= maxY; j++) {
            for (int i = minX; i <= maxX; i++) {
                NewButton newButton = allButtons[j][i].get(0);
//                newButton.setStyle("-fx-background-color: #1316aa");
                newButton.setStyle("-fx-border-color: brown");
                selectedButtons.add(newButton);

            }
        }
    }

    public void getCellData(NewButton newButton) {
        cellArmyNameType.clear();
        int damage = 0;
        int hp = 0;
        int speed = 0;
        int i;
        for (i = 0; i < newButton.getArmy().size(); i++) {
            if (!cellArmyNameType.contains(newButton.getArmy().get(i).getNames().getName())) {
                cellArmyNameType.add(newButton.getArmy().get(i).getNames().getName());
            }
            damage += newButton.getArmy().get(i).getAttackPower();
            hp += newButton.getArmy().get(i).getHp();
            speed += newButton.getArmy().get(i).getSpeed();
        }
        if (i != 0) {
            avgHp = hp / i;
            avgSpeed = speed / i;
            avgDamage = damage / i;
        }
    }

    public void createButtonsArraylist() {
        allButtons = new ArrayList[16][30];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 30; j++) {
                allButtons[i][j] = new ArrayList<>();
            }
        }
    }

    public void numberOfAllSoldiers() {
        for (NewButton selectedButton : selectedButtons) {
            int x = selectedButton.getX();
            int y = selectedButton.getY();
            if (Map.getTroopMap()[x][y].size() != 0) {
                for (Army army : Map.getTroopMap()[x][y]) {
                    if (army.getOwner().equals(Manage.getCurrentEmpire())) {
                        numberOfMySoldiers++;
                    }
                }
            }
        }
    }

    public void clearSelectedButtons() {
        //TODO: if button is selected :
        selectedButtons.clear();
    }

    public void removeColorOfSelectedButtons() {
        for (NewButton selectedButton : selectedButtons) {
            selectedButton.setStyle("");
        }
        drawIsOn = false;
    }

    private void applyingMouseEventForButton(NewButton newButton) {
        selectedButtons = new ArrayList<>();
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newButton.setStyle("-fx-border-color: brown");
            }
        };
        EventHandler<MouseEvent> event2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (selectedButtons.size() == 1) {
                    newButton.setStyle(null);
                }
                showCellData.setText("");
                pane.getChildren().remove(showCellData);
            }
        };
        EventHandler<MouseEvent> event3 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getCellData(newButton);
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                int x = (int) b.getX();
                int y = (int) b.getY() - 50;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AVG Hp : " + avgHp + '\n' + "AVG Damage : " + avgDamage + '\n' +
                        "AVG Speed : " + avgSpeed + '\n');
                for (int i = 0; i < cellArmyNameType.size(); i++) {
                    stringBuilder.append(cellArmyNameType.get(i) + " ");
                }
                showCellData.setText(stringBuilder.toString());
                showCellData.setX(x);
                showCellData.setY(y);
                if (showCellData != null && !pane.getChildren().contains(showCellData))
                    pane.getChildren().add(showCellData);
            }
        };

        EventHandler<MouseEvent> event4 = new EventHandler<MouseEvent>() {//-----> Start of Number 11
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (!drawIsOn) {
                            removeColorOfSelectedButtons();
                        }
                        PointerInfo a = MouseInfo.getPointerInfo();
                        firstPoint = a.getLocation();
                        firstPoint.setLocation(a.getLocation().getX(), a.getLocation().getY());
                        drawIsOn = true;
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Hi");
                }
            }
        };
        EventHandler<MouseEvent> event5 = new EventHandler<MouseEvent>() {// -------> Number 11
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && drawIsOn) {
                    PointerInfo a = MouseInfo.getPointerInfo();
                    secondPoint.setLocation(a.getLocation().getX(), a.getLocation().getY());
                    drawRec(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y, allButtons);
                    drawIsOn = false;
//                    TextInputDialog textInputDialog = new TextInputDialog();
//                    textInputDialog.setHeaderText("Enter the name and number of required army :");
//                    textInputDialog.setContentText("Name of Army: \nNumber:");
//                    Optional<String> result = textInputDialog.showAndWait();
                } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

                }
            }
        };
        EventHandler<MouseEvent> event6 = new EventHandler<MouseEvent>() { //----> Number 3
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
//                    newButton.setSelected(true);
//                        selectedButtons.add(newButton);
//                        newButton.setSelected(true);
//                        PointerInfo a = MouseInfo.getPointerInfo();
//                        Point b = a.getLocation();
//                        int x = (int) b.getX();
//                        int y = (int) b.getY() - 50;
//                        StringBuilder stringBuilder = new StringBuilder();
//                        numberOfAllSoldiers();
//                        stringBuilder.append("Soldier Num: " + numberOfMySoldiers + "\n" + "Min Production: " + leastProduction +
//                                "\nMax Production: " + mostProduction + "\nAVG Production: " + avgProduction);
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    //Do sth else
                }
            }
        };

        newButton.setOnMousePressed(event4);
        newButton.setOnMouseReleased(event5);
        //newButton.setOnMouseExited(event2);
        newButton.setOnMouseMoved(event3);
        //newButton.setOnMouseClicked(event6);
    }

    int x, y;

    public void mouseMovement() {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        x = (int) b.getX();
        y = (int) b.getY();
        pane.setOnMouseMoved((event) -> {
            PointerInfo a2 = MouseInfo.getPointerInfo();
            Point b2 = a2.getLocation();
            int x2 = (int) b2.getX();
            int y2 = (int) b2.getY();
            double changeInX = x2 - x;
            double changeInY = y2 - y;
            if (changeInX > 0) {
                System.out.println("moving right");
            } else if (changeInX < 0) {
                System.out.println("moving left");
            }
            if (changeInY > 0) {
                System.out.println("moving down");
            } else if (changeInY < 0) {
                System.out.println("moving up");
            }
            x = x2;
            y = y2;
        });
    }

}