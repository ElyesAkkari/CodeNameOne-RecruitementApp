/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.NewsfeedForm;
import com.codename1.uikit.cleanmodern.afficheroffre;
import com.mobilePIDEV.entites.Utilisateur;
import com.mobilePIDEV.utils.Statics;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Ahmed
 */
public class ServiceUtilisateur {
    public ArrayList<Utilisateur> users;
    
    private ConnectionRequest req;
    
    String json;
    
    public static ServiceUtilisateur instance=null; 

    private ServiceUtilisateur() {
        req = new ConnectionRequest();
    }
    
    public static ServiceUtilisateur getInstance(){
        if(instance == null){
            instance = new ServiceUtilisateur();
        }
        return instance;
        
    }
    
    
    
    // Affichage 
//    public ArrayList<Utilisateur>affichageUtilisateur(){
//        ArrayList<Utilisateur> user = new ArrayList<>();
//                String url = Statics.BASE_URL+"/affichageUtilisateur";
//       // req = new ConnectionRequest(url, false);
//        req.setUrl(url);
//        
//        req.addResponseListener(new ActionListener<NetworkEvent>(){
//              @Override
//              public void actionPerformed(NetworkEvent evt){
//               //   throw new UnsupportedOperationException("erreur");
//               JSONParser jsonp;
//               jsonp = new JSONParser();
//               try{
//                   Map<String,Object> maputilisateur = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//                   
//                   List<Map<String,Object>> list = (List<Map<String,Object>>) maputilisateur.get("root");
//                   
//                   for(Map<String, Object> obj : list){
//                       Utilisateur u = new Utilisateur();
//                       
//                       float id = Float.parseFloat(obj.get("id").toString());
//                       String  username = obj.get("username").toString();
//                       String email  = obj.get("email").toString();
//                       
//                       u.setId((int)id);
//                       u.setUsername(username);
//                       u.setEmail(email);
//                       
//                       user.add(u);
//                   }
//               }  catch (IOException ex) {
//                   
//                   System.out.println(ex.getMessage());
//                  }
//              }
//        });
//
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        
//        return user;
//    }
    
    
    
    
    // Sign up 
    
    public void signup(TextField username,TextField prenom,TextField email,TextField password,TextField confpassword, Resources rs ){
                String url = Statics.BASE_URL+"signup?username="+username.getText().toString()+"&prenom="+prenom.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString();
                req.setUrl(url);
            
            
            if(username.getText().equals(" ") && prenom.getText().equals(" ") && email.getText().equals(" ") && password.getText().equals(" ") ){
                Dialog.show("Erreur","Veuilllez remplir les champs","OK",null);
            }
            
            req.addResponseListener((e)->{
                byte[]data = (byte[]) e.getMetaData();
              String  responseData = new String(data);
                System.out.println("data ===>"+responseData);
             
            });
            
            NetworkManager.getInstance().addToQueueAndWait(req);

    }
    
    
    // Sign in
    
    public void signin(TextField email,TextField password, Resources rs ){
        
        String url = Statics.BASE_URL+"signin?email="+email.getText().toString()+"&password="+password.getText().toString();
       req = new ConnectionRequest(url, false);
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData())+ "";
            
            if(email.getText().equals("") || password.getText().equals("")){
                Dialog.show("Erreur","Veuilllez remplir les champs","OK",null);
            }
            try{
                if(json.equals("failed")){
            Dialog.show("Echec d'authentification"," Email ou password incorrect ","OK",null);

                }
                else{
                    System.out.println("data ==>"+json);
                    Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    
                    // Session 
                    
                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int)id);
                    
                    SessionManager.setUsername(user.get("username").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setRole(user.get("role").toString());
                    
                    if(user.get("image_name") != null){
                        SessionManager.setPhoto(user.get("image_name").toString());
                    }
                    System.out.println("current user ====>"+SessionManager.getEmail()+" ,"+SessionManager.getUsername()+" ,"+SessionManager.getPassowrd()+"."+SessionManager.getRole());
                    if(user.size() > 0){
                        //new NewsfeedForm(rs).show();
                        new afficheroffre(rs).show();
                    }
                }
            }catch(Exception ex){
                System.out.println(ex);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
    
    public String getPasswordByEmail(String email , Resources rs ){
        String url = Statics.BASE_URL+"getPasswordByEmail?email="+email;
        req = new ConnectionRequest(url, false);
        req.setUrl(url);

        req.addResponseListener(e ->{
            JSONParser j = new JSONParser();
            
             json = new String(req.getResponseData())+ "";
            
            try{
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                
            }catch(Exception ex){
                System.out.println(ex);
            }
        });
           NetworkManager.getInstance().addToQueueAndWait(req);

        return json;
    }
    
    
    public void facebookLogin(Resources rs ) {
        //use your own facebook app identifiers here   
        //These are used for the Oauth2 web login process on the Simulator.
        String clientId = "1138535139993149";
        String redirectURI = "http://127.0.0.1/"; 
        String clientSecret = "b45ae6996de857b2c215d8d43c9a3c73";
        Login fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        //Sets a LoginCallback listener
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
                System.out.println("Login Failed");
               Storage.getInstance().writeObject("token", "");
            }

            @Override
            public void loginSuccessful() {
                System.out.println("Login Successful");
                String token = fb.getAccessToken().getToken();
                Storage.getInstance().writeObject("token", token);
            }
            
        });
        //trigger the login if not already logged in
        if(!fb.isUserLoggedIn()){
            fb.doLogin();
        }else{
            //get the token and now you can query the facebook API
            String token = fb.getAccessToken().getToken();
            Storage.getInstance().writeObject("token", token);
        }
    }
    
    
    
}
