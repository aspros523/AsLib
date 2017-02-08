package aspros.app.aslibrary.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;

public class TUtil
{
    public static <T> T getT(Object o, int i)
    {
        try
        {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassCastException e)
        {
//            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className)
    {
        if(StringUtils.isEmpty(className))
        {
            return null;
        }
        try
        {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
