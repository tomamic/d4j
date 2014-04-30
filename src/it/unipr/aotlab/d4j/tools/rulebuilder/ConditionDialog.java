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

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


/**
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: ConditionDialog.java,v 1.2 2004/10/01 15:24:07 mic Exp $
 */
public class ConditionDialog extends JDialog {

    private JPanel centralPanel = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JTextField txtCode = new JTextField();
    private JLabel lblCode = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JButton btnOk = new JButton();
    private JButton btnCancel = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private String condition = null;

    public ConditionDialog(Frame frame, String title, String condition) {

        super(frame, title, true);

        this.condition = condition;

        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConditionDialog(Frame frame, String title) {
        this(frame, title, "");
    }

    private void jbInit() throws Exception {
        centralPanel.setLayout(borderLayout1);

        jPanel2.setLayout(gridBagLayout1);

        ButtonActionListener buttonActionListener = new ButtonActionListener(this);

        btnOk.setText("Ok");
        btnOk.setActionCommand("Ok");
        btnOk.addActionListener(buttonActionListener);

        btnCancel.setText("Cancel");
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(buttonActionListener);

        txtCode.setText(condition);

        lblCode.setText("Code:");
        this.setResizable(false);
        getContentPane().add(centralPanel);
        centralPanel.add(jPanel2, BorderLayout.CENTER);
        jPanel1.add(btnOk, null);
        jPanel1.add(btnCancel, null);
        jPanel2.add(lblCode, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(23, 10, 0, 12), 31, 6));
        jPanel2.add(txtCode, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(23, 0, 0, 11), 302, 0));
        centralPanel.add(jPanel1, BorderLayout.SOUTH);
    }

    public String getCondition() {

        return condition;
    }


    private class ButtonActionListener implements ActionListener {

        private JDialog owner = null;

        public ButtonActionListener(JDialog owner) {
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            if (actionCommand.equalsIgnoreCase("Cancel")) {
                condition = null;
            } else {
                condition = txtCode.getText().trim();
            }

            owner.hide();
        }
    }

}
