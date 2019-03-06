/*
 * INSERÇÃO DA B+ E EXCLUSÃO DA B
 */
package potree.BTree;

import java.util.Random;

/**
 *
 * @author Rafael Delanhese
 */
public class BTreeMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BTree arvore = new BTree();                      
        
        arvore.insereArvore(3, 0);
        arvore.insereArvore(4, 0);
        arvore.insereArvore(8, 0);
        arvore.insereArvore(9, 0);
        arvore.insereArvore(10, 0);
        arvore.insereArvore(11, 0);
        arvore.insereArvore(13, 0);
        arvore.insereArvore(17, 0);
        arvore.insereArvore(20, 0);
        arvore.insereArvore(25, 0);
        arvore.insereArvore(28, 0);
        arvore.insereArvore(30, 0);
        arvore.insereArvore(33, 0);
        arvore.insereArvore(36, 0);
        arvore.insereArvore(40, 0);
        arvore.insereArvore(43, 0);
        arvore.insereArvore(45, 0);
        arvore.insereArvore(48, 0);
        arvore.insereArvore(50, 0);
        arvore.insereArvore(52, 0);
        arvore.insereArvore(55, 0);
        
        arvore.inOrdem(arvore.getRaiz());
        System.out.println("");
        arvore.inOrdemPalavra(arvore.getRaiz(), "");
        //arvore.busca(arvore.getRaiz(), 30, 0);
    }
    
}
