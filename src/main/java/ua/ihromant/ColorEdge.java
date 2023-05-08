package ua.ihromant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ua.ihromant.ui.Color;

@RequiredArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ColorEdge {
    private final Edge edge;
    private Color color;
}
