package ua.ihromant.widget;

import lombok.Getter;
import ua.ihromant.domain.ColorPoint;
import ua.ihromant.domain.Point;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
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
    private final ColorSelectionWidget colorSelection;
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
                (colorSelection = new ColorSelectionWidget(ui).setSelected(TextColor.black)).getContainer(),
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
            colorSelection.setSelected(cp.getColor());
        }
    }

    public ColorPoint collect() {
        try {
            return new ColorPoint().setId(Integer.parseInt(id.getValue()))
                    .setPoint(new Point(Double.parseDouble(x.getValue()), Double.parseDouble(y.getValue())))
                    .setColor(colorSelection.getSelected());
        } catch (Exception e) {
            return null;
        }
    }
}
