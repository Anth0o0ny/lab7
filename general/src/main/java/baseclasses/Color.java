package baseclasses;




public enum Color {

    RED("Красный"),
    BLACK("Черный"),
    YELLOW("Желтый"),
    ORANGE("Оранжевый"),
    WHITE("Белый");

    private final String title;

    Color(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }

    public static Color fromString(String colorStr) {
        for (Color color : Color.values()) {
            if (color.toString().equalsIgnoreCase(colorStr))
                return color;
        }
        return null;
    }
}
