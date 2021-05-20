/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.AccessToken;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.NewsfeedForm;
import com.codename1.uikit.cleanmodern.afficheroffre;
import com.mobilePIDEV.entites.Utilisateur;
import com.mobilePIDEV.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Ahmed
 */
public class ServiceUtilisateur {

    public ArrayList<Utilisateur> users;

    private ConnectionRequest req;

    private Login login;

    String json;

    public static ServiceUtilisateur instance = null;

    private ServiceUtilisateur() {
        req = new ConnectionRequest();
    }

    public static ServiceUtilisateur getInstance() {
        if (instance == null) {
            instance = new ServiceUtilisateur();
        }
        return instance;

    }

    // Sign up 
    public void signup(TextField username, TextField prenom, TextField email, TextField password, TextField confpassword, Resources rs) {
        String url = Statics.BASE_URL + "signup?username=" + username.getText().toString() + "&prenom=" + prenom.getText().toString() + "&email=" + email.getText().toString() + "&password=" + password.getText().toString();
        req.setUrl(url);

        if (username.getText().equals(" ") && prenom.getText().equals(" ") && email.getText().equals(" ") && password.getText().equals(" ")) {
            Dialog.show("Erreur", "Veuilllez remplir les champs", "OK", null);
        }

        req.addResponseListener((e) -> {
            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);
            System.out.println("data ===>" + responseData);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    // Sign in
    public void signin(TextField email, TextField password, Resources rs) {

        String url = Statics.BASE_URL + "signin?email=" + email.getText().toString() + "&password=" + password.getText().toString();
        req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

            if (email.getText().equals("") || password.getText().equals("")) {
                Dialog.show("Erreur", "Veuilllez remplir les champs", "OK", null);
            }
            try {
                if (json.equals("failed")) {
                    Dialog.show("Echec d'authentification", " Email ou password incorrect ", "OK", null);
                } else if (json.equals("password is failed")) {
                    Dialog.show("Echec d'authentification", " Email ou password incorrect ", "OK", null);

                } else {
                    System.out.println("data ==>" + json);
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    // Session 
                    float id = Float.parseFloat(user.get("id").toString());

                    SessionManager.setId((int) id);

                    SessionManager.setUsername(user.get("username").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setPrenom(user.get("prenom").toString());
                    SessionManager.setRole(user.get("role").toString());

                    if (user.get("cin") != null) {
                        float cin = Float.parseFloat(user.get("cin").toString());

                        SessionManager.setCin((int) cin);
                    }
                    if (user.get("numTel") != null) {
                        float num = Float.parseFloat(user.get("numTel").toString());

                        SessionManager.setNum((int) num);

                    }
                    if (user.get("image_name") != null) {
                        SessionManager.setPhoto(user.get("image_name").toString());
                    }
                    
                    System.out.println("current user ====>" + SessionManager.getEmail() + " ," + SessionManager.getUsername() + " ," + SessionManager.getPassowrd() + "," + SessionManager.getCin() + "," + SessionManager.getNum());
                    if (user.size() > 0) {
                        //new NewsfeedForm(rs).show();
                        new afficheroffre(rs).show();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public String getPasswordByEmail(String email, Resources rs) {
        String url = Statics.BASE_URL + "getPasswordByEmail?email=" + email;
        req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener(e -> {
            JSONParser j = new JSONParser();

            json = new String(req.getResponseData()) + "";

            try {
                Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return json;
    }

    public void modifieProfil(TextField username, TextField prenom, TextField email, TextField cin, TextField numtel, Resources rs, int id) {

        String url = Statics.BASE_URL + "modiferProfil?id=" + id + "&username=" + username.getText().toString() + "&prenom=" + prenom.getText().toString() + "&email=" + email.getText().toString() + "&cin=" + cin.getText().toString() + "&numtel=" + numtel.getText().toString();
        req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener((e) -> {
            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);
            System.out.println("data ===>" + responseData);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public void signinGoogle() {

        String clientId = "160011012371-3q58an2eft1gffespbnrssjqbdivquf2.apps.googleusercontent.com";
        String redirectURI = "http://127.0.0.1/";
        String clientSecret = "LH3rqneZBZRdTnXShTGPDrCw";

        if (clientSecret.length() == 0) {
            System.err.println("create your own google project follow the guide here:");
            System.err.println("http://www.codenameone.com/google-login.html");
            return;
        }
        Login gc = GoogleConnect.getInstance();
        gc.setClientId(clientId);
        gc.setRedirectURI(redirectURI);
        gc.setClientSecret(clientSecret);
        login = gc;
        gc.setCallback(new LoginListener(LoginListener.GOOGLE));
        if (!gc.isUserLoggedIn()) {
            //gc.doLogin();
        } else {
            showGoogleUser(gc.getAccessToken().getToken());
        }

    }

    public class LoginListener extends LoginCallback {

        public static final int GOOGLE = 1;

        private int loginType;

        public LoginListener(int loginType) {
            this.loginType = loginType;
        }

        public void loginSuccessful() {

            try {
                AccessToken token = login.getAccessToken();

                if (loginType == GOOGLE) {
                    showGoogleUser(token.getToken());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void loginFailed(String errorMessage) {
            Dialog.show("Login Failed", errorMessage, "Ok", null);
        }
    }

    private void showGoogleUser(String token) {
        ConnectionRequest req = new ConnectionRequest();
        req.addRequestHeader("Authorization", "Bearer " + token);
        req.setUrl("https://www.googleapis.com/plus/v1/people/me");
        req.setPost(false);
        InfiniteProgress ip = new InfiniteProgress();
        Dialog d = ip.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req);
        d.dispose();
        byte[] data = req.getResponseData();
        JSONParser parser = new JSONParser();
        Map map = null;
        try {
            map = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String name = (String) map.get("displayName");
        Map im = (Map) map.get("image");
        String url = (String) im.get("url");
        //    Form userForm = new UserForm(name, (EncodedImage) theme.getImage("user.png"), url);
        //   userForm.show();
    }

    public void ChangePassword(TextField newpassword, int id, Resources rs) {

        String url = Statics.BASE_URL + "modifierpassword?id=" + id + "&password=" + newpassword;
        //  req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener((e) -> {
            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);
            System.out.println("data ===>" + responseData);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

//    public void newPassword(TextField password, String email, Resources rs) {
//
//        String url = Statics.BASE_URL + "newpassword?email=" + email + "&password=" + password;
//        req.setUrl(url);
//
//        req.addResponseListener((e) -> {
//            byte[] data = (byte[]) e.getMetaData();
//            String responseData = new String(data);
//            System.out.println("data ===>" + responseData);
//
//        });
//
//        NetworkManager.getInstance().addToQueueAndWait(req);
//
//    }
    public void newPassword(String password , String email , Resources rs){
            
         String url = Statics.BASE_URL+"newpassword?email="+email+"&password="+password;
               req.setUrl(url);
               
               
               req.addResponseListener((e)->{
                byte[]data = (byte[]) e.getMetaData();
              String  responseData = new String(data);
                System.out.println("data ===>"+responseData);
             
            });
            
        NetworkManager.getInstance().addToQueueAndWait(req);


        }
}
