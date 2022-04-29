package ir.inabama.shopping.clothes;

import lombok.Data;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClothingQuery {

    private String model;
    private String material;
    private int minSize;
    private int maxSize;
    private int limit;
    private List<String> colors = new ArrayList <>();
    private SortType sortType = SortType.LATEST;

    public List<Criterion> filters() {
        List<Criterion> list = new ArrayList <>();
        if (model != null)
            list.add(Restrictions.like("model", model, MatchMode.ANYWHERE));
        if (material != null)
            list.add(Restrictions.like("material", material, MatchMode.ANYWHERE));

        if (minSize > 0 || maxSize > 0) {
            if (minSize == 0) {
                list.add(Restrictions.sizeLe("size", maxSize));
            } else if (maxSize == 0) {
                list.add(Restrictions.sizeGe("size", minSize));
            } else {
                list.add(Restrictions.between("size", minSize, maxSize));
            }
        }

        if (colors.size() > 0) {
            list.add(Restrictions.in("color", colors));
        }


        return list;
    }
}
