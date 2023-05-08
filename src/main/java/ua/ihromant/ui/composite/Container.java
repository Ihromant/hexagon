package ua.ihromant.ui.composite;

import java.util.stream.Stream;

public interface Container extends Component {
    void clear();

    Container scene();

    Container add(Component comp);

    default Container add(Component... children) {
        for (Component child : children) {
            add(child);
        }
        return this;
    }

    default Container add(Stream<Component> children) {
        children.forEach(this::add);
        return this;
    }
}
