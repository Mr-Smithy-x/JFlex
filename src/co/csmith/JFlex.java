package co.csmith;

import co.csmith.annotations.CUpdate;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Div;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by cj on 5/26/16.
 */
public class JFlex {


    public static <T, F> Object fromJson(final T updateClass, final String json) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<T> update = (Class<T>) updateClass;
        Object clzz = update.newInstance();
        Field[] fields = update.getDeclaredFields();
        JSONObject jsonObject = new JSONObject(json);
        for (Field f : fields) {
            if (!f.isAccessible()) f.setAccessible(true);
            if (f.getGenericType().getTypeName().equals(double.class.getTypeName()) || f.getGenericType().getTypeName().equals(Double.class.getTypeName())) {
                f.set(clzz, getFloat(f, jsonObject, f.getAnnotation(CUpdate.class)));
            } else if (f.getGenericType().getTypeName().equals(float.class.getTypeName()) || f.getGenericType().getTypeName().equals(Float.class.getTypeName())) {
                f.set(clzz, getFloat(f, jsonObject, f.getAnnotation(CUpdate.class)));
            } else if (f.getGenericType().getTypeName().equals(String.class.getTypeName()) || f.getGenericType().getTypeName().equals(CharSequence.class.getTypeName())) {
                f.set(clzz, getString(f, jsonObject, f.getAnnotation(CUpdate.class)));
            } else if (f.getGenericType().getTypeName().equals(Integer.class.getTypeName()) || f.getGenericType().getTypeName().equals(int.class.getTypeName())) {
                f.setInt(clzz, getInteger(f, jsonObject, f.getAnnotation(CUpdate.class)));
            } else if (f.getGenericType().getTypeName().equals(boolean.class.getTypeName()) || f.getGenericType().getTypeName().equals(Boolean.class.getTypeName())) {
                f.setBoolean(clzz, getBoolean(f, jsonObject, f.getAnnotation(CUpdate.class)));
            }
        }

        return clzz;
    }


    private static double getDouble(Field f, JSONObject jsonObject, CUpdate cUpdate) {
        if (f.getGenericType().getTypeName().equals(Double.class.getTypeName()) || f.getGenericType().getTypeName().equals(double.class.getTypeName())) {
            if (cUpdate != null) {
                return jsonObject.getDouble(cUpdate.value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getDouble(f.getName());
            }
        }
        return 0f;
    }

    private static float getFloat(Field f, JSONObject jsonObject, CUpdate cUpdate) {
        if (f.getGenericType().getTypeName().equals(float.class.getTypeName()) || f.getGenericType().getTypeName().equals(double.class.getTypeName())) {
            if (cUpdate != null) {
                return new Float(jsonObject.getDouble(cUpdate.value()));
            } else if (jsonObject.has(f.getName())) {
                return new Float(jsonObject.getDouble(f.getName()));
            }
        }
        return 0f;
    }


    private static String getString(Field f, JSONObject jsonObject, CUpdate cUpdate) throws IllegalAccessException {
        if (f.getGenericType().getTypeName().equals(String.class.getTypeName()) || f.getGenericType().getTypeName().equals(CharSequence.class.getTypeName())) {
            if (cUpdate != null) {
                return jsonObject.getString(cUpdate.value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getString(f.getName());
            }
        }
        return "";
    }

    private static boolean getBoolean(Field f, JSONObject jsonObject, CUpdate cUpdate) throws IllegalAccessException {
        if (f.getGenericType().getTypeName().equals(boolean.class.getTypeName()) || f.getGenericType().getTypeName().equals(Boolean.class.getTypeName())) {
            if (cUpdate != null) {
                return jsonObject.getBoolean(cUpdate.value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getBoolean(f.getName());
            }
        }
        return false;
    }

    private static int getInteger(Field f, JSONObject jsonObject, CUpdate cUpdate) {
        if (f.getGenericType().getTypeName().equals(int.class.getTypeName()) || f.getGenericType().getTypeName().equals(Integer.class.getTypeName())) {
            if (cUpdate != null) {
                return jsonObject.getInt(cUpdate.value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getInt(f.getName());
            }
        }
        return 0;
    }


    private static String getString(Field f, JSONObject jsonObject) throws IllegalAccessException {
        if (f.getGenericType().getTypeName().equals(String.class.getTypeName()) || f.getGenericType().getTypeName().equals(CharSequence.class.getTypeName())) {
            if (f.getAnnotation(CUpdate.class) != null) {
                System.out.println(f.getAnnotation(CUpdate.class).value());
                return jsonObject.getString(f.getAnnotation(CUpdate.class).value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getString(f.getName());
            } else {
                System.out.println(f.getName());
            }
        }
        return "";
    }

    private static boolean getBoolean(Field f, JSONObject jsonObject) throws IllegalAccessException {
        if (f.getGenericType().getTypeName().equals(boolean.class.getTypeName()) || f.getGenericType().getTypeName().equals(Boolean.class.getTypeName())) {
            if (f.getAnnotation(CUpdate.class) != null) {
                return jsonObject.getBoolean(f.getAnnotation(CUpdate.class).value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getBoolean(f.getName());
            }
        }
        return false;
    }

    private static int getInteger(Field f, JSONObject jsonObject) {
        if (f.getGenericType().getTypeName().equals(int.class.getTypeName()) || f.getGenericType().getTypeName().equals(Integer.class.getTypeName())) {
            if (f.getAnnotation(CUpdate.class) != null) {
                return jsonObject.getInt(f.getAnnotation(CUpdate.class).value());
            } else if (jsonObject.has(f.getName())) {
                return jsonObject.getInt(f.getName());
            }
        }
        return 0;
    }

}
