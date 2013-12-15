/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ModuloUtils {
    
    public static int moduloPower(int a, int exp, int n){
        if(n<2){
            throw new IllegalArgumentException(""+n);
        }
        int res = 1;
        for(int i = 0; i < exp; i++){
            res = (res*a)%n;
        }
        return res;
    }
}
