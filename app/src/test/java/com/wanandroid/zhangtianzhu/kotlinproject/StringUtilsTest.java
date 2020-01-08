package com.wanandroid.zhangtianzhu.kotlinproject;

import com.wanandroid.zhangtianzhu.kotlinproject.utils.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

//RunWith指定该测试类使用某个运行器，Parameterized指定该测试类为测试数据的集合
@RunWith(Parameterized.class)
public class StringUtilsTest {
    private String mExpert;
    private String mActual;

    /**
     * 这里模拟了创建对象的过程，Juit会自动把@Parameters里面的数据集合，
     * 按照顺序依次放入构造方法中
     *
     * @param mExpert
     * @param mActual
     */
    public StringUtilsTest(String mExpert, String mActual) {
        this.mExpert = mExpert;
        this.mActual = mActual;
    }

    //指定测试类的测试数据集合，通过构造方法传入
    @Parameterized.Parameters
    public static Collection<Object[]> money() {
        return Arrays.asList(new Object[][]{
                {"880000", "88万"},
                {"660000", "66万"},
                {"220000", "22万"}
        });
    }

    @Before
    public void setUp() {
        System.out.println("测试开始");
    }

    @After
    public void tearDown() {
        System.out.println("测试结束");
    }

    @Test
    public void getNumFromFormatTest() {
        //断言当前值与预期值是否相等
        assertEquals(mExpert, StringUtils.getNumFromFormat(mActual));
    }
}
