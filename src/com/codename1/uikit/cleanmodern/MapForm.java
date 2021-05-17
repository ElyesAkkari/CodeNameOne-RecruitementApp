/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
//import static com.mycompany.gui.AjoutForm.b;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author firas
 */
public class MapForm {
Form f = new Form();
public MapForm(Resources res) {
        
         MapContainer cnt = new MapContainer("AIzaSyCy-fMWerzvXcPCV0FDI07hW2DAzs_mnpY");

        Button btnMoveCamera = new Button("Notre Localisation ");
        btnMoveCamera.addActionListener(e->{
            cnt.setCameraPosition(new Coord(36.8992047, 10.1875152));
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        
    


        cnt.addTapListener(e->{
    
            
                cnt.clearMapLayers();
                cnt.addMarker(
                        EncodedImage.createFromImage(markerImg, false),
                        cnt.getCoordAtPosition(e.getX(), e.getY()),
                        ""+cnt.getCameraPosition().toString(),
                        "",
                        e3->{
                                ToastBar.showMessage("You clicked "+cnt.getName(), FontImage.MATERIAL_PLACE);
                        }
                );
             ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("http://maps.google.com/maps/api/geocode/json?latlng="+cnt.getCameraPosition().getLatitude()+","+cnt.getCameraPosition().getLongitude()+"&oe=utf8&sensor=false");
                     NetworkManager.getInstance().addToQueueAndWait(r);

            JSONParser jsonp = new JSONParser();
           try {
               java.util.Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(r.getResponseData()).toCharArray()));
                              System.out.println("roooooot:" +tasks.get("results"));
                              List<java.util.Map<String, Object>> list1 = (List<java.util.Map<String, Object>>)tasks.get("results");
                              java.util.Map<String, Object> list = (java.util.Map<String, Object>) list1.get(0);

                               List<java.util.Map<String, Object>> listf = (List<java.util.Map<String, Object>>) list.get("address_components");
String ch="";
                         for (java.util.Map<String, Object> obj : listf) {
                 ch=ch+obj.get("long_name").toString();
                         }
                       //
                     //     b.setAdresse(ch);

                        

           } catch (IOException ex) {
           }

            
        });
        Container root = new Container();
         f.setLayout(new BorderLayout());
         f.addComponent(BorderLayout.CENTER, cnt);
         f.addComponent(BorderLayout.SOUTH, btnMoveCamera);
f.show();
 

}

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}