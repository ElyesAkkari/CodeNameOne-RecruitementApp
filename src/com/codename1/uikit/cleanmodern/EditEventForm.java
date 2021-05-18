/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.Event;
import com.mobilePIDEV.services.ServiceEvent;

/**
 *
 * @author hazem
 */
public class EditEventForm extends BaseForm{
    public EditEventForm(Resources res, Event ev) {
        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Edit EVENT");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Image im1 = res.getImage("profile-background.jpg");//.scaled(250, 250);
        if (im1.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            im1 = im1.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(im1);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
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
        RadioButton event = RadioButton.createToggle("Event", barGroup);
        event.setUIID("SelectBar");
        RadioButton sponsor = RadioButton.createToggle("Sponsor", barGroup);
        sponsor.setUIID("SelectBar");
        

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, event, sponsor),
                FlowLayout.encloseBottom(arrow)
        ));

        event.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(event, arrow);
        });
        bindButtonSelection(event, arrow);
        bindButtonSelection(sponsor, arrow);
        event.addActionListener(l -> new EventForm(res).showBack());
        //sponsor.addActionListener(l -> new SponsorForm(res).showBack());
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        TextField nom = new TextField(ev.getNom(), "Nom", 15, TextField.ANY);
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);
        TextField type = new TextField(ev.getType(), "Type", 25, TextField.ANY);
        type.setUIID("TextFieldBlack");
        addStringValue("Type", type);
        TextField desc = new TextField(ev.getDescription(), "Description", 25, TextField.ANY);
        desc.setUIID("TextFieldBlack");
        addStringValue("Description", desc);
        Button submit = new Button("submit");
        add(submit);
        submit.addActionListener(l -> {
            if (nom.getText().isEmpty() || type.getText().isEmpty()|| desc.getText().isEmpty()) {
                ToastBar.showMessage("Fill all Text Areas", FontImage.MATERIAL_ERROR);
                //Dialog.show("Fill all Text Areas", "", "annuler","ok");
            } else {
                ev.setNom(nom.getText());
                ev.setType(type.getText());
                ev.setDescription(desc.getText());
                if (ServiceEvent.getInstance().editEvent(ev)) {
                    Dialog.show("Success", "Event Edited", new Command("OK"));
                    new EventForm(res).showBack();
                } else {
                    Dialog.show("ERROR", "Server error", new Command("OK"));
                }
            }
        });

    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer) {

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

    /*   private void addButton(Quiz q, Image img) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
//        Date date = new Date();
//        //----- days count
//        int days = (int) (Math.abs(date.getTime() - p.getAdded().getTime()) / (1000 * 60 * 60 * 24));
//        //!---

        //Image img = res.getImage("news-item-2.jpg");
        // Button image = new Button(img.fill(width, height));
        // image.setUIID("Label");
        // Container cnt = BorderLayout.west(image);
        //cnt.setLeadComponent(image);
        Label titre = new Label("Titre : " + q.getTitre(), "NewsTopLine2");
        Label owner = new Label("Created By : " + q.getOwner());
        Label nbrq = new Label("Question number : " + q.getNbr_quest());
        Container cnt = BorderLayout.west(
                BoxLayout.encloseY(
                        BoxLayout.encloseX(titre),
                         BoxLayout.encloseX(owner),
                         BoxLayout.encloseX(nbrq)
                ));
        add(cnt);

        //cnt.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

    private void addResult(Image img) {

    }
     */
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    private void addStringValue(String str, Component c) {
        add(BorderLayout.west(new Label(str, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));

        //add(createLineSeparator(0x33fec3));
    }
}
