package example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText loginText;
    private EditText passwordText;
    private EditText messageText;
    private Button loginButton;
    private Button editButton;
    private TextView textView;
    private Button passwordButton;
    private Button logoutButton;
    private EditText editPasswordText;

    public String message;
    public String login;
    public String password;
    public boolean messageChanged = false;
    public boolean passwordChanged = false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = "login";

        if (!passwordChanged){
            password = "pwd";
        }
        if (!messageChanged){
            message = "Hello";
        }

        loginText = (EditText)findViewById(R.id.login_text);
        passwordText = (EditText)findViewById(R.id.password_text);
        loginButton = (Button)findViewById(R.id.login_button);
        editButton = (Button)findViewById(R.id.edit_message_button);
        messageText = (EditText) findViewById(R.id.edit_message_text);
        passwordButton = (Button)findViewById(R.id.change_password_button);
        editPasswordText = (EditText) findViewById(R.id.change_password_text);
        textView = (TextView)findViewById(R.id.text_view);
        logoutButton = (Button)findViewById(R.id.logout_button);

        loggedOut();


        if (loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (loginText.getText().toString().equals(login) && passwordText.getText().toString().equals(password)){
                        textView.setVisibility(TextView.VISIBLE);
                        loginText.setVisibility(EditText.GONE);
                        passwordText.setVisibility(EditText.GONE);
                        loginButton.setVisibility(Button.GONE);
                        editButton.setVisibility(Button.VISIBLE);
                        messageText.setVisibility(EditText.VISIBLE);
                        passwordButton.setVisibility(Button.VISIBLE);
                        editPasswordText.setVisibility(EditText.VISIBLE);
                        logoutButton.setVisibility(Button.VISIBLE);
                        if (editButton != null){
                            editButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v){
                                    message = messageText.getText().toString();
                                    textView.setText(message);
                                    messageChanged = true;
                                }
                            });
                        }
                        if (passwordButton != null){
                            passwordButton.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    password = editPasswordText.getText().toString();
                                    passwordChanged = true;
                                    textView.setText("Password changed");
                                }
                            });
                        }
                        if (logoutButton != null){
                            logoutButton.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    loggedOut();
                                }
                            });
                        }

                    }
                }
            });
        }



    }

    public void loggedOut(){
        if (logoutButton != null){
            logoutButton.setVisibility(Button.GONE);
        }

        if (textView != null) {
            textView.setText(message);
            textView.setVisibility(TextView.GONE);
        }

        if (messageText != null) {
            messageText.setVisibility(EditText.GONE);
        }

        if (editButton != null){
            editButton.setVisibility(Button.GONE);
        }

        if (editPasswordText != null){
            editPasswordText.setVisibility(EditText.GONE);
        }

        if (passwordButton != null){
            passwordButton.setVisibility(Button.GONE);
        }

        if (loginText != null){
            loginText.setVisibility(EditText.VISIBLE);
        }
        if (passwordText != null) {
            passwordText.setVisibility(EditText.VISIBLE);

        }
        if (loginButton != null){
            loginButton.setVisibility(Button.VISIBLE);
        }
    }
}
