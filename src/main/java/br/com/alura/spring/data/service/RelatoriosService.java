package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	Boolean system = true;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual funcao deseja selecionar?");
			System.out.println("1 - Buscar funcionario nome");
			System.out.println("2 - Buscar funcionario nome, salario e data contratacao");
			System.out.println("3 - Buscar funcionario nome, salario e data contratacao maior");
			System.out.println("4 - Funcionario projecao");
			
			int opcao = scanner.nextInt();
			
			switch(opcao) {
			case 1:
				this.buscaFuncionario(scanner);
			case 2:
				this.buscaFuncionarioNomeSalarioMaiorData(scanner);
			case 3:
				this.buscaFuncionarioNomeSalarioMaiorDataMaior(scanner);
			case 4:
				this.pesquisaFuncionarioSalario();
			default:
				system = false;
			} 
		}
	}
	
	private void buscaFuncionario(Scanner scanner) {
		System.out.println("Qual o nome deseja pesquisar");
		String name = scanner.next();
		List<Funcionario> list = funcionarioRepository.findByNome(name);
		
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual o nome deseja pesquisar");
		String nome = scanner.next();
		
		System.out.println("Qual o salario deseja pesquisar");
		Double salario = scanner.nextDouble();
		
		System.out.println("Qual o data deseja pesquisar");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorDataMaior(Scanner scanner) {
		System.out.println("Qual o data deseja pesquisar");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacaoMaior(localDate);
		
		list.forEach(System.out::println);
	}
	
	
	private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		
		list.forEach(f -> System.out.println("Funcionario: "
				+ "| id: "+f.getId()
				+" | nome: "+f.getNome()
				+" | salario: "+f.getSalario()));
	}
}
