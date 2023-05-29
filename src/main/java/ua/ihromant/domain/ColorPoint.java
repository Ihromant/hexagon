package ua.ihromant.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ColorPoint {
    private int id;
    private TextColor color;
    private Point point;
    private String name;
    private int nameAngle;
}
