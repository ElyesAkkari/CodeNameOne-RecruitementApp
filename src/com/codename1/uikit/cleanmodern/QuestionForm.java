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
import com.codename1.components.SpanLabel;
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
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.entites.Question;
import com.mobilePIDEV.entites.Quiz;
import com.mobilePIDEV.services.ServiceParticipation;
import com.mobilePIDEV.services.ServiceQuestion;
import com.mobilePIDEV.services.ServiceQuiz;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.ButtonType;
//import java.util.concurrent.TimeUnit;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class QuestionForm extends BaseForm {

    ArrayList<Question> questions;

    public QuestionForm(Resources res) {
        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Quiz Management");
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
        RadioButton quiz = RadioButton.createToggle("Quiz", barGroup);
        quiz.setUIID("SelectBar");
        RadioButton question = RadioButton.createToggle("Questions", barGroup);
        question.setUIID("SelectBar");
        RadioButton rep = RadioButton.createToggle("Answers", barGroup);
        rep.setUIID("SelectBar");

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, quiz, question, rep),
                FlowLayout.encloseBottom(arrow)
        ));

        question.setSelected(true);
        quiz.addActionListener(l -> new QuizForm(res).showBack());
        rep.addActionListener(l -> new ReponseForm(res).show());
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(question, arrow);
        });
        bindButtonSelection(quiz, arrow);
        bindButtonSelection(question, arrow);
        bindButtonSelection(rep, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        questions = ServiceQuestion.getInstance().getAllQuestions();
        for (Question q : questions) {
            addButton(q, res);
        }
        Button addQuiz = new Button("add question");
        addQuiz.addActionListener(l -> {
            new AddQuestionForm(res).show();
        });
        this.add(addQuiz);

    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer){
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

    private void addButton(Question q, Resources res) {

        Label titre = new Label("Question : " + q.getBody(), "NewsTopLine2");
        Label nbrq = new Label("Question number : " + q.getNbr_rep());
        Label qz = new Label("NO QUIZ");
        if (q.getQuiz() != null) {
            qz.setText("Quiz : " + q.getQuiz().getTitre());
        }

        Container cnt = BorderLayout.west(
                BoxLayout.encloseY(
                        BoxLayout.encloseX(titre),
                        BoxLayout.encloseX(nbrq),
                        BoxLayout.encloseX(qz)
                ));
        Button btn = new Button();
        cnt.addPointerPressedListener(l -> {
            Dialog dlg = new Dialog("Question");
            Button edit = new Button("edit");
            Button delete = new Button("delete");

            delete.addActionListener(del -> {
                if (Dialog.show("Confirmation", "Delete \" " + q.getBody() + "\" ?", "ok", "cancel")) {

                    if (ServiceQuestion.getInstance().DeleteQuestion(q)) {
                        Dialog.show("Success", "Question Deleted", new Command("OK"));
                        new QuestionForm(res).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                }
            });

            edit.addActionListener(ed -> {
                 new EditQuestionForm(res, q).show();
            });
            dlg.setLayout(BoxLayout.y());
            if (q.getQuiz() != null) {
                dlg.add(new SpanLabel("Titre : " + q.getBody() + "\nQuiz : " + q.getQuiz().getTitre(), "DialogBody"));
            } else {
                dlg.add(new SpanLabel("Titre : " + q.getBody() + "\nQuiz : NO QUIZ", "DialogBody"));
            }
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
