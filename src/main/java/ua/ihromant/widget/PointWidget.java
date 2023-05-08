package ua.ihromant.widget;

import lombok.Getter;
import ua.ihromant.ColorPoint;
import ua.ihromant.Point;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.RGBAColor;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Input;
import ua.ihromant.ui.composite.TextButton;

public class PointWidget implements Widget {
    @Getter
    private final Component container;
    private final Input id;
    private final Input x;
    private final Input y;
    private final Input r;
    private final Input g;
    private final Input b;
    @Getter
    private final TextButton update;
    @Getter
    private final TextButton close;

    public PointWidget(UIFactory ui) {
        this.container = ui.vertical().add(
                ui.horizontal().add(
                        id = ui.input().setDisabled(true),
                        ui.text().setText("Id")
                ),
                ui.horizontal().add(
                        x = ui.input(),
                        ui.text().setText("x")
                ),
                ui.horizontal().add(
                        y = ui.input(),
                        ui.text().setText("y")
                ),
                ui.horizontal().add(
                        r = ui.input(),
                        ui.text().setText("r")
                ),
                ui.horizontal().add(
                        g = ui.input(),
                        ui.text().setText("g")
                ),
                ui.horizontal().add(
                        b = ui.input(),
                        ui.text().setText("b")
                ),
                ui.horizontal().add(
                        update = ui.txtButton("Update"),
                        (close = ui.txtButton("Close")).setVisible(false)
                )
        ).box(Box.border(Border.color(Color.RED_FLAG)));
    }

    public void fill(ColorPoint cp) {
        id.setValue(Integer.toString(cp.getId()));
        if (cp.getPoint() != null) {
            x.setValue(Double.toString(cp.getPoint().x()));
            y.setValue(Double.toString(cp.getPoint().y()));
        }
        if (cp.getColor() != null) {
            r.setValue(Integer.toString(cp.getColor().r()));
            g.setValue(Integer.toString(cp.getColor().g()));
            b.setValue(Integer.toString(cp.getColor().b()));
        }
    }

    public ColorPoint collect() {
        try {
            return new ColorPoint().setId(Integer.parseInt(id.getValue()))
                    .setPoint(new Point(Double.parseDouble(x.getValue()), Double.parseDouble(y.getValue())))
                    .setColor(new RGBAColor(Integer.parseInt(r.getValue()), Integer.parseInt(g.getValue()), Integer.parseInt(b.getValue())));
        } catch (Exception e) {
            return null;
        }
    }
}
