package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.UnidadeRepository;

@Service
public class CrudUnidadeService {

	private final UnidadeRepository unidadeRepository;
	
	public CrudUnidadeService(UnidadeRepository unidadeRepository) {
		this.unidadeRepository = unidadeRepository;
	}
	
	public void inicial(Scanner scanner) {
		Boolean system = true;
		
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
		Unidade unidade = new Unidade();
		System.out.println("Descricao do unidade");
		String descricao = scanner.next();
		
		unidade.setDescricao(descricao);
		unidadeRepository.save(unidade);
		
		System.out.println("Unidade salva com sucesso");
	}
	
	private void atualizar(Scanner scanner) {
		Iterable<Unidade> unidades = unidadeRepository.findAll();
		
		for(Unidade unidade : unidades) {
			System.out.println(unidade.getId()+" - "+unidade.getDescricao());
		}
		System.out.println("Qual registro deseja atualizar?");
		int opcao = scanner.nextInt();
		System.out.println("Como vai ser a nova descricao do unidade?");
		String novaDescricao = scanner.next();
		
		Unidade unidadeSelecionado = unidadeRepository.findById(opcao).get();
		
		unidadeSelecionado.setDescricao(novaDescricao);
		
		unidadeRepository.save(unidadeSelecionado);
		System.out.println("Unidade atualizado com sucesso");
	}
	
	private void visualizar() {
		Iterable<Unidade> unidades = unidadeRepository.findAll();
		
		for(Unidade unidade : unidades) {
			System.out.println(unidade.toString());
		}
	}
	
	private void deletar(Scanner scanner) {
		Iterable<Unidade> unidades = unidadeRepository.findAll();
		
		for(Unidade unidade : unidades) {
			System.out.println(unidade.getId()+" - "+unidade.getDescricao());
		}
		System.out.println("Qual registro deseja deletar?");
		int opcao = scanner.nextInt();
		
		unidadeRepository.deleteById(opcao);
		System.out.println("Unidade deletado com sucesso");
	}
}
