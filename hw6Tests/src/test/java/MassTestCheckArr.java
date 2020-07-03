import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MassTestCheckArr {
    int[] arr;
    boolean checkTrue;
    CheckArrOnOneFour checkArrOnOneFour;

    public MassTestCheckArr(int[] arr, boolean checkTrue) {
        this.arr = arr;
        this.checkTrue = checkTrue;
    }

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 1, 1, 4, 1, 1}, true},
                {new int[]{1, 1, 1, 1, 1, 1}, false},
                {new int[]{4, 4, 4, 4}, false}
        });
    }

    @Before
    public void init() {
        checkArrOnOneFour = new CheckArrOnOneFour();
    }

    @Test
    public void test() {
        Assert.assertEquals(checkArrOnOneFour.checkArr(arr), checkTrue);
    }
}
