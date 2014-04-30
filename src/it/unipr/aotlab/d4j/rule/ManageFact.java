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

package it.unipr.aotlab.d4j.rule;

import jade.content.AgentAction;

import java.io.Serializable;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: ManageFact.java,v 1.1 2004/10/02 23:31:00 mic Exp $
 */
public abstract class ManageFact implements AgentAction, Serializable {

    private String id = null;
    private String object = null;
    private String delegationCertificate = null;

    public ManageFact() {
        /* Intentionally left blank. */
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFact() {
        return object;
    }

    public void setFact(String object) {
        this.object = object;
    }

    /**
     * Returns (if present) a <tt>String</tt> containing a <tt>Base64</tt> encode
     * <tt>DelegationCertificate</tt>.
     *
     * @return a <tt>String</tt> containing a <tt>Base64</tt> encode
     *         <tt>DelegationCertificate</tt>.
     */
    public String getDelegationCertificate() {
        return delegationCertificate;
    }

    /**
     * Sets the <tt>DelegationCertificate</tt> slot. The <tt>DelegationCertificate</tt>
     * object must be converted into a <tt>Base64</tt> encoded <tt>String</tt>.
     *
     * @param delegationCertificate a <tt>String</tt> containing a <tt>Base64</tt> encode
     *                              <tt>DelegationCertificate</tt>.
     */
    public void setDelegationCertificate(String delegationCertificate) {
        this.delegationCertificate = delegationCertificate;
    }
}
