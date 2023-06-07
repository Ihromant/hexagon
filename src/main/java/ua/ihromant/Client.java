package ua.ihromant;

import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.TextMetrics;
import ua.ihromant.domain.ColorEdge;
import ua.ihromant.domain.ColorPoint;
import ua.ihromant.domain.Model;
import ua.ihromant.domain.Point;
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
import ua.ihromant.widget.ExportWidget;
import ua.ihromant.widget.PointWidget;
import ua.ihromant.widget.Widget;

import java.util.Optional;

public class Client {
    private static final UIFactory ui = new HTMLUIFactory();
    private static final Model model = new Model();
    private static final Canvas points = ui.canvas().pixelSize(800, 800);
    private static final PointWidget pointWidget = new PointWidget(ui);
    private static final EdgeWidget edgeWidget = new EdgeWidget(ui);
    private static Runnable callback;
    private static final GraphicsContext context = ui.canvas().getContext();

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
                executeRepaint();
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
                    executeRepaint();
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
                    executeRepaint();
                }
            });
        });
        controls.add(addEdge);
        controls.add(pointWidget.getContainer().setVisible(false));
        cont.add(controls);
        edgeWidget.getUpdate().setVisible(false);
        edgeWidget.getDelete().setVisible(true);
        controls.add(edgeWidget.getContainer().setVisible(false));
        TextButton export = ui.txtButton("Export");
        export.addEventListener("click", e -> {
            ExportWidget ew = new ExportWidget(ui);
            Container modal = showModal(ew);
            ew.getClose().addEventListener("click", ev -> modal.detach());
            ew.getText().setText(getExportedText());
        });
        controls.add(export);
        UIRange range = ui.range().setRange(-50, 50).setValue(0);
        Text text = ui.text().setText(0);
        range.setListener(i -> {
            double angle = 0.1 * i;
            text.setText(Double.toString(angle));
            model.setAngle(angle);
            executeRepaint();
        });
        controls.add(range);
        controls.add(text);
        ui.root().add(cont);
    }

    private static String getExportedText() {
        StringBuilder result = new StringBuilder();
        result.append("\\begin{picture}(800,800)(0,0)\n");
        result.append("\\linethickness{1pt}\n");
        for (ColorEdge edge : model.colorEdges()) {
            ColorPoint from = model.byId(edge.getEdge().from());
            ColorPoint to = model.byId(edge.getEdge().to());
            Crd fc = toCrd(from.getPoint());
            Crd tc = toCrd(to.getPoint());
            int dx = tc.x() - fc.x();
            int dy = tc.y() - fc.y();
            int gcd = gcd(Math.abs(dx), Math.abs(dy));
            int nX = dx / gcd;
            int nY = dy / gcd;
            result.append("\\put(").append(fc.x()).append(",").append(800 - fc.y()).append(")").append("{\\color{").append(edge.getColor()).append("}").append("\\line(").append(nX).append(",").append(-nY).append("){").append(Math.max(Math.abs(dx), Math.abs(dy))).append("}}\n");
        }
        for (ColorPoint cp : model.colorPoints()) {
            Crd crd = toCrd(cp.getPoint());
            Crd textCrd = calculateTextCrd(cp, crd);
            result.append("\\put(").append(crd.x()).append(",").append(800 - crd.y()).append(")").append("{\\color{").append(cp.getColor()).append("}").append("\\circle*{3}}\n");
            result.append("\\put(").append(textCrd.x()).append(",").append(800 - textCrd.y()).append("){$").append(cp.getName()).append("$}\n");
        }
        result.append("\\end{picture}");
        return result.toString();
    }

    private static int gcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }

    private static void executeRepaint() {
        if (callback == null) {
            callback = Client::repaint;
            Window.requestAnimationFrame(d -> {
                callback.run();
                callback = null;
            });
        }
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
            Crd textCrd = calculateTextCrd(cp, crd);
            context.text(cp.getName(), textCrd.x(), textCrd.y());
        }
    }

    private static Crd toCrd(Point point) {
        return new Crd(point.x() * 20 + 400, 400 - point.y() * 20);
    }

    private static Crd calculateTextCrd(ColorPoint cp, Crd crd) {
        double radius = 5;
        double angle = Math.toRadians(cp.getNameAngle());
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        TextMetrics metrics = context.measureText(cp.getName());
        double x = cos >= 0 ? crd.x() + radius * cos : crd.x() + radius * cos - metrics.getWidth();
        double y = sin >= 0 ? crd.y() - radius * sin : crd.y() - radius * sin + metrics.getActualBoundingBoxAscent();
        return new Crd(x, y);
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
}
