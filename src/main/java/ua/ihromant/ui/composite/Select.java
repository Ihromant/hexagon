package ua.ihromant.ui.composite;

public interface Select extends Component {
    Select setWidth(int width);

    String getSelected();

    Select setSelected(String value);

    Select setDisabled(boolean disabled);

    int getIndex();

    Select setIndex(int index);

    SelectOption[] getOptions();

    Select setOptions(SelectOption[] options);

    void removeOption(String value);

    void addOption(SelectOption option);
}
