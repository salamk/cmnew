/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tools;

/**
 *
 * @author coolmarch
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
/**
 *
 * @author Mashuk
 */
public class SlideShow extends JFrame {
 
    private JPanel slidePane;
    private JPanel buttonPane;
    private CardLayout card;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel lblSlide;
 
    /**
     * 
     * @throws HeadlessException 
     */
    public SlideShow() throws HeadlessException {
        initComponent();
        
    }
 
    /**
     * 
     */
    private void initComponent() {
        card = new CardLayout();
        slidePane = new JPanel();
        buttonPane = new JPanel();
        btnPrev = new JButton();
        btnNext = new JButton();
//        lblSlide = new JLabel();
 
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Simple SlideShow");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        slidePane.setLayout(card);
         
        for (int i = 1; i <= 10; i++) {
            lblSlide = new JLabel();
            lblSlide.setText(getResizeIcon(i));
            slidePane.add(lblSlide, "card" + i);
        }
 
        add(slidePane, BorderLayout.CENTER);
 
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
 
        btnPrev.setText("Previous");
        btnPrev.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                goPrevious();
            }
        });
        buttonPane.add(btnPrev);
 
        btnNext.setText("Next");
        btnNext.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                goNext();
            }
        });
        buttonPane.add(btnNext);
 
        add(buttonPane, BorderLayout.SOUTH);
         
    }
 
    /**
     * 
     */
    private void goPrevious() {
        card.previous(slidePane);
    }
 
    /**
     * 
     */
    private void goNext() {
        card.next(slidePane);
    }
    /**
     * 
     * @param i
     * @return 
     */
    private ImageIcon getSIcon(int i) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/slideshow/images/img ("+i+").jpg"));        
        return icon;
    }
    /**
     * 
     * @param i
     * @return 
     */
    private String getResizeIcon(int i){
    String st = "<html><body><img width= '800' height='500' src='"+getClass().getResource("/slideshow/images/img ("+i+").jpg") +"'</body></html>";
    return st;
    }
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
 
            @Override
            public void run() {
                SlideShow ss = new SlideShow();
                ss.setVisible(true);
            }
        });
    }
}