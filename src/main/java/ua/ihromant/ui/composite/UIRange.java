package ua.ihromant.ui.composite;

import java.util.function.IntConsumer;

public interface UIRange extends Component {
    UIRange setRange(int min, int max);

    int value();

    int min();

    int max();

    UIRange setValue(int newValue);

    UIRange setListener(IntConsumer listener);
}

