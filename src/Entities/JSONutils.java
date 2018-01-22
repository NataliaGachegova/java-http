package Entities;

import java.util.List;
import java.util.Set;

public class JSONutils {
    public static <T extends JSONable> String getArray(Set<T> set, boolean deep) {
        if(set.size() == 0) {
            return "[]";
        }
        String s = "[";

        for (T o:set) {
            s += o.toJSON(deep) + ",";
        }
        return s.substring(0, s.length() - 1) + "]";
    }

    public static <T extends JSONable> String getArray(List<T> list, boolean deep) {
        if(list.size() == 0) {
            return "[]";
        }
        String s = "[";

        for (int i = 0; i < list.size(); i++) {
            s += list.get(i).toJSON(deep) + ",";
        }
        return s.substring(0, s.length() - 1) + "]";
    }
}
