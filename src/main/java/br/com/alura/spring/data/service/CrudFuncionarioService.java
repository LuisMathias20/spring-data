package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class CrudFuncionarioService {
	
	Boolean system = true;

	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
	}
	
	public void inicial(Scanner scanner) {		
		while(system) {
			System.out.println("Qual funcao deseja selecionar?");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			int opcao = scanner.nextInt();
			
			switch(opcao) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
			} 
		}
	}
	
	private void salvar(Scanner scanner) {
		Funcionario funcionario = new Funcionario();
		System.out.println("Nome do funcionario");
		String nome = scanner.next();
		funcionario.setNome(nome);
		
		System.out.println("Cpf do funcionario");
		String cpf = scanner.next();
		funcionario.setCpf(cpf);
		
		System.out.println("Salario do funcionario");
		Double salario = scanner.nextDouble();
		funcionario.setSalario(salario);
		
		System.out.println("Cargo do funcionario");
		int cargo = scanner.nextInt();
		funcionario.setCargo(cargoRepository.findById(cargo).get());
		
		funcionarioRepository.save(funcionario);
		
		System.out.println("Funcionario salva com sucesso");
	}
	
	private void atualizar(Scanner scanner) {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		
		for(Funcionario funcionario : funcionarios) {
			System.out.println(funcionario.getId()+" - "+funcionario.getNome());
		}
		System.out.println("Qual registro deseja atualizar?");
		int opcao = scanner.nextInt();
		System.out.println("Como vai ser a nova descricao do funcionario?");
		String novaDescricao = scanner.next();
		
		Funcionario funcionarioSelecionado = funcionarioRepository.findById(opcao).get();
		
		funcionarioSelecionado.setNome(novaDescricao);
		
		funcionarioRepository.save(funcionarioSelecionado);
		System.out.println("Funcionario atualizado com sucesso");
	}
	
	private void visualizar(Scanner scanner) {
		System.out.println("Qaul pagina deseja visualizar?");
		Integer page = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Pagina atual "+funcionarios.getNumber());
		System.out.println("Total registros "+funcionarios.getTotalElements());
	}
	
	private void deletar(Scanner scanner) {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		
		for(Funcionario funcionario : funcionarios) {
			System.out.println(funcionario.getId()+" - "+funcionario.getNome());
		}
		System.out.println("Qual registro deseja deletar?");
		int opcao = scanner.nextInt();
		
		funcionarioRepository.deleteById(opcao);
		System.out.println("Funcionario deletado com sucesso");
	}
}
