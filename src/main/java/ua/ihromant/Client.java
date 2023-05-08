package ua.ihromant;

import ua.ihromant.ui.Box;
import ua.ihromant.ui.HTMLUIFactory;
import ua.ihromant.ui.HTMLUtil;
import ua.ihromant.ui.LineConf;
import ua.ihromant.ui.MouseEvt;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Board;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.Container;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.ui.composite.TextButton;
import ua.ihromant.widget.PointWidget;
import ua.ihromant.widget.Widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Client {
    private static final UIFactory ui = new HTMLUIFactory();
    private static int counter;
    private static final Map<Integer, ColorPoint> model = new HashMap<>();
    private static final Canvas points = ui.canvas().pixelSize(800, 800);
    private static final PointWidget pointWidget = new PointWidget(ui);

    public static void main(String[] args) {
        Container cont = ui.horizontal(LineConf.gap(20));
        cont.box(Box.padding(10));
        Board board = ui.board();
        board.add(generateAxis());
        board.add(points.position(10, 10));
        points.addEventListener("click", (MouseEvt e) -> {
            Point clicked = fromCrd(new Crd(e.offsetX(), e.offsetY()));
            Optional<ColorPoint> point = model.values().stream().filter(p -> p.getPoint().dist(clicked) < 0.3).findAny();
            if (point.isPresent()) {
                pointWidget.fill(point.get());
                pointWidget.getContainer().setVisible(true);
            } else {
                pointWidget.getContainer().setVisible(false);
            }
        });
        pointWidget.getUpdate().addEventListener("click", e -> {
            ColorPoint cp = pointWidget.collect();
            if (cp != null) {
                model.put(cp.getId(), cp);
                repaint();
            }
        });
        cont.add(board);
        Container controls = ui.vertical(LineConf.gap(10));
        controls.box(Box.padding(10));
        TextButton button = ui.txtButton("Add point");
        button.addEventListener("click", e -> {
            PointWidget pw = new PointWidget(ui);
            ColorPoint cp = new ColorPoint().setId(counter);
            pw.fill(cp);
            pw.getClose().setVisible(true);
            Container modal = showModal(pw);
            pw.getClose().addEventListener("click", ev -> modal.detach());
            pw.getUpdate().addEventListener("click", ev -> {
                ColorPoint result = pw.collect();
                if (result != null) {
                    modal.detach();
                    model.put(result.getId(), result);
                    counter++;
                    repaint();
                }
            });
        });
        controls.add(button);
        controls.add(pointWidget.getContainer().setVisible(false));
        cont.add(controls);
        ui.root().add(cont);
    }

    private static void repaint() {
        GraphicsContext context = points.getContext();
        context.clearRect(0, 0, 800, 800);
        for (ColorPoint cp : model.values()) {
            context.setFill(cp.getColor());
            context.circle(HTMLUtil.round(cp.getPoint().x() * 20 + 400), HTMLUtil.round(400 - cp.getPoint().y() * 20), 2);
        }
    }

    private static Crd toCrd(Point point) {
        return new Crd(point.x() * 20 + 400, 400 - point.y() * 20);
    }

    private static Point fromCrd(Crd crd) {
        return new Point(0.05 * (crd.x() - 400), 0.05 * (400 - crd.y()));
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

    private static Container showModal(Widget widget) {
        Container modal = ui.modal();
        modal.add(widget.getContainer());
        ui.root().add(modal);
        return modal;
    }

    private record Modal(Container elem) {

    }
}
