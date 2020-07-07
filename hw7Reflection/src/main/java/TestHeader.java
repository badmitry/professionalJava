import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestHeader {

    public static void start(Class testClass) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = testClass.getDeclaredMethods();
        int isAfter = 0;
        int isBefore = 0;
        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                isAfter++;
            }
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                isBefore++;
            }
        }
        if (isAfter > 1 || isBefore > 1) {
            throw new RuntimeException("Аннотаций AfterSuite и ВeforeSuite должно быть не более 1");
        }
        List<Method> methodsList = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(AnoTest.class)) {
                methodsList.add(m);
            }
        }

        Collections.sort(methodsList, new Comparator<Method>() {
            @Override
            public int compare(Method m1, Method m2) {
                if (m1.getAnnotation(AnoTest.class).priority() > m2.getAnnotation(AnoTest.class).priority()) {
                    return 1;
                } else if (m1.getAnnotation(AnoTest.class).priority() < m2.getAnnotation(AnoTest.class).priority()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                m.invoke(null);
            }
        }

        for (Method m : methodsList) {
            if ((boolean) m.invoke(null)) {
                System.out.println("тест " + m.getName() + " пройден успешно");
            } else {
                System.out.println("тест " + m.getName() + " не пройден");
            }
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                m.invoke(null);
            }
        }
//        for (Method m : methodsList) {
//            System.out.println(m.getAnnotation(AnoTest.class).priority());
//        }
    }
}
