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
public class BTreeMais implements INBMais {

    private No raiz;

    public No getRaiz() {
        return raiz;
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

    public void split(No folha, No pai, int info) {
        No caixaUm;
        No caixaDois;
        int i, pos, tamanho;

        caixaUm = new No();
        caixaDois = new No();

        if (folha.getvLig(0) == null) {
            tamanho = (int)(((N - 1)*1.0 / 2)+0.5);
        } else {
            tamanho = (N / 2) - 1;
        }

        for (i = 0; i < tamanho; i++) {
            caixaUm.setvInfo(i, folha.getvInfo(i));
            caixaUm.setvLig(i, folha.getvLig(i));
        }
        caixaUm.setvLig(i, folha.getvLig(i));
        caixaUm.setTL(i);

        for (i = tamanho; i < N - 1; i++) {
            caixaDois.setvInfo(i - tamanho, folha.getvInfo(i));
            caixaDois.setvLig(i - tamanho, folha.getvLig(i));
        }
        caixaDois.setvLig(i - tamanho, folha.getvLig(i));
        caixaDois.setTL(i - tamanho);

        if(folha.getvLig(0) == null){
            caixaUm.setProx(caixaDois);
            caixaDois.setAnt(caixaUm);
        }
        if (pai == folha) {
            folha.setvInfo(0, folha.getvInfo(tamanho));
            folha.setvLig(0, caixaUm);
            folha.setvLig(1, caixaDois);
            folha.setTL(1);
        } else {
            info = folha.getvInfo(tamanho);
            pos = pai.buscar(info);
            pai.remanejar(pos);
            pai.setTL(pai.getTL() + 1);
            pai.setvInfo(pos, info);           
            pai.setvLig(pos, caixaUm);
            pai.setvLig(pos + 1, caixaDois);
            //dispose(folha);
            if (pai.getTL() >= N - 1) {
                folha = pai;
                info = folha.getvInfo(N);
                pai = localizaPai(pai, info);
                split(folha, pai, info);
            }
        }
    }

    public void insere(int info) {
        if (raiz == null) {
            raiz = new No(0, info);
        } else {
            No folha = navegaAteFolha(info);
            int pos = folha.buscar(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setTL(folha.getTL() + 1);
            if (folha.getTL() >= N) {
                No pai = localizaPai(folha, info);
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
}
