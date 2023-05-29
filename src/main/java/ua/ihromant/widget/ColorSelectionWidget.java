package ua.ihromant.widget;

import lombok.Getter;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ColorSelectionWidget {
    private static final int ROWS = 3;
    private static final int COLS = 6;
    private final Map<TextColor, Component> components = new EnumMap<>(TextColor.class);
    @Getter
    private TextColor selected;
    @Getter
    private final Component container;

    public ColorSelectionWidget(UIFactory ui) {
        this.container = ui.vertical().add(
                IntStream.range(0, ROWS).mapToObj(i -> ui.horizontal().add(
                                IntStream.range(0, COLS).mapToObj(j -> {
                                    TextColor color = TextColor.values()[i * COLS + j];
                                    Component comp = ui.placeholder(30, 15);
                                    comp.look().setBackground(color);
                                    comp.box(Box.border(Border.TRANSPARENT));
                                    components.put(color, comp);
                                    comp.addEventListener("click", e -> setSelected(color));
                                    return comp;
                                })
                        )
                )
        );
    }

    public ColorSelectionWidget setSelected(TextColor color) {
        if (selected != null) {
            components.get(selected).box(Box.border(Border.TRANSPARENT));
        }
        components.get(color).box(color == TextColor.black ? Box.border(Border.YELLOW) : Box.border(Border.BLACK));
        selected = color;
        return this;
    }
}
