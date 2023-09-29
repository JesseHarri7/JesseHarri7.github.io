package Players;

public enum PlayerIDs {

    JESSE("17a7333a-dba7-52c3-94c6-f70dc04d8e74"),
    MINGI_ALT("1aefc637-d337-5a83-870c-fe6cfd5d5aae"),
    MINGI_OG("432d35d6-b399-5d80-a617-35b3ee811beb"),
    LEE("2019764c-a4da-5f18-bf46-97cfe3b1a03e"),
    AMMAAR("7d79ca5b-9d20-5294-bc39-776268836040"),
    KIARA("fe3dded5-cec1-5001-9934-116d8acd9dc9"),
    AZRAA("08d91147-c428-57d9-a005-879bfcca0f17"),
    JOHANNES("1219cb4c-13e4-56b5-b597-d7e342d04278"),
    RADESH("5208b6c8-bd46-5967-8d7c-f82bd39c1606"),
    JORDAN("59b2e7dc-cc0c-5236-9912-c508d6e95c33"),
    KEAGAN("c6157198-e901-554f-97b5-0bc6df85725b");



    private String playerUUID;

    private PlayerIDs(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerUUID() {
        return this.playerUUID;
    }
}
