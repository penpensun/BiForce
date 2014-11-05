/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.matrices;

/**
 * This interface describes the basic functions of a matrix.
 * @author Peng Sun
 * @param <T>
 */
public interface Matrix<T> {
    
    /**
     * This method returns a 2-dimension matrix.
     * @return 
     */
    public T[][] getMatix();
}
