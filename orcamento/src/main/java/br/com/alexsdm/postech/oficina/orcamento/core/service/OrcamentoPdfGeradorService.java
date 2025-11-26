package br.com.alexsdm.postech.oficina.orcamento.core.service;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Orcamento;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.inject.Named;

import java.io.ByteArrayOutputStream;

@Named
public class OrcamentoPdfGeradorService implements PdfGenerator {

    public byte[] generate(Orcamento orcamento,
                        Cliente cliente) {
        try {

            var baos = new ByteArrayOutputStream();
            var document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            adicionarCabecalho(document);
            document.add(new Paragraph(" "));

            adicionarCliente(cliente, document);
            document.add(new Paragraph(" "));

            adicionarPecas(orcamento, document);

            document.add(new Paragraph(" "));

            adicionarServico(orcamento, document);

            document.add(new Paragraph(" "));
            adicionarSumario(orcamento, document);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private void adicionarCabecalho(Document document) throws DocumentException {
        var tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        var titulo = new Paragraph("ORÇAMENTO", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
    }

    private void adicionarCliente(Cliente cliente,
                                  Document document) throws DocumentException {

        var nomeCompleto = cliente.getNome() + " " + cliente.getSobrenome();

        var descricaoVeiculo = cliente.getMarcaVeiculo() + " " + cliente.getModeloVeiculo() + " " +
                cliente.getAnoVeiculo() + " " + cliente.getAnoVeiculo();

        document.add(new Paragraph("Cliente: " + cliente.getNome() + " " + cliente.getSobrenome()));
        document.add(new Paragraph("CPF/CNPJ: " + cliente.getCpfCnpj()));
        document.add(new Paragraph("Veículo: " + nomeCompleto));
        document.add(new Paragraph("Placa: " + descricaoVeiculo));

    }

    private void adicionarPecas(Orcamento orcamento, Document document) throws DocumentException {
        var tabelaPecas = new PdfPTable(4);
        tabelaPecas.setWidthPercentage(100);
        tabelaPecas.addCell("Peça");
        tabelaPecas.addCell("Qtd");
        tabelaPecas.addCell("Valor Unitário");
        tabelaPecas.addCell("Subtotal");
        orcamento.getItensPeca().forEach(itemPeca -> {
            tabelaPecas.addCell(itemPeca.getNome());
            tabelaPecas.addCell(String.valueOf(itemPeca.getQuantidade()));
            tabelaPecas.addCell("R$ " + itemPeca.getPreco());
            tabelaPecas.addCell("R$ " + itemPeca.getTotal());
        });
        document.add(tabelaPecas);
    }

    private void adicionarServico(Orcamento orcamento, Document document) throws DocumentException {
        var tabelaServicos = new PdfPTable(2);
        tabelaServicos.setWidthPercentage(100);
        tabelaServicos.addCell("Serviço");
        tabelaServicos.addCell("Valor");
        orcamento.getServicos().forEach(servico -> {
            tabelaServicos.addCell(servico.getNome());
            tabelaServicos.addCell("R$ " + servico.getPreco());
        });
        document.add(tabelaServicos);
    }

    private void adicionarSumario(Orcamento orcamento, Document document) throws DocumentException {
        document.add(new Paragraph("Total em Peças: R$ " + orcamento.getValorTotalEmPecas()));
        document.add(new Paragraph("Total em Serviços: R$ " + orcamento.getValorTotalEmServicos()));
        document.add(new Paragraph("Valor Total: R$ " + orcamento.getValorTotal()));
        document.add(new Paragraph("Status: " + orcamento.getStatus()));
    }

}