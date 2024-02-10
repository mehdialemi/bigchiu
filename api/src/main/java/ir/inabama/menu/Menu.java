package ir.inabama.menu;

import lombok.Data;

import java.util.List;

@Data
public class Menu {

    private String title;
    private List<SubMenu> list;

    @Data
    public static class SubMenu {
        private String title;
        private String iconBase64;

        private String link;

        private List<SubMenuDetail> list;
    }

    @Data
    public static class SubMenuDetail {
        private String text;
        private String link;
    }
}
