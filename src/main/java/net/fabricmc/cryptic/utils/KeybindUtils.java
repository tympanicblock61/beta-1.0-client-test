package net.fabricmc.cryptic.utils;

import net.fabricmc.loader.impl.util.StringUtil;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class KeybindUtils {
    static Map<String, String> replacements = new HashMap<>();

    public static HashSet<Integer> pressed = new HashSet<>();

    static {
        //HOME            = 0xC7; /* Home on arrow keypad */
        //public static final int KEY_UP              = 0xC8; /* UpArrow on arrow keypad */
        //public static final int KEY_PRIOR           = 0xC9; /* PgUp on arrow keypad */
        //public static final int KEY_LEFT            = 0xCB; /* LeftArrow on arrow keypad */
        //public static final int KEY_RIGHT           = 0xCD; /* RightArrow on arrow keypad */
        //public static final int KEY_END             = 0xCF; /* End on arrow keypad */
        //public static final int KEY_DOWN            = 0xD0; /* DownArrow on arrow keypad */
        //public static final int KEY_NEXT            = 0xD1; /* PgDn on arrow keypad */
        //public static final int KEY_INSERT          = 0xD2; /* Insert on arrow keypad */
        //public static final int KEY_DELETE
        replacements.put("HOME", "Keypad Home");
        replacements.put("UP", "Keypad Up");
        replacements.put("PRIOR", "Keypad Prior");
        replacements.put("LEFT", "Keypad Left");
        replacements.put("RIGHT", "Keypad Right");
        replacements.put("END", "Keypad End");
        replacements.put("DOWN", "Keypad Down");
        replacements.put("NEXT", "Keypad Next");
        replacements.put("INSERT", "Keypad Insert");
        replacements.put("DELETE", "Keypad Delete");
        replacements.put("ESCAPE", "Esc");
        replacements.put("LOCK", " Lock");
        replacements.put("NUMPAD", "Numpad ");
        replacements.put("SCROLL", "Scroll Lock");
        replacements.put("R", "Right ");
        replacements.put("RMENU", "Right Alt");
    }
    public static Map<Integer, String> keyNameCache = new HashMap<>();
    public static String getKeyName(int key) {
        String keyName = Keyboard.getKeyName(key);
        System.out.println(keyName);
        if (keyName != null) return keyName;
        else if (keyNameCache.containsKey(key)) return keyNameCache.get(key);
        else {
            Field[] fields = Keyboard.class.getDeclaredFields();
            try {
                for ( Field field : fields ) {
                    if ( Modifier.isStatic(field.getModifiers())
                            && Modifier.isPublic(field.getModifiers())
                            && Modifier.isFinal(field.getModifiers())
                            && field.getType().equals(int.class)
                            && field.getName().startsWith("KEY_")
                            && !field.getName().endsWith("WIN") ) { /* Don't use deprecated names */

                        int val = field.getInt(null);
                        if (val == key) {
                            String name;
                            name = field.getName().substring(4);

                            for (Map.Entry<String, String> entry: replacements.entrySet()) {
                                boolean found = false;
                                if (name.equals(entry.getKey()) || name.contains(entry.getKey())) {
                                    List<String> splits = splitAt(name, entry.getKey());
                                    System.out.println(splits);
                                    for (String part : splits) {
                                        if (part.equals(entry.getKey())) {
                                            splits.set(splits.indexOf(entry.getKey()), entry.getValue());
                                            splits.set(0, StringUtil.capitalize(splits.get(0).toLowerCase()));
                                            splits.set(1, StringUtil.capitalize(splits.get(0).toLowerCase()));
                                            name = String.join("", splits);
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                                if (found) break;
                            }

                            keyNameCache.put(key, name);
                            return name;
                        }
                    }

                }
            } catch (Exception ignored) {}
            return "Unknown";
        }
    }

    public static List<String> splitAt(String toSplit, String find) {
        List<String> parts = new ArrayList<>(2);
        int index = toSplit.indexOf(find);
        if (index != -1) {
            String firstPart = toSplit.substring(0, index);
            String secondPart = toSplit.substring(index + find.length());
            parts.add(firstPart);
            parts.add(secondPart);
        }
        return parts;
    }
}
