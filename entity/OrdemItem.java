package entity;

public class OrdemItem {
    private int id;
    private int ordemId;
    private int servicoId;
    private int quantidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdemId() {
        return ordemId;
    }

    public void setOrdemId(int ordemId) {
        this.ordemId = ordemId;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}