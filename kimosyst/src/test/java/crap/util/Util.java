/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author JOVIR
 */
public class Util {

    public static void main(String[] args) {
        Map map = new HashMap();
        ArrayList list = new ArrayList();
        list.add("saf");
        list.add("asdfa");

        map.put("s", list);
        map.put("d", list);

        Set keys = map.keySet();

        System.out.println(new Util().mapOfList2Json(map));
    }

    public String mapOfList2Json(Map map) {
        String result = "[";
        Set keys = map.keySet();
        Object[] akeys = keys.toArray();
        for (int i = 0; i < akeys.length; i++) {
            if (i < keys.size() - 1) {
                result += "{ \"" + akeys[i] + "\" : " + list2Json(map.get(akeys[i])) + "},";
            } else {
                result += "{ \"" + akeys[i] + "\" : " + list2Json(map.get(akeys[i])) + "}";
            }
        }
        result += "]";
        return result;
    }

    public String list2Json(Object obj) {
        String result = "[";
        try {
            ArrayList list = (ArrayList) obj;
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    result += "\"" + list.get(i) + "\",";
                } else {
                    result += "\"" + list.get(i) + "\"";
                }
            }
        } catch (Exception e) {
            result += "\"" + obj + "\"";
        }
        result += "]";
        return result;

    }

}
