package ir.inabama.common;

public enum Province {

    AZARBAYJAN_SHARGHI(1),
    AZARBAYJAN_GHARBI(2),
    ARDEBIL(3),
    ESFEHAN(4),
    ILAM(5),
    BOOSHEHR(6),
    TEHRAN(7),
    CHEHAR_MAHAL_BAKHTIYARI(8),
    KHOOZESTAN(10),
    ZANJAN(11),
    SEMNAN(12),
    SISTAN(13),
    FARS(14),
    KERMAN(15),
    KORDESTAN(16),
    KERMANSHAH(17),
    KOHKILOOYER(18),
    GILAN(19),
    LORESTAN(20),
    MAZANDARAN(21),
    MARKAZI(22),
    HORMOZGAN(23),
    HAMEDAN(24),
    YAZD(25),
    GOM(26),
    GOLESTAN(27),
    GHAZVIN(28),
    KHORASAN_JONOOBI(29),
    KHORASAN_RAZAVI(30),
    KHORASAN_SHOMALI(31),
    ALBORZ(32);

    private int id;
    Province(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
