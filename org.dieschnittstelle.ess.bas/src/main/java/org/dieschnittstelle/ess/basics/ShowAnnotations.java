package org.dieschnittstelle.ess.basics;


import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.DisplayAs;
import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;
import org.dieschnittstelle.ess.basics.reflection.ReflectedStockItemBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

    public static void main(String[] args) {
        // we initialise the collection
        StockItemCollection collection = new StockItemCollection(
                "stockitems_annotations.xml", new AnnotatedStockItemBuilder());
        // we load the contents into the collection
        collection.load();

        for (IStockItem consumable : collection.getStockItems()) {
            showAttributes(((StockItemProxyImpl) consumable).getProxiedObject());
        }

        // we initialise a consumer
        Consumer consumer = new Consumer();
        // ... and let them consume
        consumer.doShopping(collection.getStockItems());
    }

    private static void showAttributes(Object instance) {
        Class klass = instance.getClass();
        StringBuilder out_str = new StringBuilder("{" + klass.getSimpleName());
        try {
            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                String display_name = name;

                if(field.isAnnotationPresent(DisplayAs.class)){
                    display_name = field.getAnnotation(DisplayAs.class).value();
                }

                String getter_name = ReflectedStockItemBuilder.getAccessorNameForField("get", name);
                Method getter = klass.getDeclaredMethod(getter_name);
                Object val = getter.invoke(instance);
                out_str.append("; ").append(display_name).append(": ").append(val.toString());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // ignore that fuck
        }

        out_str.append("}");
        show(out_str.toString());
    }
}
