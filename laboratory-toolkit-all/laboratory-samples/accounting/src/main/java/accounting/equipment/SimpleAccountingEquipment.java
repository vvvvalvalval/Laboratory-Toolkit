/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.equipment;

import accounting.documents.BalanceSheet;
import accounting.documents.IncomeStatement;

/**
 * Simple {@link AccountingEquipment} implementation.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class SimpleAccountingEquipment implements AccountingEquipment {

    private final BalanceSheet balanceSheet;
    private final IncomeStatement incomeStatement;

    public SimpleAccountingEquipment(BalanceSheet balanceSheet, IncomeStatement incomeStatement) {
        this.balanceSheet = balanceSheet;
        this.incomeStatement = incomeStatement;
    }

    @Override
    public IncomeStatement incomeStatement() {
        return incomeStatement;
    }

    @Override
    public BalanceSheet balanceSheet() {
        return balanceSheet;
    }
}
