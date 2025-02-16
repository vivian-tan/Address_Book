package types;

public enum MainMenuOptions implements IOption{
    EXIT("0", "Addressbuch verlassen"),
    SHOW_CONTACTS("1", "Alle Kontakte anzeigen"),
    ADD_CONTACT("2", "Kontakt hinzufügen");

    private String keyCode;
    private String label;

    private MainMenuOptions(String keyCode, String label) {
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
