package types;

public enum ActionType {
    EXIT("Addressbuch verlassen"),
    MAIN_MENU("Zurück zum Hauptmenü"),
    ADD_CONTACT("Kontakt hinzufügen"),
    EDIT_CONTACT("Kontakt bearbeiten"),
    REMOVE_CONTACT("Kontakt löschen"),
    SHOW_CONTACT("Kontaktdaten anzeigen"),
    SHOW_CONTACTS("Alle Kontakte anzeigen");

    private String label;

    private ActionType(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
