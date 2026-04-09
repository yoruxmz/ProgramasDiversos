import java.util.Scanner;
import java.util.ArrayList;

public class CaixaFarmacia {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArrayList<String> relatorioFinal = new ArrayList<>();

        int opcaoMenu = 0, opcaoCliente = 0, qtdGeral = 0, qtdFidelizado = 0;
        double compraCliente = 0, totalFinal = 0, faturamentoTotal = 0, totalDescontosConcedidos = 0;
        boolean fiel = false;

        do {
            System.out.print("""
                    ===== CAIXA DA FARMÁCIA =====
                    1 - Registrar Compra
                    2 - Gerar Relatório Diário
                    3 - Exportar Relatorio para Arquivo
                    4 - Sair
                    =============================
                    Selecione uma opção: """);
            opcaoMenu = entrada.nextInt();
            entrada.nextLine();

            switch (opcaoMenu) {
                case 1:
                    System.out.print("""
                            ===== REGISTRO DE COMPRAS =====
                            Selecione o tipo de Cliente:
                            1 - Cliente Fidelizado
                            2 - Cliente Geral
                            ===============================
                            Selecione uma opção: """);
                    opcaoCliente = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Digite o valor da compra: R$ ");
                    compraCliente = entrada.nextDouble();
                    entrada.nextLine();

                    fiel = isFidelizado(opcaoCliente);
                    totalFinal = calcularTotal(compraCliente, fiel);
                    double descontoAplicado = compraCliente - totalFinal;
                    System.out.printf("Total a pagar: R$ %.2f\n\n", totalFinal);

                    String resumoCompra = """
                               Tipo de Cliente: %s
                               Valor da Compra: R$ %.2f
                               Valor do Desconto: R$ %.2f
                            Valor Final da Venda: R$ %.2f
                            -------------------------------
                            """.formatted((fiel ? "Fidelizado" : "Geral"), compraCliente, descontoAplicado, totalFinal);
                    relatorioFinal.add(resumoCompra);

                    faturamentoTotal += totalFinal;
                    totalDescontosConcedidos += descontoAplicado;
                    if (fiel) {
                        qtdFidelizado++;
                    } else {
                        qtdGeral++;
                    }
                    break;

                case 2:
                    System.out.println("===== RELATÓRIO DE VENDAS =====");
                    if (relatorioFinal.isEmpty()) {
                        System.out.println("Nenhuma venda realizada.");
                    } else {
                        for (String registro : relatorioFinal) {
                            System.out.println(registro);
                        }
                    }
                    System.out.println("===============================");
                    break;

                case 3:
                    try {
                        java.io.PrintWriter escritor = new java.io.PrintWriter("relatorio_vendas.txt");
                        escritor.println("===== RELATÓRIO DE VENDAS FARMÁCIA =====");
                        for (String registro : relatorioFinal) {
                            escritor.println(registro);
                        }
                        escritor.println("\n===== RESUMO DO DIA =====");
                        escritor.printf("Faturamento Total: R$ %.2f\n", faturamentoTotal);
                        escritor.printf("Total de Descontos: R$ %.2f\n", totalDescontosConcedidos);
                        escritor.println("Clientes Gerais: " + qtdGeral);
                        escritor.println("Clientes Fidelizados: " + qtdFidelizado);
                        escritor.close();
                        System.out.println("Arquivo 'relatorio_vendas.txt' gerado com sucesso!");
                    } catch (java.io.IOException e) {
                        System.out.println("Erro ao gerar o arquivo: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Encerrando sistema.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcaoMenu != 3);
        entrada.close();
    }

    public static boolean isFidelizado(int cliente) {
        return cliente == 1;
    }

    public static double calcularTotal(double valorCompra, boolean eFidelizado) {
        double valorComDesconto = valorCompra;

        if (eFidelizado) {
            if (valorCompra >= 500) {
                valorComDesconto = valorCompra * 0.80;
            } else if (valorCompra >= 200) {
                valorComDesconto = valorCompra * 0.90;
            }
        }
        return valorComDesconto;
    }
}