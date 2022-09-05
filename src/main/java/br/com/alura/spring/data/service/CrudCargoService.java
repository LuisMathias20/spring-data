package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository cargoRepository;
	
	Boolean system = true;
	
	public CrudCargoService(CargoRepository cargoRepository) {
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
			case 2:
				atualizar(scanner);
			case 3:
				visualizar();
			case 4:
				deletar(scanner);
			default:
				system = false;
			} 
		}
	}
	
	private void salvar(Scanner scanner) {
		Cargo cargo = new Cargo();
		System.out.println("Descricao do cargo");
		String descricao = scanner.next();
		
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		
		System.out.println("Cargo salva com sucesso");
	}
	
	private void atualizar(Scanner scanner) {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		
		for(Cargo cargo : cargos) {
			System.out.println(cargo.getId()+" - "+cargo.getDescricao());
		}
		System.out.println("Qual registro deseja atualizar?");
		int opcao = scanner.nextInt();
		System.out.println("Como vai ser a nova descricao do cargo?");
		String novaDescricao = scanner.next();
		
		Cargo cargoSelecionado = cargoRepository.findById(opcao).get();
		
		cargoSelecionado.setDescricao(novaDescricao);
		
		cargoRepository.save(cargoSelecionado);
		System.out.println("Cargo atualizado com sucesso");
	}
	
	private void visualizar() {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		
		for(Cargo cargo : cargos) {
			System.out.println(cargo.toString());
		}
	}
	
	private void deletar(Scanner scanner) {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		
		for(Cargo cargo : cargos) {
			System.out.println(cargo.getId()+" - "+cargo.getDescricao());
		}
		System.out.println("Qual registro deseja deletar?");
		int opcao = scanner.nextInt();
		
		cargoRepository.deleteById(opcao);
		System.out.println("Cargo deletado com sucesso");
	}
}
