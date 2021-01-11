package com.example.easycontact;

import com.example.easycontact.Activities.LoginActivity;
import com.example.easycontact.Activities.PostDetailActivity;
import com.example.easycontact.Activities.RegisterUser;
import com.example.easycontact.Activities.user;
import com.example.easycontact.Models.Post;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void CheckFieldAddpost() {

        PostDetailActivity myObjectUnderTest = new PostDetailActivity();
        String result = myObjectUnderTest.ValidateFieldString("ds","dssd","dsfs");

        assertThat(result, is("valid field post will add with this parameter is input"));

    }
    @Test
    public void CheckListIsEmty(){
        List<Post> listPost = new ArrayList<Post>();
        Post post  = new Post("Hello ","post","my image","my id ","userphoto");
        Post post1  = new Post("Hello ","post","my image","my id ","userphoto");
        Post post2  = new Post("Hello ","post","my image","my id ","userphoto");
        Post post3  = new Post("Hello ","post","my image","my id ","userphoto");
        listPost.add(post);
        listPost.add(post1);
        listPost.add(post2);
        listPost.add(post3);

        PostDetailActivity myObjectUnderTest = new PostDetailActivity();
        String result = myObjectUnderTest.VerifyList(listPost);
        assertThat(result, is("list not empty"));

    }
    @Test
    public void CheckEmailExistforLogin(){
        List<user> listUser = new ArrayList<user>();
        user u1 =new user("13262801","ilyeshrizi@gmail.com","ilyes","hrizi","true","online","my photo");
        user u2 =new user("12345678","wissalmahdi@gmail.com","wissal","mahdi","true","online","my photo");
        user u3 =new user("13262801","karimbouzid@gmail.com","karim","bouzid","true","online","my photo");
        listUser.add(u1);
        listUser.add(u2);
        listUser.add(u3);
        LoginActivity loginActivity = new LoginActivity();
        String result = loginActivity.checkUserExist(listUser,"ilyeshrizi@gmail.com");
        assertThat(result,is("email exist you can login"));
    }
    @Test
    public void CheckEmailExistforRegister(){
        List<user> listUser = new ArrayList<user>();
        user u1 =new user("13262801","ilyeshrizi@gmail.com","ilyes","hrizi","true","online","my photo");
        user u2 =new user("12345678","wissalmahdi@gmail.com","wissal","mahdi","true","online","my photo");
        user u3 =new user("13262801","karimbouzid@gmail.com","karim","bouzid","true","online","my photo");
        listUser.add(u1);
        listUser.add(u2);
        listUser.add(u3);
        RegisterUser registerUser = new RegisterUser();
        String result = registerUser.checkUserExist(listUser,"ilyeschrizi@gmail.com");
        assertThat(result,is("email not exist register complete"));
    }
}