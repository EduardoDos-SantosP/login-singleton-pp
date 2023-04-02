import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Usuario> usuarios = new ArrayList<>();

    private static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        print("-".repeat(20));
        Scanner input = new Scanner(System.in);

        while (true) {
            print("\nDigite 0 para sair");
            print("Digite 1 para efeturar login");
            print("Digite 2 para cadastrar novo usuário");
            print("Digite 3 para usuário logado");
            
            var op = input.nextInt();
            input.nextLine();
            switch (op) {
                case 0:
                    return;
                case 1:
                    login(input);
                    break;
                case 2:
                    cadastrar(input);
                    break;
                case 3:
                    var u = Sessao.getInstancia().getUsuario();
                    print(u == null 
                        ? "Nenhum usuário logado" 
                        : ("'" + u.getNome() + "' está logado no momento"));
            }
        }
    }

    private static void cadastrar(Scanner input) {
        print("\n--Cadastro de usuário--");
        var usuario = new Usuario();

        while (usuario.getNome() == null) {
            print("Digite seu nome de usuário");
            var nome = input.nextLine();
            boolean existeUsuarioComEsseNome = usuarios.stream()
                    .anyMatch(u -> u.getNome().equals(nome));
            if (existeUsuarioComEsseNome)
                print("Já existe um usuário cadastrado com esse nome");
            else 
                usuario.setNome(nome);
        }
        
        print("Digite sua senha");
        usuario.setSenha(input.nextLine());

        usuarios.add(usuario);
        print("Usuário cadastrado com sucesso");
    }

    private static void login(Scanner input) {
        if (Sessao.getInstancia().getUsuario() != null) {
            print("Usuário já logado");
            return;
        }

        print("\n--Login de usuário--");
        
        while (Sessao.getInstancia().getUsuario() == null) {
            print("Digite o nome de usuário");
            var nome = input.nextLine();
            print("Digite a senha");
            var senha = input.nextLine();

            if (!efetuarCaptcha(input)) {
                print("Captcha incorreto");
                continue;
            }

            var usuarioCadastrado = usuarios.stream()
                .filter(u -> u.getNome().equals(nome) && u.getSenha().equals(senha))
                .findFirst();

            if (usuarioCadastrado.isPresent()) {
                Sessao.getInstancia().setUsuario(usuarioCadastrado.get());
                print("Usuário logado com sucesso");
            }                
            else print("Nenhum usuário cadastrado com essas credenciais");

            return;
        }
    }

    private static boolean efetuarCaptcha(Scanner input) {
        var gerador = new Random();
        var codigo = "" + gerador.nextInt(10) + 
            gerador.nextInt(10) + 
            gerador.nextInt(10) + 
            gerador.nextInt(10);
        
        print("Digite o seguinte código para verificarmos que você não é um robô: " + codigo);
        return codigo.equals(input.nextLine());
    }
}
