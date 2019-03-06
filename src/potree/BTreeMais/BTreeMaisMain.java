/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potree.BTreeMais;

/**
 *
 * @author Rafael Delanhese
 */
public class BTreeMaisMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BTreeMais arvore = new BTreeMais();

        arvore.insere(7);
        arvore.insere(9);
        arvore.insere(13);
        arvore.insere(15);
        arvore.insere(8);
        arvore.insere(30);
        arvore.insere(35);
        arvore.insere(50);
        arvore.insere(50);
        arvore.insere(50);
        arvore.insere(50);
        

        arvore.inOrdem(arvore.getRaiz());
    }

}
