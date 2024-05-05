package ir.inabama.common;

public enum Category {

    PLUS_18("plus18"),
    NEWS("news"),
    RELIGION("religion"),
    GENERAL_KNOWLEDGE("general_knowledge"),
    ECONOMY("economy"),
    TRADE("trade"),
    SUCCESS("success"),
    LIFE("life"),
    ROMANTIC("romantic"),
    FAMILY("family"),
    FUN("fun"),
    FASHION_BEAUTY("fashion_beauty"),
    FOOD("food"),
    HEALTH("health"),
    SPORT("sport"),
    MUSIC("music"),
    FILM_CINEMA("film_cinema"),
    SCIENCE_TECHNOLOGY("science_technology"),
    EDUCATION("education"),
    POLITICS_SOCIETY("politics_society"),
    NATURE_ANIMALS("nature-animals"),
    LINKS("links");

    private String name;
    Category(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
