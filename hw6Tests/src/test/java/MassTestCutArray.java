import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MassTestCutArray {
    private int[] arr1;
    private int[] arr2;
    private CutArray cutArray;

    public MassTestCutArray(int[] arr1, int[] arr2) {
        this.arr1 = arr1;
        this.arr2 = arr2;
    }

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4, 5, 6}, new int[]{5, 6}},
                {new int[]{1, 2, 3, 4, -5, 6}, new int[]{-5, 6}},
                {new int[]{3, 3, 5, 4}, new int[0]},
        });
    }

    @Before
    public void init() {
        cutArray = new CutArray();
    }

    @Test
    public void test() {
        Assert.assertArrayEquals(cutArray.cutArray(arr1), arr2);
    }

    @Test(expected = RuntimeException.class)
    public void testException() {
        cutArray.cutArray(new int[]{1, 2, 3});
    }
}
