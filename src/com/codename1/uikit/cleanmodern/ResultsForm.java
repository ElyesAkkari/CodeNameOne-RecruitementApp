/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.share.EmailShare;
import com.codename1.share.ShareService;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.services.ServiceParticipation;
import com.mobilePIDEV.services.SessionManager;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
//import java.util.concurrent.TimeUnit;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class ResultsForm extends BaseForm {

    ArrayList<Participation> participation;

    public ResultsForm(Resources res) {
        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Image im1 = res.getImage("d.png").scaled(250, 250);
        Image im2 = res.getImage("b.png").scaled(250, 250);

        System.out.println("height : " + res.getImage("dog.jpg").getHeight());
        addTab(swipe, im2, spacer2/*, "", "", ""*/);
        addTab(swipe, im1, spacer3/*, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11"*/);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Jobs", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Events", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Formations", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("Results", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));

        myFavorite.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(myFavorite, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        participation = ServiceParticipation.getInstance().getAllParts();
        for (Participation p : participation) {
            if (p.getUser_id() == SessionManager.getId())
            { addButton(res.getImage("result.jpg"), p, false, 26, 32, res);}

        }
//        addButton(res.getImage("news-item-2.jpg"), "Fusce ornare cursus masspretium tortor integer placera.", true, 15, 21);
//        addButton(res.getImage("news-item-3.jpg"), "Maecenas eu risus blanscelerisque massa non amcorpe.", false, 36, 15);
//        addButton(res.getImage("news-item-4.jpg"), "Pellentesque non lorem diam. Proin at ex sollicia.", false, 11, 9);
//    
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer/*, String likesStr, String commentsStr, String text*/) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
//        Label likes = new Label(likesStr);
//        Style heartStyle = new Style(likes.getUnselectedStyle());
//        heartStyle.setFgColor(0xff2d55);
//        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
//        likes.setIcon(heartImage);
//        likes.setTextPosition(RIGHT);

//        Label comments = new Label(commentsStr);
//        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        /*             new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                                         */spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Image img, Participation p, boolean liked, int likeCount, int commentCount, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Date date = new Date();
        //----- days count
        int days = (int) (Math.abs(date.getTime() - p.getAdded().getTime()) / (1000 * 60 * 60 * 24));
        //!---
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        String title;
        if (p.getQuiz() != null) {
            title = "Quiz : " + p.getQuiz().getTitre()
                    + "\n Note : " + p.getNote()
                    + "\n" + days + " days ago.";
            TextArea ta = new TextArea(title);
        } else {
            title = "Note : " + p.getNote()
                    + "\n" + days + " days ago.";
        }
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
        likes.setTextPosition(RIGHT);
        if (!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);
        }
        Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments)
                ));
        add(cnt);
        image.addActionListener(e -> {
            Dialog dlg = new Dialog();
            ShareButton sb = new ShareButton();
            sb.setText("Share Result");

            Image screenshot = Image.createImage(getWidth(), getHeight());
            revalidate();
            setVisible(true);
            paintComponent(screenshot.getGraphics(), true);

            String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
            } catch (IOException err) {
                Log.e(err);
            }
            Image fb_logo = res.getImage("fb.png");
            fb_logo = fb_logo.scaledHeight(85);
            ShareService fb = new ShareService("custom Facebook", fb_logo) {
                @Override
                public void share(String text) {
                    sharePost(title);
                }
                @Override
                public boolean canShareImage() {
                    return true;
                }
            };
            //sb.setImageToShare(imageFile, "image/png");
            sb.setTextToShare(title);
            sb.addShareService(fb);
            dlg.add(sb);
            Display.getInstance().createContact("Elyes", "Akkari", "+21656782825", "70112233", "+21656782825", "elyesakkari0@gmail.com");
            int h = Display.getInstance().getDisplayHeight();
            dlg.setDisposeWhenPointerOutOfBounds(true);
            dlg.show(h / 8 * 7, 0, 0, 0);
        });
    }

  

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
    public void sharePost (String str){
        Display.getInstance().execute("https://www.facebook.com/sharer/sharer.php"
                + "?u="+Util.encodeUrl("http://127.0.0.1:8000")
                + "&title=A+nice+question+about+Facebook"
                +"&p[images][0]="+Util.encodeUrl("https://upload.wikimedia.org/wikipedia/commons/b/b6/Logo_ESPRIT_-_Tunisie.png")
                + "&quote="+Util.encodeUrl("I've passed a Quiz with IMPACTEERS! \n"+str)
                + "&description=Apparently%2C+the+accepted+answer+is+not+correct.");
    }
}
