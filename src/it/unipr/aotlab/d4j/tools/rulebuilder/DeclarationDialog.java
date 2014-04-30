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

import it.unipr.aotlab.d4j.rule.RuleParameter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it@tin.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: DeclarationDialog.java,v 1.3 2004/10/03 13:12:39 mic Exp $
 */
public class DeclarationDialog extends JDialog {

    private JPanel centralPanel = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JButton btnOk = new JButton();
    private JButton btnCancel = new JButton();
    private JPanel jPanel2 = new JPanel();
    private JLabel lblType = new JLabel();
    private JTextField txtType = new JTextField();
    private JTextField txtIdentifier = new JTextField();
    private JLabel lblIdentifier = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private RuleParameter ruleParameter = null;

    public DeclarationDialog(Frame frame, String title, RuleParameter ruleParameter) {
        super(frame, title, true);

        this.ruleParameter = ruleParameter;

        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DeclarationDialog(Frame frame, String title) {
        this(frame, title, new RuleParameter("", ""));
    }

    private void jbInit() throws Exception {

        centralPanel.setLayout(borderLayout1);

        ButtonActionListener buttonActionListener = new ButtonActionListener(this);

        btnOk.setText("Ok");
        btnOk.setActionCommand("Ok");
        btnOk.addActionListener(buttonActionListener);
        btnCancel.setText("Cancel");
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(buttonActionListener);

        jPanel2.setLayout(gridBagLayout1);
        lblIdentifier.setText("Identifier:");
        txtIdentifier.setText(ruleParameter.getIdentifier());
        lblType.setText("Type:");
        txtType.setText(ruleParameter.getType());
        this.setResizable(false);
        getContentPane().add(centralPanel);
        jPanel1.add(btnOk, null);
        jPanel1.add(btnCancel, null);
        centralPanel.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(txtIdentifier, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(13, 0, 0, 10), 215, 0));
        jPanel2.add(lblIdentifier, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(13, 16, 0, 12), 15, 6));
        jPanel2.add(txtType, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 10, 10), 215, 0));
        jPanel2.add(lblType, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 16, 10, 0), 11, 6));
        centralPanel.add(jPanel1, BorderLayout.SOUTH);
    }

    public RuleParameter getDeclaration() {

        return ruleParameter;
    }

    private class ButtonActionListener implements ActionListener {

        private JDialog owner = null;

        public ButtonActionListener(JDialog owner) {
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            if (actionCommand.equalsIgnoreCase("Cancel")) {
                ruleParameter = null;
            } else {

                String identifier = txtIdentifier.getText().trim();
                String type = txtType.getText().trim();

                ruleParameter = new RuleParameter(identifier, type);
            }

            owner.hide();
        }

    }
}
