package ir.bigchiu.shop.clothing;

import ir.bigchiu.shop.common.SortType;
import lombok.Data;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClothingFilterQuery {

    private String model;
    private String material;
    private int minSize;
    private int maxSize;
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
