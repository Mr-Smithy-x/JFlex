package co.csmith;
import co.csmith.annotations.CUpdate;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by C. Smith on 5/26/16. 9:21pm
 */
public class JFlex {

    public static <T> Object fromJson(final T updateClass, final String json) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<T> update = (Class<T>) updateClass;
        Object updateInstance = update.newInstance();
        JSONObject jsonObject = new JSONObject(json);
        for (Field declaredField : update.getDeclaredFields()) {
            if (!declaredField.isAccessible()) declaredField.setAccessible(true);
            if (declaredField.getGenericType().getTypeName().equals(double.class.getTypeName()) || declaredField.getGenericType().getTypeName().equals(Double.class.getTypeName())) {
                declaredField.set(updateInstance, getDouble(declaredField, jsonObject, declaredField.getAnnotation(CUpdate.class)));
            } else if (declaredField.getGenericType().getTypeName().equals(float.class.getTypeName()) || declaredField.getGenericType().getTypeName().equals(Float.class.getTypeName())) {
                declaredField.set(updateInstance, getFloat(declaredField, jsonObject, declaredField.getAnnotation(CUpdate.class)));
            } else if (declaredField.getGenericType().getTypeName().equals(String.class.getTypeName()) || declaredField.getGenericType().getTypeName().equals(CharSequence.class.getTypeName())) {
                declaredField.set(updateInstance, getString(declaredField, jsonObject, declaredField.getAnnotation(CUpdate.class)));
            } else if (declaredField.getGenericType().getTypeName().equals(Integer.class.getTypeName()) || declaredField.getGenericType().getTypeName().equals(int.class.getTypeName())) {
                declaredField.setInt(updateInstance, getInteger(declaredField, jsonObject, declaredField.getAnnotation(CUpdate.class)));
            } else if (declaredField.getGenericType().getTypeName().equals(boolean.class.getTypeName()) || declaredField.getGenericType().getTypeName().equals(Boolean.class.getTypeName())) {
                declaredField.setBoolean(updateInstance, getBoolean(declaredField, jsonObject, declaredField.getAnnotation(CUpdate.class)));
            }
        }
        return updateInstance;
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
                return (float) jsonObject.getDouble(cUpdate.value());
            } else if (jsonObject.has(f.getName())) {
                return (float) jsonObject.getDouble(f.getName());
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
}
