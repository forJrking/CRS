//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sky.crs.util;

import android.text.TextUtils;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 */
public class EmptyChecker {
    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty() || c.size() == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        else if (object instanceof String) {
            return isEmpty((String) object);
        }
        else if (object instanceof Integer) {
            return ((int) object) == 0;
        }
        else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        }
        else if (object instanceof Map) {
            return ((Map) object).isEmpty();
        }
        else if (object instanceof Object[]) {
            return ((Object[]) ((Object[]) object)).length == 0;
        }
        else if (object instanceof Iterator) {
            return !((Iterator) object).hasNext();
        }
        else if (object instanceof Enumeration) {
            return !((Enumeration) object).hasMoreElements();
        }
        else {
            try {
                return Array.getLength(object) == 0;
            }
            catch (IllegalArgumentException var2) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(StringBuilder sb) {
        return sb == null || sb.length() == 0;
    }

    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(Map<?, ?> m) {
        return !isEmpty(m);
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(StringBuilder sb) {
        return !isEmpty(sb);
    }

    public static boolean isAnyEmpty(CharSequence... charSequences) {
        if (charSequences.length > 0) {
            for (CharSequence charSequence : charSequences) {
                if (charSequence == null || TextUtils.isEmpty(charSequence)) {
                    return true;
                }
            }
        }
        return false;
    }

    private EmptyChecker() {
    }

}
