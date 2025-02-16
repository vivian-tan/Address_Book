package types;

public enum ContactListOptions implements IOption{
    MAIN_MENU("1", "Zurück zum Hauptmenü"),
    ADD_CONTACT("2", "Kontakt hinzufügen"),
    SHOW_CONTACT("3", "Kontaktdaten anzeigen"),
    EDIT_CONTACT("4", "Kontakt bearbeiten"),
    DELETE_CONTACT("5", "Kontakt löschen");

    private String keyCode;
    private String label;

    private ContactListOptions(String keyCode, String label) {
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