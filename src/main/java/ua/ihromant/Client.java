package ua.ihromant;

import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUIFactory;
import ua.ihromant.ui.LineConf;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Board;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.Container;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.ui.composite.TextButton;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final UIFactory ui = new HTMLUIFactory();
    private static int counter;
    private static final List<ColorPoint> model = new ArrayList<>();
    private static final Canvas points = ui.canvas().pixelSize(800, 800);

    public static void main(String[] args) {
        Container cont = ui.horizontal(LineConf.gap(20));
        cont.box(Box.padding(10));
        Board board = ui.board();
        board.add(generateAxis());
        board.add(points.position(0, 0));
        cont.add(board);
        Container controls = ui.vertical(LineConf.gap(10));
        controls.box(Box.padding(10));
        TextButton button = ui.txtButton("Add point");
        controls.add(button);
        cont.add(controls);
        ui.root().add(cont);
    }

    private static Canvas generateAxis() {
        Canvas result = ui.canvas().pixelSize(800, 800);
        GraphicsContext context = result.getContext();
        context.hLine(400, 0, 800);
        context.line(800, 400, 780, 405); // arrow
        context.line(800, 400, 780, 395); // arrow
        for (int i = -19; i < 20; i++) {
            if (i == 0) {
                continue;
            }
            context.vLine((i + 20) * 20, 397, 403);
            if (i % 5 == 0) {
                context.text(String.valueOf(i), (i + 20) * 20 - 7, 415);
            }
        }
        context.vLine(400, 0, 800);
        context.line(400, 0, 405, 20); // arrow
        context.line(400, 0, 395, 20); // arrow
        for (int i = -19; i < 20; i++) {
            if (i == 0) {
                continue;
            }
            context.hLine((i + 20) * 20, 397, 403);
            if (i % 5 == 0) {
                context.text(String.valueOf(i), 405, (20 - i) * 20 + 3);
            }
        }
        context.line(0, 0, 800, 800);
        context.line(0, 800, 800, 0);
        return result;
    }

    private record ColorPoint(int id, Color color, Point point) {

    }
}
