/*****************************************************************
 The AOT Package is a rules engine add-on for Jade created by the
 University of Parma - 2003

 GNU Lesser General Public License

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation,
 version 2.1 of the License.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the
 Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 Boston, MA  02111-1307, USA.
 *****************************************************************/

package it.unipr.aotlab.d4j.tools.rulebuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * The About and Credits dialog window.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: AboutDialog.java,v 1.1 2004/10/01 10:03:42 mic Exp $
 */
public class AboutDialog extends JDialog implements ActionListener {

    private JPanel mainPanel = new JPanel();
    private JPanel creditsPanel = new JPanel();
    private JPanel insetsPanel1 = new JPanel();
    private JPanel imagePanel = new JPanel();
    private JPanel creditPanel = new JPanel();
    private JButton button1 = new JButton();
    private JLabel imageLabel = new JLabel();
    private JLabel label1 = new JLabel();
    private JLabel label3 = new JLabel();
    private JLabel label4 = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private FlowLayout flowLayout1 = new FlowLayout();
    private GridLayout gridLayout1 = new GridLayout();
    private String comments = "";
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel9 = new JLabel();

    public AboutDialog(Frame parent) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        this.setModal(true);
        this.setTitle("About");
        mainPanel.setLayout(borderLayout1);
        creditsPanel.setLayout(borderLayout2);
        insetsPanel1.setLayout(flowLayout1);
        imagePanel.setLayout(flowLayout1);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridLayout1.setRows(14);
        gridLayout1.setVgap(3);
        gridLayout1.setColumns(1);
        label1.setText("<html>Created by <a href=\"mailto:a_beneventi@tin.it\">Alessandro Beneventi</a></html>");
        label1.setToolTipText("a_beneventi@tin.it");
        label3.setText("Version 1.0 - Fall 2003");
        label4.setText(comments);
        creditPanel.setLayout(gridLayout1);
        creditPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button1.setText("Ok");
        button1.addActionListener(this);
        jLabel1.setText("<html><i>\"We all stand on the shoulders of giants.\"</i></html>");
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText("<html><i>(Jesse Liberty)</i></html>");
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("When you think you\'ve created something good, ");
        jLabel4.setToolTipText("");
        jLabel4.setText("just sit down and think about it. You might end up");
        jLabel5.setText("realizing that you\'ve only added a little contribution ");
        jLabel6.setText("to something created or just theorized by others.");
        jLabel8.setText("That\'s my case but I\'m happy anyway...");
        jLabel9.setText("");
        imageLabel.setIcon(GuiProperties.getIcon("PanelIcon"));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        imagePanel.add(imageLabel, null);
        creditsPanel.add(imagePanel, BorderLayout.WEST);
        this.getContentPane().add(mainPanel, null);
        creditPanel.add(label1, null);
        creditPanel.add(label3, null);
        creditPanel.add(label4, null);
        creditsPanel.add(creditPanel, BorderLayout.CENTER);
        insetsPanel1.add(button1, null);
        mainPanel.add(insetsPanel1, BorderLayout.SOUTH);
        mainPanel.add(creditsPanel, BorderLayout.NORTH);
        creditPanel.add(jLabel1, null);
        creditPanel.add(jLabel2, null);
        creditPanel.add(jLabel9, null);
        creditPanel.add(jLabel3, null);
        creditPanel.add(jLabel4, null);
        creditPanel.add(jLabel5, null);
        creditPanel.add(jLabel6, null);
        creditPanel.add(jLabel7, null);
        creditPanel.add(jLabel8, null);
        setResizable(false);
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            cancel();
        }
        super.processWindowEvent(e);
    }

    void cancel() {
        dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            cancel();
        }
    }
}