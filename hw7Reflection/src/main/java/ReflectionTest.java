import java.lang.reflect.InvocationTargetException;

public class ReflectionTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestHeader.start(TestDoSomething.class);
    }
}
