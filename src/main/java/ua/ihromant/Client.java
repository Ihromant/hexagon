package ua.ihromant;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

public class Client {
    private static final int HEXAGON_RADIUS = 25;
    private static final int X_TOP = 100;
    private static final int Y_TOP = 100;
    public static void main(String[] args) {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement svg = HTMLDocument.current().createElementNS("http://www.w3.org/2000/svg", "svg").cast();
        svg.setAttribute("width", "800");
        svg.setAttribute("height", "600");
        for (int i = 0; i < Field.WIDTH; i++) {
            for (int j = 0; j < Field.HEIGHT; j++) {
                Field field = new Field(i, j);
                HTMLElement poly = computePolygon(field, computeCenter(field));
                svg.appendChild(poly);
            }
        }
        document.getBody().appendChild(svg);
    }

    private static HTMLElement computePolygon(Field field, Point center) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(center.add(shift(i)).toString());
            builder.append(' ');
        }
        builder.append(center.add(shift(5)).toString());
        HTMLElement poly = HTMLDocument.current().createElementNS("http://www.w3.org/2000/svg", "polygon").cast();
        poly.setId(field.getX() + "_" + field.getY());
        poly.setAttribute("points", builder.toString());
        poly.setAttribute("stroke", "limegreen");
        poly.setAttribute("fill", "none");
        return poly;
    }

    private static Point shift(int number) {
        double ang = Math.PI / 2 - number * Math.PI / 3;
        return new Point(HEXAGON_RADIUS * Math.cos(ang), HEXAGON_RADIUS * Math.sin(ang));
    }

    public static Point computeCenter(Field f) {
        int xSub = 2 * f.getX() - f.getY() % 2;
        int ySub = 3 * f.getY();
        double xSwap = Math.sin(Math.PI / 3) * HEXAGON_RADIUS * xSub;
        double ySwap = Math.cos(Math.PI / 3) * HEXAGON_RADIUS * ySub;
        return new Point(X_TOP + xSwap, Y_TOP + ySwap);
    }
}
