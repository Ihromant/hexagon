package ua.ihromant;

import ua.ihromant.ui.Box;
import ua.ihromant.ui.HTMLUIFactory;
import ua.ihromant.ui.LineConf;
import ua.ihromant.ui.MouseEvt;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Board;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.Container;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.ui.composite.Text;
import ua.ihromant.ui.composite.TextButton;
import ua.ihromant.ui.composite.UIRange;
import ua.ihromant.widget.EdgeWidget;
import ua.ihromant.widget.PointWidget;
import ua.ihromant.widget.Widget;

import java.util.Optional;

public class Client {
    private static final UIFactory ui = new HTMLUIFactory();
    private static final Model model = new Model();
    private static final Canvas points = ui.canvas().pixelSize(800, 800);
    private static final PointWidget pointWidget = new PointWidget(ui);
    private static final EdgeWidget edgeWidget = new EdgeWidget(ui);

    public static void main(String[] args) {
        Container cont = ui.horizontal(LineConf.gap(20));
        cont.box(Box.padding(10));
        Board board = ui.board();
        board.add(generateAxis());
        board.add(points.position(10, 10));
        points.addEventListener("click", (MouseEvt e) -> {
            Point clicked = fromCrd(new Crd(e.offsetX(), e.offsetY()));
            Optional<ColorPoint> point = model.findPoint(clicked);
            if (point.isPresent()) {
                pointWidget.fill(point.get());
                pointWidget.getContainer().setVisible(true);
                edgeWidget.getContainer().setVisible(false);
            } else {
                pointWidget.getContainer().setVisible(false);
                Optional<ColorEdge> edge = model.findEdge(clicked);
                if (edge.isPresent()) {
                    edgeWidget.getContainer().setVisible(true);
                    edgeWidget.fill(edge.get());
                } else {
                    edgeWidget.getContainer().setVisible(false);
                }
            }
        });
        pointWidget.getUpdate().addEventListener("click", e -> {
            ColorPoint cp = pointWidget.collect();
            if (cp != null) {
                model.addOrReplace(cp);
                repaint();
            }
        });
        cont.add(board);
        Container controls = ui.vertical(LineConf.gap(10));
        controls.box(Box.padding(10));
        TextButton addPoint = ui.txtButton("Add point");
        addPoint.addEventListener("click", e -> {
            PointWidget pw = new PointWidget(ui);
            ColorPoint cp = new ColorPoint().setId(model.counter());
            pw.fill(cp);
            pw.getClose().setVisible(true);
            Container modal = showModal(pw);
            pw.getClose().addEventListener("click", ev -> modal.detach());
            pw.getUpdate().addEventListener("click", ev -> {
                ColorPoint result = pw.collect();
                if (result != null) {
                    modal.detach();
                    model.addOrReplace(result);
                    repaint();
                }
            });
        });
        controls.add(addPoint);
        TextButton addEdge = ui.txtButton("Add edge");
        addEdge.addEventListener("click", e -> {
            EdgeWidget ew = new EdgeWidget(ui);
            ew.getClose().setVisible(true);
            Container modal = showModal(ew);
            ew.getClose().addEventListener("click", ev -> modal.detach());
            ew.getUpdate().addEventListener("click", ev -> {
                ColorEdge result = ew.collect();
                if (result != null) {
                    modal.detach();
                    model.addOrReplace(result);
                    repaint();
                }
            });
        });
        controls.add(addEdge);
        controls.add(pointWidget.getContainer().setVisible(false));
        cont.add(controls);
        edgeWidget.getUpdate().setVisible(false);
        edgeWidget.getDelete().setVisible(true);
        controls.add(edgeWidget.getContainer().setVisible(false));
        UIRange range = ui.range().setRange(-50, 50).setValue(0);
        Text text = ui.text().setText(0);
        range.setListener(i -> {
            double angle = 0.1 * i;
            text.setText(Double.toString(angle));
            model.setAngle(angle);
            repaint();
        });
        controls.add(range);
        controls.add(text);
        ui.root().add(cont);
    }

    private static void repaint() {
        GraphicsContext context = points.getContext();
        context.clearRect(0, 0, 800, 800);
        for (ColorEdge edge : model.colorEdges()) {
            context.setStroke(edge.getColor());
            ColorPoint from = model.byId(edge.getEdge().from());
            ColorPoint to = model.byId(edge.getEdge().to());
            Crd fc = toCrd(from.getPoint());
            Crd tc = toCrd(to.getPoint());
            context.line(fc.x(), fc.y(), tc.x(), tc.y());
        }
        for (ColorPoint cp : model.colorPoints()) {
            context.setFill(cp.getColor());
            Crd crd = toCrd(cp.getPoint());
            context.circle(crd.x(), crd.y(), 2);
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
