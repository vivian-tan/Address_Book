package types;

import java.util.Arrays;
import java.util.List;

public enum EditContactOptions implements IOption{
    EXIT_EDIT_MODE("1", "Bearbeitung beenden"),
    EDIT_PERSON("2", "Angaben zur Person bearbeiten"),
    EDIT_ADDRESS("3", "Angaben zur Adresse bearbeiten"),
    ADD_ADDRESS("4", "Adresse hinzufügen"),
    REMOVE_ADDRESS("5", "Adresse löschen"),
    EDIT_PHONE("6", "Angaben zur Telefonnummer bearbeiten"),
    ADD_PHONE("7", "Telefonnummer hinzufügen"),
    REMOVE_PHONE("8", "Telefonnummer löschen");

    private String keyCode;
    private String label;

    private EditContactOptions(String keyCode, String label) {
        this.keyCode = keyCode;
        this.label = label;
    }

    @Override
    public String getKeyCode() {
        return keyCode;
    }
    @Override
    public String getLabel(){
        return label;
    }
}
