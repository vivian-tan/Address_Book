package types;

import java.util.Arrays;
import java.util.List;

public enum ContactOptions implements IOption{
    MAIN_MENU("1", "Zurück zum Hauptmenü"),
    SHOW_CONTACTS("2", "Zurück zur Liste der Kontakte"),
    EDIT_CONTACT("3", "Kontakt bearbeiten"),
    DELETE_CONTACT("4", "Kontakt löschen");

    private String keyCode;
    private String label;

    private ContactOptions(String keyCode, String label) {
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
    @Override
    public List<IOption> getValues() {
        return Arrays.asList(ContactOptions.values());
    }
}
