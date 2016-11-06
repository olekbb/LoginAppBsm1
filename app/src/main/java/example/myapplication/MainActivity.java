package example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


public class MainActivity extends AppCompatActivity {
    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.
                BouncyCastleProvider(), 1);
    }
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
    public byte[] password;
    public boolean messageChanged = false;
    public boolean passwordChanged = false;

    static private SecretKey key;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = "login";

        key = null;
        if (!passwordChanged){
            password = new byte[256];
            try {
                password = encrypt("pwd");
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    boolean passwordValid = false;
                    try {
                        passwordValid = loginText.getText().toString().equals(login) && passwordText.getText().toString().equals(decrypt(password));
                        System.out.println(decrypt(password));
                        System.out.println(password);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (passwordValid){
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
                                    try {
                                        password = encrypt(editPasswordText.getText().toString());
                                        passwordChanged = true;
                                        textView.setText("Password changed");
                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

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
    public static SecretKey generateAESKey(int keysize)
            throws NoSuchAlgorithmException {
        final SecureRandom random = new SecureRandom();
        final KeyGenerator generator = KeyGenerator.
                getInstance("AES");
        generator.init(keysize, random);
        return generator.generateKey();
    }

    private static IvParameterSpec iv;
    public static IvParameterSpec getIV() {
        if (iv == null) {
            byte[] ivByteArray = new byte[16];
            // populate the array with random bytes
            new SecureRandom().nextBytes(ivByteArray);
            iv = new IvParameterSpec(ivByteArray);
        }
        return iv;
    }
    public static byte[] encrypt(String plainText)
            throws GeneralSecurityException, IOException {
        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIV());
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }
    public static SecretKey getKey() throws NoSuchAlgorithmException
    {
        if (key == null) {
            key = generateAESKey(256);
        }
        return key;
    }
    public static String decrypt(byte[] cipherText)
            throws GeneralSecurityException, IOException {
        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, getKey(),getIV());
        return cipher.doFinal(cipherText).toString();
    }
}
