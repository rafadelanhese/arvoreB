/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potree.BtreeTeste;

/**
 *
 * @author Rafael Delanhese
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         BTreeTeste arvore = new BTreeTeste();                      
        
        arvore.insereArvore('A', 0);
        arvore.insereArvore('R', 0);
        arvore.insereArvore('V', 0);
        arvore.insereArvore('O', 0);
        arvore.insereArvore('R', 0);
        arvore.insereArvore('E', 0);
        
        arvore.inOrdemPalavra(arvore.getRaiz(), "");
   
    }
    
}
