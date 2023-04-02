public class Sessao {
    private static Sessao instancia;

    private Usuario usuario;

    private Sessao() {}
    
    public static Sessao getInstancia() {
        if (instancia == null)
        instancia = new Sessao();
        
        return instancia;
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
