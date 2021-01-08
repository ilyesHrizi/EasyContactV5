package com.example.easycontact;

import com.example.easycontact.Activities.LoginActivity;
import com.example.easycontact.Activities.PostDetailActivity;
import com.example.easycontact.Activities.RegisterUser;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void CheckFieldLogin() {

        LoginActivity myObjectUnderTest = new LoginActivity();

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.ValidateFieldString("useruse54r@gmail.com","13262801");

        // ...then the result should be the expected one.
        assertThat(result, is("valid field for login"));
    }
    @Test
    public void CheckFieldRegister() {

        RegisterUser myObjectUnderTest = new RegisterUser();

        String result = myObjectUnderTest.ValidateFieldString("user","user","ilyeshrizi60@gmail.com","56654545");

        assertThat(result, is("valid field for register"));
    }
    @Test
    public void readStringFromContext_LocalizedString() {

        PostDetailActivity myObjectUnderTest = new PostDetailActivity();
        String result = myObjectUnderTest.ValidateFieldString("ds","dssd","dsfs");

        assertThat(result, is("valid field post will add with this parameter is input"));

    }
}