/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.documents;

/**
 * The Income Statement measures the wealth creation of a company for a fiscal
 * year.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface IncomeStatement {

    public double operatingRevenues();
    public double operatingExpenses();
    
    public double amortizations();
    public double provisions();
    
    public double financialRevenues();
    public double financialExpenses();
    
    public double incomeTax();
    public double exceptionalIncome();
}
