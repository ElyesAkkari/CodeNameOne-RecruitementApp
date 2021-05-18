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
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
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
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.Answer;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.entites.Question;
import com.mobilePIDEV.entites.Quiz;
import com.mobilePIDEV.services.ServiceParticipation;
import com.mobilePIDEV.services.ServiceQuestion;
import com.mobilePIDEV.services.ServiceReponse;
import com.mobilePIDEV.services.SessionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.Timeline;

;

/**
 *
 * @author DELL
 */
public class QuizTestForm extends BaseForm {

    Quiz quiz;
    Resources r;
    Container blY = new Container(BoxLayout.y());

    public void setQuiz(Quiz q) {
        this.quiz = q;
    }
    Date currentTime = new Date();
    private Resources theme;
    private Font numbersFont;
    long lastRenderedTime = 0;
    Button getQuiz = new Button("start Quiz");
    Container cnt = new Container(BoxLayout.x());
    private Form current;
    private static final Integer STARTTIME = 300;
    private Timeline timeline;
    private int tot = 0;
    private Integer timeSeconds = 300;
    private Integer timeMinutes = 300 / 60;
    HashMap<Answer, CheckBox> radioMap = new HashMap<>();

    public QuizTestForm(Resources res, Quiz q) {

        super("ResultsForm", BoxLayout.y());
        setQuiz(q);
        r = res;
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("QUIZ : " + q.getTitre());
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
        addTab(swipe, im1, spacer3);

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
        /*---------------------------------------------
        ---------------------------------------------
        ---------------------------------------------
        ---------------------------------------------*/

        Container container = new Container(BoxLayout.y());
        
//        blY.add(timer);

        ArrayList<Question> questions = ServiceQuestion.getInstance().getAllQuestions();
        ArrayList<Answer> reponses = ServiceReponse.getInstance().getAllReponses();
        for (Question quest : questions) {
            if (quest.getQuiz_id() == q.getId()) {
                blY.add(new Label(quest.getBody(), "NewsTopLine2"));
                for (Answer ans : reponses) {
                    int i = 0;
                    cnt = new Container(BoxLayout.x());
                    if (ans.getQuest_id() == quest.getId()) {
                        // cnt.add(new CheckBox(ans.getReponse()));
                        CheckBox cb = new CheckBox();
                        cnt.add(BorderLayout.west(new Label(ans.getReponse(), "PaddedLabel"))
                                .add(BorderLayout.CENTER, cb));
                        radioMap.put(ans, cb);

                        if (ans.isValid()) {
                            tot++;
                            System.out.println(ans.getReponse());
                        }
                    }
                    blY.add(cnt);
                }
            }
        }
        Button submit = new Button("submit");
        blY.add(submit);
        blY.add(new Label("___"));
        blY.setVisible(false);
        container.addAll(getQuiz, blY);
        getQuiz.addActionListener(l -> {
            getQuiz.setEnabled(false);
            getQuiz.getAllStyles().setBgTransparency(255);
            getQuiz.getStyle().setBgColor(0xff2d55);
            System.out.println("click get quiz");
            //getQuiz(q);
            blY.setVisible(true);
            registerAnimated(this);

        });
        submit.addActionListener(l -> {
            submit();
        });
        add(container);
        //add(container);

    }

   /* public void getQuiz(Quiz q) {
        System.out.println("processed");
        //getQuiz.setVisible(false);
        Button cntdown = new Button("countDown");

        Container blY = new Container(BoxLayout.y());
        blY.add(cntdown);
        ArrayList<Question> questions = ServiceQuestion.getInstance().getAllQuestions();
        ArrayList<Answer> reponses = ServiceReponse.getInstance().getAllReponses();
        for (Question quest : questions) {
            if (quest.getQuiz_id() == q.getId()) {
                blY.add(new Label(quest.getBody(), "NewsTopLine2"));
                for (Answer ans : reponses) {
                    int i = 0;
                    cnt = new Container(BoxLayout.x());
                    if (ans.getQuest_id() == quest.getId()) {
                        //addStringValue(ans.getReponse(), new CheckBox());
                        cnt.add(new CheckBox(ans.getReponse()));
                    }
                    blY.add(cnt);
                }
            }
        }
//
//        Button submit = new Button("submit");
//        blY.add(submit);
        add(blY);
    }*/

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
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    public void submit() {
        double note = 0;
        deregisterAnimated(this);
        blY.setVisible(false);
        for (Map.Entry<Answer, CheckBox> entry : radioMap.entrySet()) {
            if (entry.getValue().isSelected()) {
                if (entry.getKey().isValid()) {
                    note++;
                } else {
                    note--;
                }
            }
        }
        if (note < 0) {
            note = 0;
        }
        note = ((note / tot) * 100);
        System.out.println(note);
        Participation p = new Participation();
        p.setOffre_id(4);
        p.setQuiz_id(quiz.getId());
        p.setUser_id(SessionManager.getId());
        p.setNote(note);
        ServiceParticipation.getInstance().addParticipation(p);
        //removeAll();
        ToastBar.showMessage("Your participation has been added successfully !", FontImage.MATERIAL_INFO);
        new ResultsForm(r).show();
    }

    @Override
    public boolean animate() {
        //System.out.println("time seconds ======= " + timeSeconds);
        if (timeSeconds > 0) {
            if (System.currentTimeMillis() / 1000 > lastRenderedTime / 1000) {
                lastRenderedTime = System.currentTimeMillis();
                timeSeconds--;
                timeMinutes = timeSeconds / 60;
                Integer secs = timeSeconds % 60;
                getQuiz.setText(timeMinutes.toString() + ":" + secs.toString());
                return true;
            }
        } else {
            submit();
            deregisterAnimated(this);
        }
        return false;

    }

}
