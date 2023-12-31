package ua.ihromant;

import ua.ihromant.domain.LineData;
import ua.ihromant.domain.Point;
import ua.ihromant.domain.STS13Visualizer;
import ua.ihromant.domain.UnitalVisualizer;
import ua.ihromant.domain.Visualizer;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUIFactory;
import ua.ihromant.ui.LineConf;
import ua.ihromant.ui.MouseEvt;
import ua.ihromant.ui.SelectEvent;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.Container;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.ui.composite.Select;
import ua.ihromant.ui.composite.SelectOption;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Client {
    private static final Map<String, Visualizer> visualizers = Map.of("unital", new UnitalVisualizer(),
            "sts13fst", STS13Visualizer.first,
            "sts13snd", STS13Visualizer.second);
    private static final UIFactory ui = new HTMLUIFactory();
    private static final Canvas canvas = ui.canvas().pixelSize(1000, 800);
    private static final Select select = ui.select();
    private static final GraphicsContext context = canvas.getContext();
    private static Visualizer visualizer = new UnitalVisualizer();
    private static LineData[] model = visualizer.model();
    private static Integer selected;

    public static void main(String[] args) {
        Container cont = ui.horizontal(LineConf.gap(20));
        cont.box(Box.padding(10));
        cont.add(canvas);
        canvas.addEventListener("click", (MouseEvt e) -> {
            Point click = new Point(e.offsetX(), e.offsetY());
            Map<Integer, Double> distances = IntStream.range(0, model.length).boxed().collect(Collectors.toMap(Function.identity(), i -> {
                LineData ld = model[i];
                double min = Double.MAX_VALUE;
                for (Point[] curve : ld.bezier()) {
                    for (int j = 0; j < 1000; j++) {
                        double t = 1.0 * j / 1000;
                        double c0 = (1 - t) * (1 - t) * (1 - t);
                        double c1 = 3 * (1 - t) * (1 - t) * t;
                        double c2 = 3 * (1 - t) * t * t;
                        double c3 = t * t * t;
                        Point at = curve[0].mul(c0).add(curve[1].mul(c1)).add(curve[2].mul(c2)).add(curve[3].mul(c3));
                        min = Math.min(min, at.dist(click));
                    }
                }
                return min;
            }));
            Integer sel = IntStream.range(0, model.length).boxed().min(Comparator.comparingDouble(distances::get)).orElseThrow();
            selected = distances.get(sel) < 3 ? sel : null;
            repaint();
        });
        Container controls = ui.vertical(LineConf.gap(10));
        controls.box(Box.padding(10));
        visualizers.forEach((k, v) -> select.addOption(new SelectOption(k, k)));
        select.setSelected("unital");
        controls.add(select);
        select.addEventListener("select", (SelectEvent e) -> {
            visualizer = visualizers.get(e.value());
            model = visualizer.model();
            selected = null;
            repaint();
        });
        cont.add(controls);
        ui.root().add(cont);
        repaint();
    }

    private static void repaint() {
        context.clearRect(0, 0, 1000, 800);
        IntStream.range(0, model.length).filter(idx -> selected == null || idx == selected).forEach(Client::drawLine);
        drawPoints();
    }

    private static void drawPoints() {
        BitSet line = selected == null ? new BitSet() : model[selected].points();
        visualizer.points().stream().forEach(p -> {
            context.setFill(line.get(p) ? Color.RED : Color.BLACK);
            Point crd = visualizer.coordinate(p);
            context.circle(crd.x(), crd.y(), 3);
        });
    }

    private static void drawLine(int idx) {
        LineData ld = model[idx];
        context.setStroke(ld.color());
        Arrays.stream(ld.bezier()).forEach(bp -> context.bezier(bp[0], bp[1], bp[2], bp[3]));
    }
}
