package ua.ihromant;

import ua.ihromant.domain.F9Point;
import ua.ihromant.domain.Point;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUIFactory;
import ua.ihromant.ui.LineConf;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.Container;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.widget.ColorSelectionWidget;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Client {
    private static final double alpha = 0.5;
    private static final UIFactory ui = new HTMLUIFactory();
    private static final Canvas canvas = ui.canvas().pixelSize(1000, 800);
    private static final ColorSelectionWidget colorWidget = new ColorSelectionWidget(ui).setSelected(TextColor.black);
    private static final GraphicsContext context = canvas.getContext();
    private static final TextColor[] model = IntStream.range(0, 91)
            .mapToObj(i -> TextColor.values()[ThreadLocalRandom.current().nextInt(TextColor.values().length - 2)]).toArray(TextColor[]::new);
    private static final Map<Integer, Point> pointCrd = IntStream.range(0, 91).boxed().collect(Collectors.toMap(Function.identity(), p -> {
        if (p < 9 * 9) {
            int x = p / 9;
            int y = p % 9;
            return new Point((x - 4) * 99 + 400, (y - 4) * 99 + 400);
        } else {
            p = p - 9 * 9;
            return new Point(900, (p - 4) * 99 + 400);
        }
    }));

    public static void main(String[] args) {
        Container cont = ui.horizontal(LineConf.gap(20));
        cont.box(Box.padding(10));
        cont.add(canvas);
        Container controls = ui.vertical(LineConf.gap(10));
        controls.box(Box.padding(10));
        controls.add(colorWidget.getContainer());
        cont.add(controls);
        ui.root().add(cont);
        repaint();
    }

    private static void repaint() {
        BitSet[] lines = F9Point.generateUnital();
        BitSet points = Arrays.stream(lines).flatMapToInt(BitSet::stream).collect(BitSet::new, BitSet::set, BitSet::or);
        context.clearRect(0, 0, 1000, 800);
        context.setFill(Color.BLACK);
        int random = ThreadLocalRandom.current().nextInt(lines.length);
        IntStream.range(0, lines.length).forEach(i -> drawLine(lines[i], model[i]));
        points.stream().forEach(p -> context.circle(pointCrd.get(p).x(), pointCrd.get(p).y(), 3));
    }

    private static void drawLine(BitSet line, TextColor color) {
        context.setStroke(color);
        Point[] points = line.stream().mapToObj(pointCrd::get).toArray(Point[]::new);
        Arrays.sort(points, Comparator.comparingDouble(Point::x).thenComparingDouble(Point::y));
        if (color == TextColor.black) {
            System.out.println(Arrays.toString(points));
        }
        bezierForPoints(points[0], points[1], points[2], points[3]);
        bezierForPoints(points[0].add(points[0].subtract(points[1]).mul(0.001)), points[0], points[1], points[2]);
        bezierForPoints(points[3].add(points[3].subtract(points[2]).mul(0.001)), points[3], points[2], points[1]);
    }

    private static void bezierForPoints(Point p0, Point p1, Point p2, Point p3) {
        double d1 = Math.pow(p1.dist(p0), alpha);
        double d2 = Math.pow(p2.dist(p1), alpha);
        double d3 = Math.pow(p3.dist(p2), alpha);
        Point t1;
        Point t2;
        // Modify tangent 1
        {
            double a = d1 * d1;
            double b = d2 * d2;
            double c = (2 * d1 * d1) + (3 * d1 * d2) + (d2 * d2);
            double d = 3 * d1 * (d1 + d2);
            double t1x = (a * p2.x() - b * p0.x() + c * p1.x()) / d;
            double t1y = (a * p2.y() - b * p0.y() + c * p1.y()) / d;
            t1 = new Point(t1x, t1y);
        }
        // Modify tangent 2
        {
            double a = d3 * d3;
            double b = d2 * d2;
            double c = (2 * d3 * d3) + (3 * d3 * d2) + (d2 * d2);
            double d = 3 * d3 * (d3 + d2);
            double t2x = (a * p1.x() - b * p3.x() + c * p2.x()) / d;
            double t2y = (a * p1.y() - b * p3.y() + c * p2.y()) / d;
            t2 = new Point(t2x, t2y);
        }
        context.bezier(p1, t1, t2, p2);
    }
}
