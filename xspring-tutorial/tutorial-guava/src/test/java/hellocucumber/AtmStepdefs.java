package hellocucumber;

import cucumber.api.PendingException;

/**
 * <p>Title: hellocucumber.AtmStepdefs</p>
 * <p>Description:</p>
 * <p>Copyright: Ruijie Co., Ltd. (c) 2018</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2018/7/19 上午11:31</p>
 */
public class AtmStepdefs {
    @cucumber.api.java.zh_cn.假如("^我的账户中有余额\"([^\"]*)\"元$")
    public void 我的账户中有余额元(String accountBalance) throws Throwable {
        System.out.println("Parameter[accountBalance]'s value is: " + accountBalance);
    }

    @cucumber.api.java.zh_cn.当("^我选择固定金额取款方式取出\"([^\"]*)\"元$")
    public void 我选择固定金额取款方式取出元(String withdrawAmount) throws Throwable {
        System.out.println("Parameter[withdrawAmount]'s value is: " + withdrawAmount);
    }

    @cucumber.api.java.zh_cn.那么("^我应该收到现金\"([^\"]*)\"元$")
    public void 我应该收到现金元(String receivedAmount) throws Throwable {
        System.out.println("Parameter[receivedAmount]'s value is: " + receivedAmount);
    }

    @cucumber.api.java.zh_cn.而且("^我账户的余额应该是\"([^\"]*)\"元$")
    public void 我账户的余额应该是元(String remainingBalance) throws Throwable {
        System.out.println("Parameter[remainingBalance]'s value is: " + remainingBalance);
    }
}
