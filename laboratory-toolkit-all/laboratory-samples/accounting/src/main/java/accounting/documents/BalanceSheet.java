/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.documents;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface BalanceSheet {

    /*
     * ASSETS
     */
    public double fixedAssets();

    public double inventories();

    public double accountReceivables();

    public double cash();
    /*
     * LIABILITIES
     */

    public double stockholderEquity();

    public double longTermDebt();

    public double bankDebt();

    public double accountPayables();
}
