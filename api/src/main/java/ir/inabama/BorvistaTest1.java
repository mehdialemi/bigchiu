package ir.inabama;

import java.util.*;

public class BorvistaTest1 {

    public static List<Integer> toList(Integer number) {

        List<Integer> list = new ArrayList<>();
        char[] arr = number.toString().toCharArray();
        for (int i = 0; i < arr.length; i++) {
            String chStr = Character.toString(arr[i]);
            list.add(Integer.parseInt(chStr));
        }

        return list;
    }

    public static boolean findAdjacent(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int next = i + 1;
            if (next >= list.size())
                return false;
            if (list.get(i) == list.get(next))
                return true;
        }
        return false;
    }

    public static boolean find = false;
    public static int challenge(int repeat, int number, List<Integer> list) {
        while (!find) {
            repeat ++;
            list.addAll(toList(number));
            for (Integer num : list) {
                int m = num * number;
                List<Integer> digits = toList(m);
                List<Integer> list2 = new ArrayList<>();
                list2.addAll(list);
                list2.addAll(digits);
                if (findAdjacent(list2)) {
                    find = true;
                    System.out.println(list2);
                    return repeat;
                }
            }

            for (Integer num : list) {
                int m = num * number;
                List<Integer> list2 = new ArrayList<>();
                list2.addAll(list);
                list2.addAll(toList(m));
                if (!find) {
                    return challenge(repeat , m, list2);
                }
            }
        }
        return repeat;
    }

    public static String findIntersection(String[] strArr) {
        String[] a1 = strArr[0].split(",");
        String[] a2 = strArr[1].split(",");

        int i1 = 0, i2 = 0;
        List<Integer> l = new ArrayList<>();
        while(i1 < a1.length && i2 < a2.length) {
            int n1 = Integer.parseInt(a1[i1].trim());
            int n2 = Integer.parseInt(a2[i2].trim());

            if (n1 == n2) {
                l.add(n1);
                i1 ++;
                i2 ++;
            } else if(n1 < n2) {
                i1 ++;
            } else {
                i2 ++;
            }
        }

        if (l.isEmpty())
            return "false";

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < l.size(); i++) {
            s.append(l.get(i));
            if (i < l.size() - 1) s.append(",");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        String r = findIntersection(new String[] {"1, 2, 3, 4, 5", "6, 7, 8, 9, 10"});
        System.out.println(r);
//        int challenge = challenge(0, 42, new ArrayList<>());
//        System.out.println(challenge);
    }
}
