package ua.ihromant.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ColorEdge {
    private final Edge edge;
    private TextColor color;
}
