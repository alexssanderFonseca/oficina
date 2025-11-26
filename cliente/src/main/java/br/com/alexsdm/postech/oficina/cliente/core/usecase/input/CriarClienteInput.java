package br.com.alexsdm.postech.oficina.cliente.core.usecase.input;

public record CriarClienteInput(
        String nome,
        String sobrenome,
        String cpfCnpj,
        String email,
        String telefone,
        EnderecoInput endereco
) {
    public record EnderecoInput(
            String rua,
            String numero,
            String bairro,
            String cep,
            String cidade,
            String uf
    ) {}
}
