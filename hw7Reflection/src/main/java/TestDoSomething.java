public class TestDoSomething {
    private static DoSomething doSomething;

    @BeforeSuite
    public static void before() {
        doSomething = new DoSomething();
        System.out.println("doSomething создан");
    }

    @AfterSuite
    public static void after() {
        System.out.println("Тесты завершены");
    }

    @AnoTest(priority = 1)
    public static boolean test1() {
        return (doSomething.sum(2, 3) == 5);
    }

    @AnoTest(priority = 2)
    public static boolean test2() {
        return (doSomething.sum(2, 3) == 6);
    }
}
