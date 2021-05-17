package com.codename1.uikit.cleanmodern;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.entites.Quiz;
import com.mobilePIDEV.services.ServiceParticipation;
import com.mobilePIDEV.services.ServiceQuiz;

import java.util.ArrayList;
import java.util.Date;
//import java.util.concurrent.TimeUnit;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class ResultStatForm extends BaseForm {

    ArrayList<Participation> participation;
    Container chartCont = new Container();
    private int min = 50;
    public ResultStatForm(Resources res) {
        super("ResultsForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("QUIZ : ");
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

        /*---------------- Charts ----------------*/
        ComboBox<Quiz> cb = new ComboBox<>();
        // cb.addItem(null);
        for (Quiz q : ServiceQuiz.getInstance().getAllQuizs()) {
            cb.addItem(q);
        }
        Slider sld = new Slider();
        sld.setEditable(true);
        sld.setMinValue(0);
        sld.setProgress(50);
        sld.setMaxValue(100);
        sld.setEditable(true);
        sld.setIncrements(10);
        Image sliderThumb = res.getImage("toggleSlider.png");
        sliderThumb = sliderThumb.scaledHeight(50);
        System.out.println(sliderThumb.getHeight());
        sld.setThumbImage(sliderThumb);
        Container sldCont = new Container(BoxLayout.x());
        Label val = new Label("Result min :"+min+"%");
        sldCont.addAll(val,sld);
        cb.addActionListener(l -> {
            getChart(cb.getSelectedItem().getId(), res);
            add(chartCont);

        });
        
        sld.addActionListener(l->{
            min = sld.getProgress();
            System.out.println(cb.getSelectedItem().getId());
            val.setText("Result min :"+min+"%");
            getChart(cb.getSelectedItem().getId(), res);
            add(chartCont);
        });
        addAll(cb,sldCont);
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
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }
    PieChart chart;
    CategorySeries series;
    ChartComponent c;

    private void getChart(int id, Resources res) {
        chartCont.remove();
        chartCont = new Container();
        
        int success = 0;
        int fail = 0;
        ArrayList<Participation> partsinQ = ServiceParticipation.getInstance().getAllParts(id);
        for (Participation p : partsinQ) {
            if (p.getNote() >= min) {
                success++;
            } else {
                fail++;
            }
        }
        double[] values = new double[]{success, fail};
        int[] colors = new int[]{0xFF0000,ColorUtil.GREEN };
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        series = new CategorySeries("Resultats Quiz");
        series.add("Fail ", fail);
        series.add("Success  ", success);
        chart = new PieChart(series, renderer);
        c = new ChartComponent(chart);
        c.getAllStyles().setBgTransparency(255);
        c.getAllStyles().setBgColor(0x99CCCC);

        chartCont.add(c);
        System.out.println("added");

        for (Participation p : partsinQ) {
            addButton(res.getImage("news-item-1.jpg"), p, false, 26, 32);

        }
    }

    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsColor(0xeeeeee);
        renderer.setLabelsTextFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        renderer.setLegendTextFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
//        renderer.setMargins(new int[]{20, 20, 15, 0});
        renderer.setMargins(new int[]{0, 0, 0, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    private void addButton(Image img, Participation p, boolean liked, int likeCount, int commentCount) {
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
                    + "\n posted : " + days + " days ago.";
            TextArea ta = new TextArea(title);
        } else {
            title = "Note : " + p.getNote()
                    + "\n posted : " + days + " days ago.";
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
        chartCont.add(cnt);
        image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }
}
