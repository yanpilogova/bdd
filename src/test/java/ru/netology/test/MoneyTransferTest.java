package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1600x900";
        open("http://localhost:9999/");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCards() {
        var transferAmount = 1000;
        var dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        var amount1 = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        var amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getSecondCard()), dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        var dashboardAfter = dashboardBefore
                .transferTo(getSecondCard())
                .transferFrom(transferAmount, getFirstCard());
        var balance1 = dashboardAfter.getCardBalance(getFirstCard());
        var balance2 = dashboardAfter.getCardBalance(getSecondCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCards() {
        var transferAmount = 1000;
        var dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        var amount1 = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        var amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        var dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        var balance1 = dashboardAfter.getCardBalance(getSecondCard());
        var balance2 = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);

    }

    @Test
    void shouldTransferFullMoneyFromSecondToFirstCards() {
        var transferAmount = 10000;
        var dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        var amount1 = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        var amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        var dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        var balance1 = dashboardAfter.getCardBalance(getSecondCard());
        var balance2 = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }

    @Test
    void shouldTransferReturnMoneyFromFirstToSecondCards() {
        var transferAmount = 10000;
        var dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        var amount1 = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        var amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getSecondCard()), dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        var dashboardAfter = dashboardBefore
                .transferTo(getSecondCard())
                .transferFrom(transferAmount, getFirstCard());
        var balance1 = dashboardAfter.getCardBalance(getFirstCard());
        var balance2 = dashboardAfter.getCardBalance(getSecondCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }

    @Test
    void shouldNotLoginWithOtherUser() {
        var loginPage = new LoginPage();
        loginPage.invalidLogin(getOtherAuthInfo());
    }


    @Test
    void shouldTransferMoneyFromSecondToFirstCardsUnderLimit() {
        var transferAmount = 100500;
        var dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        var amount1 = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        var amount2 = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        var dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transferFrom(transferAmount, getSecondCard());
        var balance1 = dashboardAfter.getCardBalance(getSecondCard());
        var balance2 = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(amount1, balance1);
        assertEquals(amount2, balance2);
    }
}
