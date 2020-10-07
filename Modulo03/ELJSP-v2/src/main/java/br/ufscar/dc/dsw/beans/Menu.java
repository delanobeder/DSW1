package br.ufscar.dc.dsw.beans;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    List<ItemMenu> itensMenu;

    public Menu() {
        itensMenu = new ArrayList<>();
        itensMenu.add(new ItemMenu("Principal", "principal.jsp"));
        itensMenu.add(new ItemMenu("Notícias", "noticias.jsp"));
        itensMenu.add(new ItemMenu("Produtos", "produtos.jsp"));
        itensMenu.add(new ItemMenu("Fale conosco", "contato.jsp"));
    }

    public List<ItemMenu> getItensMenu() {
        return itensMenu;
    }
}
