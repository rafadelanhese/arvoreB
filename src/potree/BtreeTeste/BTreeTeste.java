/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potree.BtreeTeste;

import potree.BTree.IN;

/**
 *
 * @author Rafael Delanhese
 */
public class BTreeTeste implements IN{
     private No raiz;

    public BTreeTeste() {

    }

    public No getRaiz() {
        return raiz;
    }

    public No navegaAteFolha(int info) {
        int i;
        No p = raiz;
        while (p.getvLig(0) != null) {
            i = 0;
            while (i < p.getTL() && info > p.getvInfo(i)) {
                i++;
            }
            p = p.getvLig(i);
        }
        return p;
    }

    public int procuraPosInfo(No no, int info) {
        int pos = -1;
        for (int i = 0; i < no.getTL(); i++) {
            if (no.getvInfo(i) == info) {
                pos = i;
            }
        }
        return pos;
    }

    public No procuraNo(int info) {
        No aux = raiz;
        int pos = procuraPosInfo(aux, info);
        while (aux != null && pos == -1) {
            int i = 0;
            while (i < aux.getTL() && info > aux.getvInfo(i)) {
                i++;
            }
            aux = aux.getvLig(i);
            pos = procuraPosInfo(aux, info);
        }
        return aux;
    }

    public No localizaPai(No folha, int info) {
        No p;
        No pai;
        int i;
        p = raiz;
        pai = p;
        while (p != folha) {
            i = 0;
            while (i < p.getTL() && info > p.getvInfo(i)) {
                i++;
            }
            pai = p;
            p = p.getvLig(i);
        }
        return pai;
    }

    public No localizarSub(No folha, char lado) {
        if (lado == 'E') {
            while (folha.getvLig(0) != null) {
                folha = folha.getvLig(folha.getTL());
            }
        } else {
            while (folha.getvLig(0) != null) {
                folha = folha.getvLig(0);
            }
        }

        return folha;
    }

    public void split(No folha, No pai, int info) {
        No caixaUm;
        No caixaDois;
        int i, pos;

        caixaUm = new No();
        caixaDois = new No();

        for (i = 0; i < N; i++) {
            caixaUm.setvInfo(i, folha.getvInfo(i));
            caixaUm.setvPos(i, folha.getvPos(i));
            caixaUm.setvLig(i, folha.getvLig(i));
        }
        caixaUm.setvLig(N, folha.getvLig(N));
        caixaUm.setTL(N);

        for (i = N + 1; i < 2 * N + 1; i++) {
            caixaDois.setvInfo(i - (N + 1), folha.getvInfo(i));
            caixaDois.setvPos(i - (N + 1), folha.getvPos(i));
            caixaDois.setvLig(i - (N + 1), folha.getvLig(i));
        }
        caixaDois.setvLig(N, folha.getvLig(2 * N + 1));
        caixaDois.setTL(N);

        if (pai == folha) {
            folha.setvInfo(0, folha.getvInfo(N));
            folha.setvPos(0, folha.getvPos(N));
            folha.setvLig(0, caixaUm);
            folha.setvLig(1, caixaDois);
            folha.setTL(1);
        } else {
            info = folha.getvInfo(N);
            pos = pai.buscar(info);
            pai.remanejar(pos);
            pai.setTL(pai.getTL() + 1);
            pai.setvInfo(pos, folha.getvInfo(N));
            pai.setvPos(pos, folha.getvPos(N));
            pai.setvLig(pos, caixaUm);
            pai.setvLig(pos + 1, caixaDois);
            //dispose(folha);
            if (pai.getTL() > 2 * N) {
                folha = pai;
                info = folha.getvInfo(N);
                pai = localizaPai(pai, info);
                split(folha, pai, info);
            }
        }
    }

    public void insereArvore(char info, int posArq) {
        No folha;
        No pai;
        int i, pos;

        if (raiz == null) {
            raiz = new No(info, posArq);
        } else {
            folha = navegaAteFolha(info);
            pos = folha.buscar(info);
            folha.remanejar(pos);
            folha.setTL(folha.getTL() + 1);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            if (folha.getTL() > 2 * N) {
                pai = localizaPai(folha, info);
                split(folha, pai, info);
            }
        }
    }  

    public void inOrdem(No raiz) {
        int i;
        if (raiz != null) {
            for (i = 0; i < raiz.getTL(); i++) {
                inOrdem(raiz.getvLig(i));
                System.out.print(raiz.getvInfo(i) + " ");
            }
            inOrdem(raiz.getvLig(i));
        }
    }

    public void inOrdemPalavra(No raiz, String palavra) {
        int i;
        String frase = "";
        if (raiz != null) {
            for (i = 0; i < raiz.getTL(); i++) {                
                inOrdemPalavra(raiz.getvLig(i), palavra+raiz.getvInfo(i));
                frase = frase + raiz.getvInfo(i);
                System.out.println("Frase: " + frase);
            }
            inOrdemPalavra(raiz.getvLig(i), palavra.concat(String.valueOf(raiz.getvInfo(i))));            
        }        
    }

    public void busca(No raiz, int info, int i) {
        if (raiz != null) {
            if (raiz.getvInfo(i) == info) {
                System.out.println("Achou");
            } else {
                for (i = 0; i < raiz.getTL(); i++) {
                    busca(raiz.getvLig(i), info, i);
                }
                busca(raiz.getvLig(i), info, i);
            }
        }
    }
}
