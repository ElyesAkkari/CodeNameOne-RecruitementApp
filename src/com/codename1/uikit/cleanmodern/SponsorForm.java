/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.mobilePIDEV.entites.Sponsor;
import java.util.ArrayList;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.services.ServiceSponsor;
/**
 *
 * @author hazem
 */
public class SponsorForm extends BaseForm{
    Form current;
    ArrayList<Sponsor> sponsors;
    
    public SponsorForm(Resources res) {
        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Sponsor Management");
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
        RadioButton loc = RadioButton.createToggle("Trouvez-nous", barGroup);
        loc.setUIID("SelectBar");

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, event,sponsor, loc),
                FlowLayout.encloseBottom(arrow)
        ));

        event.addActionListener(l-> new EventForm(res).show());
        sponsor.setSelected(true);
        loc.addActionListener(l-> new MapForm(res).setF(current));
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(sponsor, arrow);
        });
        bindButtonSelection(event, arrow);
        bindButtonSelection(sponsor, arrow);
        bindButtonSelection(loc, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        sponsors = ServiceSponsor.getInstance().getAllSponsors();
        for (Sponsor e : sponsors) {
            addButton(e, res);
        }
        Button addSponsor = new Button("add sponsor");
        addSponsor.addActionListener(l -> {
            new AddSponsorForm(res).show();
        });
        this.add(addSponsor);

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
//        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
//        if (img.getHeight() < size) {
//            img = img.scaledHeight(size);
//        }
//        Label likes = new Label(likesStr);
//        Style heartStyle = new Style(likes.getUnselectedStyle());
//        heartStyle.setFgColor(0xff2d55);
//        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
//        likes.setIcon(heartImage);
//        likes.setTextPosition(RIGHT);

//        Label comments = new Label(commentsStr);
//        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
//        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
//            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
//        }
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

    private void addButton(Sponsor e, Resources res) {
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
        Label nom = new Label("Nom : " + e.getNom(), "NewsTopLine2");
        Label type = new Label("Type : " + e.getType());
        Label image = new Label("Image : " + e.getImage());
        Container cnt = BorderLayout.west(
                BoxLayout.encloseY(
                        BoxLayout.encloseX(nom),
                        BoxLayout.encloseX(type),
                        BoxLayout.encloseX(image)
                ));
        Button btn = new Button();
        cnt.addPointerPressedListener(l -> {
            Dialog dlg = new Dialog("Sponsor");
            Button edit = new Button("edit");
            Button delete = new Button("delete");

            delete.addActionListener(del -> {
                if (Dialog.show("Confirmation", "Delete \" " + e.getNom() + "\" ?", "ok", "cancel")) {

                    if (ServiceSponsor.getInstance().deleteSponsor(e)) {
                        Dialog.show("Success", "Sponsor Deleted", new Command("OK"));
                        new SponsorForm(res).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                }
            });

            edit.addActionListener(ed -> {
                new EditSponsorForm(res, e).show();
            });
            dlg.setLayout(BoxLayout.y());
            dlg.add(new SpanLabel("Nom : " + e.getNom() + "\ntype : " + e.getType(), "DialogBody"));
          

            dlg.addAll(edit, delete);
            // dlg.add(delete);
            int h = Display.getInstance().getDisplayHeight();
            dlg.setDisposeWhenPointerOutOfBounds(true);
            dlg.show(h / 11 * 7, 0, 0, 0);
        });
        add(cnt);

        //cnt.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

    private void addResult(Image img) {

    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
