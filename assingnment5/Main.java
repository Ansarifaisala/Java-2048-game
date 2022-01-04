package assingnment5;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

/**
 * This program is designed to only onside of movement in the game that is left
 * So if user presses Any other key like up,down or right
 * This program turns the game at a certain angle so that it can call the logic of left
 * And then update the value according to that
 * Key Bindings are used in this program so there are no buttons
 * The key are Up Arrow, Down Arrow, Left Arrow and, Right Arrow
 * And once the Game is over you hit Esc button to restart
 * User can press Esc anytime to restart game
 * @author Ansari Mohammed Faisal -000812671
 */
public class Main extends Application {

    /****/
    private static final int CELL_SIZE = 64;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Game 2048");

        FlowPane rootNode = new FlowPane();

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());

        Game game = new Game();
        Scene scene = new Scene(rootNode, game.getWidth(), game.getHeight());
        stage.setScene(scene);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    game.resetGame();
                }

                if (!game.move() || (!game.won && !game.move())) {
                    game.lost = true;
                }

                if (!game.won && !game.lost) {
                    switch (event.getCode()) {
                        case LEFT:
                            game.left();
                            break;
                        case RIGHT:
                            game.right();
                            break;
                        case DOWN:
                            game.down();
                            break;
                        case UP:
                            game.up();
                            break;
                    }
                }
                game.relocate(330, 390);
            }
        });

        rootNode.getChildren().add(game);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                GraphicsContext gc = game.getGraphicsContext2D();
                gc.setFill(Color.rgb(187, 173, 160, 1.0));
                gc.fillRect(0, 0, game.getWidth(), game.getHeight());

                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        Cell cell = game.getCells()[x + y * 4];
                        int value = cell.number;
                        int xOffset = offsetCoors(x);
                        int yOffset = offsetCoors(y);

                        gc.setFill(cell.getBackground());
                        gc.fillRoundRect(xOffset, yOffset, CELL_SIZE, CELL_SIZE, 14, 14);
                        gc.setFill(cell.getForeground());

                        final int size = value < 100 ? 32 : value < 1000 ? 28 : 24;
                        gc.setFont(Font.font("Arial", FontWeight.BOLD, size));
                        gc.setTextAlign(TextAlignment.CENTER);


                        String s = String.valueOf(value);

                        if (value != 0)
                            gc.fillText(s, xOffset + CELL_SIZE / 2, yOffset + CELL_SIZE / 2 - 2);
                        if (game.won || game.lost) {
                            gc.setFill(Color.rgb(255, 255, 255));
                            gc.fillRect(0, 0, game.getWidth(), game.getHeight());
                            gc.setFill(Color.rgb(78, 139, 202));
                            gc.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                            if (game.won) {
                                gc.fillText("You Win!", 95, 150);
                            }
                            if (game.lost) {
                                gc.fillText("Game Over!", 150, 130);
                                gc.fillText("You Lose!", 160, 200);
                            }
                            if (game.won || game.lost) {
                                gc.setFont(Font.font("Arial", FontWeight.LIGHT, 16));
                                gc.setFill(Color.rgb(128, 128, 128));
                                gc.fillText("Press ESC to play again", 110, 270);
                            }
                        }
                        gc.setFont(Font.font("Arial", FontWeight.LIGHT, 18));
                        gc.fillText("Score: " + game.score, 200, 350);
                    }
                }
            }
        }.start();
    }

    private static int offsetCoors(int arg) {
        return arg * (16 + 64) + 16;
    }

    public static void main(String[] args) {
        launch(args);
    }

}