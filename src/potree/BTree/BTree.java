/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potree.BTree;

/**
 *
 * @author Aluno
 */
public class BTree implements IN {

    private No raiz;

    public BTree() {

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

    public void insereArvore(int info, int posArq) {
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

    public void exclui(int info) {
        if (raiz == null) {
            System.out.println("Não há elementos para serem excluídos");
        } else {
            No aux = procuraNo(info);
            if (aux == null) { //NÃO ACHOU INFORMAÇÃO
                System.out.println("Número digitado não foi encontrado");
            } else {
                //PERGUNTA PRIMEIRO SE NÃO É FOLHA
                int pos = aux.buscar(info);
                if (aux.getvLig(0) != null) {

                    No noSubE = localizarSub(aux.getvLig(pos), 'E');
                    No noSubD = localizarSub(aux.getvLig(pos), 'D');

                    if (noSubE.getTL() > N) {
                        info = noSubE.getvInfo(noSubE.getTL() - 1);
                        aux.setvInfo(pos, info);
                        aux = noSubE;
                        aux.remanejaExcluir(pos);
                    } else {
                        if (noSubD.getTL() > N) {
                            info = noSubD.getvInfo(0);
                            aux.setvInfo(pos, info);
                            aux = noSubD;
                            aux.remanejaExcluir(pos);
                        } else {
                            info = noSubE.getvInfo(noSubE.getTL() - 1);
                            aux.setvInfo(pos, info);
                            aux = noSubE;
                            aux.remanejaExcluir(pos);
                        }
                    }

                    //SE APÓS RETIRAR A INFORMAÇÃO O NÓ MANTÉM AS PROPRIEDADES DA ÁRVORE
                    if (aux.getTL() < N) {
                        No pai = localizaPai(aux, info);
                        int posNo = -1;
                        //FOR PARA VER EM QUE LIGAÇÃO ESTÁ O AUX
                        for (int i = 0; i < pai.getTL(); i++) {
                            if (pai.getvLig(i) == aux) {
                                posNo = i;
                            }
                        }

                        if (posNo == -1) {
                            System.out.println("Não achou o nó aux");
                        } else {
                            //VERIFICA VIZINHO DA ESQUERDA
                            if (pai.getvLig(posNo - 1).getTL() > N) {
                                noSubE = pai.getvLig(posNo - 1);

                                aux.setvInfo(aux.getTL(), pai.getvInfo(pos));
                                aux.remanejar(aux.getTL());
                                aux.setTL(aux.getTL() + 1);

                                pai.setvInfo(pos, noSubE.getvInfo(noSubE.getTL() - 1));
                                noSubE.remanejaExcluir(noSubE.getTL() - 1);
                            } else {
                                if (pai.getvLig(posNo + 1).getTL() > N) {
                                    noSubD = pai.getvLig(posNo + 1);

                                    aux.setvInfo(aux.getTL(), pai.getvInfo(pos));
                                    aux.remanejar(aux.getTL());
                                    aux.setTL(aux.getTL() + 1);

                                    pai.setvInfo(pos, noSubD.getvInfo(0));
                                    noSubD.remanejaExcluir(0);
                                }
                            }
                        }
                    }
                } else {
                    if (aux.getTL() > N) {
                        //É FOLHA E TEM ELEMENTOS PARA EXCLUIR E REMANEJAR QUE NÃO FERE AS LEIS DA ÁRVORE
                        aux.remanejaExcluir(pos);
                    }
                }
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
        if (raiz != null) {
            for (i = 0; i < raiz.getTL(); i++) {                
                inOrdemPalavra(raiz.getvLig(i), palavra.concat(String.valueOf(raiz.getvInfo(i))));
                //System.out.println(palavra);
            }
            inOrdemPalavra(raiz.getvLig(i), palavra.concat(String.valueOf(raiz.getvInfo(i))));            
        }
        System.out.println(palavra);
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
